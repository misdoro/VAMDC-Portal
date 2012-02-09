package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;

public class TransitionsForm extends AbstractForm implements QueryPageForm{

	public TransitionsForm(QueryData queryData){
		super(queryData);
		fields = new ArrayList<AbstractField>();
		fields.add(new RangeField(Restrictable.RadTransWavelength,"Wavelength"));
		fields.add(new RangeField("upper",Restrictable.StateEnergy,"Upper state energy"));
		fields.add(new RangeField("lower",Restrictable.StateEnergy,"Lower state energy"));
		fields.add(new RangeField(Restrictable.RadTransProbabilityA,"Probability, A"));
	}

	public String getTitle() { return "Transitions"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	

}
