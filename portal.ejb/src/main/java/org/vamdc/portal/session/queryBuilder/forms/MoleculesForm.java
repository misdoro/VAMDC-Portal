package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.entity.molecules.EntityQuery;
import org.vamdc.portal.entity.molecules.Isotopologues;
import org.vamdc.portal.entity.molecules.MoleculeNames;
import org.vamdc.portal.entity.molecules.Molecules;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;
import org.vamdc.portal.session.queryBuilder.fields.TextField;

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
		
		inchikey = new TextField(Restrictable.InchiKey,"InChIKey");
		addField(inchikey);
	}

	@Override
	public void clear(){
		super.clear();
		resetInchiKeys();
		molecules = Collections.emptyList();
	}
	
	public List<IsotopologueFacade> getMolecules() { return molecules; }
	
	public Map<String,Boolean> getSelectedInchikeys() {	return inchikeys; }
	public void setSelectedInchikeys(Map<String,Boolean> inchikeys) { this.inchikeys = inchikeys; }

	public void selectedInchi(){
		this.inchikey.setValue(buildInchiList(inchikeys));
		this.molChemName.setIgnoreField(true);
		this.molStoichForm.setIgnoreField(true);
	}
	
	private void resetInchiKeys(){
		inchikeys = new HashMap<String,Boolean>();
		inchikey.setValue("");
		this.molChemName.setIgnoreField(false);
		this.molStoichForm.setIgnoreField(false);
	}

	private String buildInchiList(Map<String, Boolean> inchikeys) {
		String result = "";
		for (String inchikey:inchikeys.keySet()){
			if (inchikeys.get(inchikey))
				result+=inchikey+", ";
		}
		return result;
	}
	
	
	public class IsotopologueFacade {
		private Isotopologues molecule;
		public IsotopologueFacade(Isotopologues molecule){
			this.molecule=molecule;
		}
		public String getFormula(){ return molecule.getIsoName(); }
		public String getInchikey(){ return molecule.getInChIkey();	}
	}
	

	public class ChemNameSuggestion implements SuggestionField.Suggestion{

		private static final long serialVersionUID = 7286322175153115141L;

		@Override
		public Collection<String> options(Object input) {
			String value = (String) input;
			if (value==null || value.length()==0 || value.trim().length()==0)
				return Collections.emptyList();

			Collection<String> result = new ArrayList<String>();
			for (Object molecule:EntityQuery.getMolecsFromNameWild(queryData.getEntityManager(), value.trim())){
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
			resetInchiKeys();
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

			for (Object molecule:EntityQuery.getMolecsFromFormulaWild(queryData.getEntityManager(), value.trim())){
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
			resetInchiKeys();
		}
	}
	
	private Integer getMolecIDfromName(String name){
		MoleculeNames molec = EntityQuery.getMolecNamesFromName(queryData.getEntityManager(), name);
		if (molec!=null)
			return molec.getMolecId();
		return 0;
	}
	
	private Integer getMolecIDfromStoichForm(String formula){
		Molecules molec = EntityQuery.getMoleculeFromFormula(queryData.getEntityManager(), formula);
		if (molec!=null)
			return molec.getId();
		return 0;
	}
	
	
	private List<IsotopologueFacade> loadMolecules(Integer molecID){
		ArrayList<IsotopologueFacade> result = new ArrayList<IsotopologueFacade>();
		for (Object isotopologue:EntityQuery.getIsotopologuesByMolecId(queryData.getEntityManager(), molecID)){
			result.add(new IsotopologueFacade((Isotopologues) isotopologue));
		}
		return result;
	}

}
