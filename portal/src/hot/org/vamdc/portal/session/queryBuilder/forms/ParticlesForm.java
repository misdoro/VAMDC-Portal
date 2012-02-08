package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class ParticlesForm extends AbstractForm implements QueryForm{

	public String getTitle() { return "Particles"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	
	public ParticlesForm(QueryData queryData){
		super(queryData);
		fields = new ArrayList<AbstractField>();
		fields.add(new SimpleField(Restrictable.ParticleName,"Particle name"));
	}

}
