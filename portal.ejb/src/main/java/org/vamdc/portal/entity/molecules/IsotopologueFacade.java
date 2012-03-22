package org.vamdc.portal.entity.molecules;

import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm.MoleculeInfo;

public class IsotopologueFacade implements MoleculeInfo{
	private Isotopologues molecule;
	public IsotopologueFacade(Isotopologues molecule){
		this.molecule=molecule;
	}
	
	@Override
	public String getFormula(){ return molecule.getIsoName(); }
	@Override
	public String getInchiKey() { return molecule.getInChIkey(); }
	@Override
	public String getDescription() { return molecule.getIsoNameHtml(); }
}
