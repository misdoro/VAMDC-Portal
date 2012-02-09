package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.vamdc.dictionary.IAEAProcessCode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.XsamsProcessCode;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.LabelField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;

public class CollisionsForm extends AbstractForm implements Form{

	public String getTitle() { return "Collisions"; }
	public Integer getOrder() { return Order.Collisions; }
	public String getView() { return "/xhtml/query/forms/collisionsForm.xhtml"; }

	
	private AbstractField processName;
	private AbstractField processDescription;
	private AbstractField xsamsProcCode;
	private AbstractField iaeaProcCode;
	private QueryData queryData;
	
	public CollisionsForm(QueryData queryData) {
		super(queryData);
		this.queryData = queryData;
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
	
	/**
	 * Class for managing species table at the bottom of the form
	 * @author doronin
	 *
	 */
	public class SpeciesFacade{
		private Form speciesForm;
		private int index;
		
		public SpeciesFacade(Form speciesForm,int index){
			this.speciesForm = speciesForm;
			this.index = index;
		}
	
		public void roleChanged(ValueChangeEvent event){
			String prefix="";
			if (event!=null)
				prefix = (String)event.getNewValue();
			
			if ("reactant".equals(prefix) || "product".equals(prefix))
				prefix=prefix+index;
			speciesForm.setPrefix(prefix);
		}
		
		public Collection<SelectItem> getRoles(){
			 Collection<SelectItem> result = new ArrayList<SelectItem>();
			 result.add(new SelectItem("","undefined"));
			 result.add(new SelectItem("target","Target"));
			 result.add(new SelectItem("collider","Collider"));
			 result.add(new SelectItem("reactant","Reactant"));
			 result.add(new SelectItem("product","Product"));
			 return result;
		}
		
		public String getRole(){ return speciesForm.getPrefix(); }
		public void setRole(String role){ speciesForm.setPrefix(role); }
		
		public String getTitle(){ return speciesForm.getTitle(); }
		
		public String getId(){
			return speciesForm.getId();
		}
	}

	public Collection<SpeciesFacade> getSpecies(){
		Collection<SpeciesFacade> result = new ArrayList<SpeciesFacade>();
		for(Form form:queryData.getSpeciesForms())
			result.add(new SpeciesFacade(form,0));
		return result;
	}
	
}
