package org.vamdc.portal.session.queryBuilder.fields;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SuggestionImpl implements SuggestionField.Suggestion{

	private static final long serialVersionUID = 8835812132003605567L;
	
	/**
	 * @return a collection that will be used for suggestion lookup
	 */
	protected abstract Collection<String> loadValues();
	protected Collection<String> values;
	
	public SuggestionImpl(){
		this.values= loadValues();
	}
	
	@Override
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

	@Override
	public String getIllegalLabel() { return "No suggestion available"; }
	@Override
	public void selected() {};
}
