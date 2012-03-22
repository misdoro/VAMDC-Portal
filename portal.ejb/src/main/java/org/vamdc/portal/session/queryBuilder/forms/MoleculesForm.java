package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.entity.EntityFacade;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;
import org.vamdc.portal.session.queryBuilder.fields.TextField;

public class MoleculesForm extends AbstractForm implements Form{

	/**
	 * Molecule info for isotopologues table
	 * 
	 */
	public interface MoleculeInfo{
		public String getFormula();
		public String getInchiKey();
		public String getDescription();
	}

	private List<MoleculeInfo> molecules = Collections.emptyList();

	private AbstractField molChemName;
	private AbstractField molStoichForm;
	private AbstractField inchikey;

	private Map<String,Boolean> inchikeys = new HashMap<String,Boolean>();

	private static final long serialVersionUID = 3499663104107031985L;
	@Override
	public String getTitle() { return "Molecules"; }
	@Override
	public Integer getOrder() { return Order.Molecules; }
	@Override
	public String getView() { return "/xhtml/query/forms/moleculesForm.xhtml"; }

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

	public List<MoleculeInfo> getMolecules() { return molecules; }

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



	public class ChemNameSuggestion implements SuggestionField.Suggestion{

		private static final long serialVersionUID = 7286322175153115141L;

		@Override
		public Collection<String> options(Object input) {
			return EntityFacade.suggestChemicalName(queryData.getEntityManager(),(String)input);
		}

		@Override
		public String getIllegalLabel() {
			return "not found in the database";
		}

		@Override
		public void selected() {
			molecules = EntityFacade.loadMoleculesFromName(queryData.getEntityManager(), molChemName.getValue());
			molStoichForm.clear();
			resetInchiKeys();
		}
	}


	public class StoichFormSuggestion implements SuggestionField.Suggestion{

		private static final long serialVersionUID = -1570905697531370200L;

		@Override
		public Collection<String> options(Object input) {
			return EntityFacade.suggestStoichiometricFormula(queryData.getEntityManager(),(String)input);
		}

		@Override
		public String getIllegalLabel() {
			return "not found in the database";
		}

		@Override
		public void selected() {
			molecules = EntityFacade.loadMoleculesFromStoichForm(queryData.getEntityManager(), molStoichForm.getValue());
			molChemName.clear();
			resetInchiKeys();
		}
	}


}
