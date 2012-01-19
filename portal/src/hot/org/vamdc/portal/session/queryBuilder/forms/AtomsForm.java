package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class AtomsForm implements QueryForm {

	private List<AbstractField> fields;

	public AtomsForm(){
		fields = new ArrayList<AbstractField>();
		fields.add(new SimpleField(Restrictable.AtomSymbol,"Atom symbol"));
		fields.add(new SimpleField(Restrictable.InchiKey,"InChIKey"));
	}

	public String getTitle() { return "Atoms"; }

	public String getView() { return "/xhtml/query/forms/atomsForm.xhtml"; }

	public Collection<AbstractField> getFields() { return fields; }

	public String getQueryPart() {
		String query="";
		for (AbstractField field:fields){
			if (field.hasValue()){
				if (query.length()>0){
					query+=" AND "+field.getQuery();
				}
				else query=field.getQuery();
			}
		}
		return query;
	}

	public Collection<Restrictable> getKeywords() {
		EnumSet<Restrictable> keywords = EnumSet.noneOf(Restrictable.class);
		for (AbstractField field:fields){
			if (field.hasValue())
				keywords.add(field.getKeyword());
		}
		return keywords;
	}

	public String getId() {
		return "1";
	}

}
