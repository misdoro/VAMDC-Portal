package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.XsamsProcessCode;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;

public class CollisionsForm extends AbstractForm implements QueryForm{

	AbstractField xsamsProcCode;
	AbstractField iaeaProcCode;
	public CollisionsForm(QueryData queryData) {
		super(queryData);
		fields = new ArrayList<AbstractField>();
		
		xsamsProcCode = new SuggestionField(Restrictable.CollisionCode,"Process code",new XsamsCodeSuggest());
		fields.add(xsamsProcCode);
		
		iaeaProcCode = new SimpleField(Restrictable.CollisionIAEACode,"IAEA process code");
		fields.add(iaeaProcCode);
	}

	public String getTitle() { return "Collisions"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public class XsamsCodeSuggest implements SuggestionField.Suggestion{
		public Collection<String> options(Object input) {
			String in = (String)input;
			Collection<String> processes = new ArrayList<String>();
			for (XsamsProcessCode key:XsamsProcessCode.values()){
				String guess = key.name();
				if (guess.toUpperCase().contains(in.toUpperCase())){
					processes.add(guess);
				}
			}
			return processes;
		}

		public String getIllegalLabel() { return "Illegal process code"; }
	}
	
}
