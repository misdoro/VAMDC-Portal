package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class ParticlesForm extends AbstractForm implements QueryForm{

	public String getTitle() { return "Particles"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public String getId() {	return "3"; }
	
	public ParticlesForm(Collection<QueryForm> forms){
		super(forms);
		fields = new ArrayList<AbstractField>();
		fields.add(new SimpleField(Restrictable.ParticleName,"Particle name"));
	}

}
