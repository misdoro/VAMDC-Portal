package org.vamdc.portal.session.queryBuilder.forms;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.EntityManager;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.entity.molecules.MoleculeNames;
import org.vamdc.portal.entity.molecules.Molecules;
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
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public MoleculesForm(){

		addField(new SuggestionField(Restrictable.MoleculeChemicalName,"Chemical name", new ChemNameSuggestion()));
		addField(new SuggestionField(Restrictable.MoleculeStoichiometricFormula,"Stoichiometric formula", new StoichFormSuggestion()));
		addField(new RangeField(Restrictable.IonCharge,"Ion charge"));
		addField(new SimpleField(Restrictable.InchiKey,"InChIKey"));
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
			//TODO: process selection event
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
			//TODO: process selection event
		}
	}

}
