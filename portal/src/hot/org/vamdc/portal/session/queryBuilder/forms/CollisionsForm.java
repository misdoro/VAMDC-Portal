package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import org.vamdc.dictionary.IAEAProcessCode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.XsamsProcessCode;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.LabelField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;

public class CollisionsForm extends AbstractForm implements QueryForm{

	AbstractField processName;
	AbstractField processDescription;
	AbstractField xsamsProcCode;
	AbstractField iaeaProcCode;
	
	public CollisionsForm(QueryData queryData) {
		super(queryData);
		fields = new ArrayList<AbstractField>();

		processName = new SuggestionField(null,"Process name",new ProcessNameSuggest());
		fields.add(processName);
		
		processDescription = new LabelField("Process description");
		fields.add(processDescription);
		
		xsamsProcCode = new SuggestionField(Restrictable.CollisionCode,"Process code",new XsamsCodeSuggest());
		fields.add(xsamsProcCode);

		iaeaProcCode = new SuggestionField(Restrictable.CollisionIAEACode,"IAEA process code",new IaeaCodeSuggest());
		fields.add(iaeaProcCode);
	}

	public String getTitle() { return "Collisions"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public abstract class SuggestionImpl implements SuggestionField.Suggestion{
		protected abstract Collection<String> getValues();

		protected Collection<String> values;
		SuggestionImpl(){
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
		public void selected() { 
			processDescription.setValue("");
			processName.setValue("");
		}
	}

	public class XsamsCodeSuggest extends SuggestionImpl{
		@Override
		protected Collection<String> getValues() {
			Collection<String> result = new ArrayList<String>();
			for (XsamsProcessCode key:XsamsProcessCode.values())
				result.add(key.name());
			return result;
		}
	}

	public class IaeaCodeSuggest extends SuggestionImpl{
		@Override
		protected Collection<String> getValues() {
			Collection<String> result = new ArrayList<String>();
			for (IAEAProcessCode key:IAEAProcessCode.values())
				result.add(key.name());
			return result;
		}
	}
		
	public class ProcessNameSuggest extends SuggestionImpl{

		@Override
		protected Collection<String> getValues() {
			Collection<String> result = new ArrayList<String>();
			for (XsamsProcessCode code:XsamsProcessCode.values())
				result.add(code.processName());
			return result;
		}
		
		public void selected() {
			String process = processName.getValue();
			
			XsamsProcessCode code = getCodeFromName(process);
			if (code!=null){
				processDescription.setValue(code.processDesc());
				xsamsProcCode.setValue(code.name());
			}
			
			
		}
		
		private XsamsProcessCode getCodeFromName(String name){
			for (XsamsProcessCode code:XsamsProcessCode.values()){
				if (code.processName().equals(name))
					return code;
			}
			return null;
		}
	}

}
