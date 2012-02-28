package org.vamdc.portal.session.queryBuilder.fields;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SuggestionImpl implements SuggestionField.Suggestion{

	private static final long serialVersionUID = 8835812132003605567L;
	protected abstract Collection<String> getValues();

	protected Collection<String> values;
	public SuggestionImpl(){
		this.values= getValues();
	}
	
	public Collection<String> options(Object input) {
		String in = (String)input;
		Collection<String> result = new ArrayList<String>();
		for (String key:values){
			if (key.toUpperCase().contains(in.toUpperCase())){
				result.add(key);
			}
		}
		return result;
	}

	public String getIllegalLabel() { return "Illegal value"; }
	public abstract void selected();
}
