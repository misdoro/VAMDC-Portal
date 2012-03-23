package org.vamdc.portal.entity;

import org.vamdc.portal.entity.species.SpeciesIso;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm.MoleculeInfo;

public class IsotopologueFacade implements MoleculeInfo{
	private SpeciesIso molecule;
	public IsotopologueFacade(SpeciesIso iso){
		this.molecule=iso;
	}
	
	@Override
	public String getFormula(){ return molecule.getIsoName(); }
	@Override
	public String getInchiKey() { return molecule.getInChIkey(); }
	@Override
	public String getDescription() { return molecule.getIsoNameHtml(); }
	@Override
	public String getName() { return molecule.getIsoName(); }
	@Override
	public String getOrdinaryFormula() { return molecule.getIsoName(); }
}
