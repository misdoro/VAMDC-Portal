package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;

public class RadiativeForm extends TreeForm{

	protected List<AbstractField> fields;
	public RadiativeForm(QueryTreeInterface tree) {
		super(tree);
		this.fields = new ArrayList<AbstractField>();
		fields.add(new RangeField(Restrictable.RadTransWavelength, "Transition wavelength"));
	}
	
	public Collection<AbstractField> getFields() { return fields; }

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/radiativeForm.xhtml";
	}

	@Override
	public void validate() {
		
	}

}
