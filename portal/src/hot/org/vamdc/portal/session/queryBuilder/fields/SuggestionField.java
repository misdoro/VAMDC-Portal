package org.vamdc.portal.session.queryBuilder.fields;

import java.util.Collection;

import org.vamdc.dictionary.Restrictable;

public class SuggestionField extends AbstractField{
	
	public interface Suggestion{
		public Collection<String> options(Object input);
		public String getIllegalLabel();
		public void selected();
	}
	
	public SuggestionField(Restrictable keyword, String title, Suggestion suggestion) {
		super(keyword, title);
		this.suggestion = suggestion;
	}

	private Suggestion suggestion;
	
	@Override
	public String getView() { return "/xhtml/query/fields/suggestionField.xhtml"; }
	
	public Suggestion getSuggestion(){ return this.suggestion; }
	public void setSuggestion(Suggestion suggestion) { this.suggestion = suggestion; }
	
	
	
}
