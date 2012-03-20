package org.vamdc.portal.session.queryBuilder.forms;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.entity.molecules.Isotopologues;
import org.vamdc.portal.entity.molecules.MoleculeNames;
import org.vamdc.portal.entity.molecules.Molecules;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;

public class MoleculesForm extends AbstractForm implements Form{


	private static final long serialVersionUID = 3499663104107031985L;
	@Override
	public String getTitle() { return "Molecules"; }
	@Override
	public Integer getOrder() { return Order.Molecules; }
	@Override
	public String getView() { return "/xhtml/query/forms/moleculesForm.xhtml"; }

	private List<IsotopologueFacade> molecules = Collections.emptyList();
	
	private AbstractField molChemName;
	private AbstractField molStoichForm;
	private AbstractField inchikey;
	
	private Map<String,Boolean> inchikeys = new HashMap<String,Boolean>();
	
	public MoleculesForm(){
		molChemName = new SuggestionField(Restrictable.MoleculeChemicalName,"Chemical name", new ChemNameSuggestion());
		addField(molChemName);
		
		molStoichForm = new SuggestionField(Restrictable.MoleculeStoichiometricFormula,"Stoichiometric formula", new StoichFormSuggestion()); 
		addField(molStoichForm);
		
		addField(new RangeField(Restrictable.IonCharge,"Ion charge"));
		
		inchikey = new SimpleField(Restrictable.InchiKey,"InChIKey");
		addField(inchikey);
	}

	public List<IsotopologueFacade> getMolecules() { return molecules; }
	
	public Map<String,Boolean> getInchikeys() {	return inchikeys; }
	public void setInchikeys(Map<String,Boolean> inchikeys) { this.inchikeys = inchikeys; }

	public class IsotopologueFacade {
		private Isotopologues molecule;
		public IsotopologueFacade(Isotopologues molecule){
			this.molecule=molecule; 
			System.out.println("Loaded isotopologue"+molecule.getIsoName()+molecule.getInChIkey());
		}
		public String getFormula(){ return molecule.getIsoName(); }
		public String getInchikey(){ return molecule.getInChIkey();	}
	}
	
	private Integer getMolecIDfromName(String name){
		EntityManager entityManager = queryData.getEntityManager();
		if (entityManager==null)
			return 0;
		MoleculeNames molec = (MoleculeNames) entityManager.createNamedQuery("MoleculeNames.findByMolecName")
				.setParameter("molecName", name).getSingleResult();
		if (molec!=null)
			return molec.getMolecId();
		return 0;
	}
	
	private Integer getMolecIDfromStoichForm(String formula){
		EntityManager entityManager = queryData.getEntityManager();
		if (entityManager==null)
			return 0;
		Molecules molec = (Molecules) entityManager.createNamedQuery("Molecules.findByStoichiometricFormula")
				.setParameter("stoichiometricFormula", formula)
				.getSingleResult();
		if (molec!=null)
			return molec.getId();
		return 0;
	}
	
	
	private List<IsotopologueFacade> loadMolecules(Integer molecID){
		EntityManager entityManager = queryData.getEntityManager();

		if (entityManager==null)
			return Collections.emptyList();
		ArrayList<IsotopologueFacade> result = new ArrayList<IsotopologueFacade>();
		for (Object isotopologue:
			entityManager.createNamedQuery("Isotopologues.findByMolecID")
			.setParameter("molecId", molecID)
			.getResultList()){
			System.out.println(isotopologue);
			Isotopologues isot=(Isotopologues) isotopologue;
			result.add(new IsotopologueFacade(isot));
		}
		return result;
	}
	

	public class ChemNameSuggestion implements SuggestionField.Suggestion{

		private static final long serialVersionUID = 7286322175153115141L;

		@Override
		public Collection<String> options(Object input) {
			String value = (String) input;
			if (value==null || value.length()==0 || value.trim().length()==0)
				return Collections.emptyList();

			Collection<String> result = new ArrayList<String>();

			EntityManager entityManager = queryData.getEntityManager();

			if (entityManager==null)
				return Collections.emptyList();

			for (Object molecule:entityManager
					.createNamedQuery("MoleculeNames.findByMolecNameWildcard")
					.setParameter("molecName", "%"+value+"%")
					.setMaxResults(20)
					.getResultList()){
				MoleculeNames molec = (MoleculeNames)molecule;
				result.add(molec.getMolecName());
			}

			return result;
		}

		@Override
		public String getIllegalLabel() {
			return "not found in the database";
		}

		@Override
		public void selected() {
			Integer molecId=getMolecIDfromName(molChemName.getValue());
			molecules = loadMolecules(molecId);
		}
	}

	public class StoichFormSuggestion implements SuggestionField.Suggestion{


		private static final long serialVersionUID = -1570905697531370200L;

		@Override
		public Collection<String> options(Object input) {
			String value = (String) input;
			if (value==null || value.length()==0 || value.trim().length()==0)
				return Collections.emptyList();

			Collection<String> result = new ArrayList<String>();

			EntityManager entityManager = queryData.getEntityManager();

			if (entityManager==null)
				return Collections.emptyList();

			for (Object molecule:entityManager
					.createNamedQuery("Molecules.findByStoichiometricFormulaWildcard")
					.setParameter("stoichiometricFormula", "%"+value+"%")
					.setMaxResults(20)
					.getResultList()){
				Molecules molec = (Molecules)molecule;
				result.add(molec.getStoichiometricFormula());
			}

			return result;
		}

		@Override
		public String getIllegalLabel() {
			return "not found in the database";
		}

		@Override
		public void selected() {
			Integer molecId=getMolecIDfromStoichForm(molStoichForm.getValue());
			molecules = loadMolecules(molecId);
		}
	}

}
