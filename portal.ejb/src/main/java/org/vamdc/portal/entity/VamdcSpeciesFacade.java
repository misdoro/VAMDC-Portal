package org.vamdc.portal.entity;

import org.vamdc.portal.entity.constant.Markup;
import org.vamdc.portal.entity.species.VamdcSpecies;
import org.vamdc.portal.entity.species.VamdcSpeciesNames;
import org.vamdc.portal.entity.species.VamdcSpeciesStructFormulae;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm.MoleculeInfo;

public class VamdcSpeciesFacade implements MoleculeInfo{

	private final VamdcSpecies element;
	
	public VamdcSpeciesFacade(VamdcSpecies element){
		this.element = element;
	}
	
	@Override
	public String getName() {
		for (VamdcSpeciesNames vsn:element.getVamdcSpeciesNameses()){
			if (vsn.getVamdcMarkupTypes().getId() == Markup.Text.getId())
				return vsn.getName();
		}
		return "";
	}

	@Override
	public String getFormula() {return element.getStoichiometricFormula();}

	@Override
	public String getOrdinaryFormula() { 
		for (VamdcSpeciesStructFormulae vsff:element.getVamdcSpeciesStructFormulaes()){
			if (vsff.getVamdcMarkupTypes().getId() == Markup.Text.getId())
				return vsff.getFormula();
		}
		return "";
			
	}

	@Override
	public String getInchiKey() { return element.getInchikey(); }

	@Override
	public String getVamdcSpeciesID() {	return element.getId(); }

	@Override
	public String getDescription() {
		String result = "";
		for(VamdcSpeciesStructFormulae vsff: element.getVamdcSpeciesStructFormulaes()){
			result=vsff.getFormula();
			if (vsff.getVamdcMarkupTypes().getId() == Markup.Html.getId())
				break;
		}
		return appendName(result);
	
	}
	private String appendName(String data){
		return getName()+" "+data+"";
	}

}
