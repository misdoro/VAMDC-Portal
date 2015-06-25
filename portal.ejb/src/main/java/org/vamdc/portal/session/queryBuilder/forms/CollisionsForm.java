package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.vamdc.dictionary.IAEAProcessCode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.XsamsProcessCode;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.LabelField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionImpl;

public class CollisionsForm extends AbstractForm implements Form{


	private static final long serialVersionUID = -2400424219243476027L;
	@Override
	public String getTitle() { return "Collisions"; }
	@Override
	public Integer getOrder() { return Order.Process; }
	@Override
	public String getView() { return "/xhtml/query/forms/collisionsForm.xhtml"; }

	
	protected AbstractField processName;
	protected AbstractField processDescription;
	protected AbstractField xsamsProcCode;
	protected AbstractField iaeaProcCode;
	
	public CollisionsForm() {
		super();
		init();

	}
	
	protected void init(){
		processName = new SuggestionField(null,"Process name",new ProcessNameSuggest());
		addField(processName);
		
		processDescription = new LabelField("Process description");
		addField(processDescription);
		
		xsamsProcCode = new SuggestionField(Restrictable.CollisionCode,"Process code",new XsamsCodeSuggest());
		addField(xsamsProcCode);

		iaeaProcCode = new SuggestionField(Restrictable.CollisionIAEACode,"IAEA process code",new IaeaCodeSuggest());
		addField(iaeaProcCode);		
		
	}

	public class XsamsCodeSuggest extends SuggestionImpl{

		private static final long serialVersionUID = 7850044834682292686L;
		@Override
		protected Collection<String> loadValues() {
			Collection<String> result = new ArrayList<String>();
			for (XsamsProcessCode key:XsamsProcessCode.values())
				result.add(key.name());
			return result;
		}

		@Override
		public void selected() {
			processDescription.setValue("");
			processName.setValue("");
		}
	}

	public class IaeaCodeSuggest extends SuggestionImpl{

		private static final long serialVersionUID = 4808699657011895289L;
		@Override
		protected Collection<String> loadValues() {
			Collection<String> result = new ArrayList<String>();
			for (IAEAProcessCode key:IAEAProcessCode.values())
				result.add(key.name());
			return result;
		}

		@Override
		public void selected() {
			processDescription.setValue("");
			processName.setValue("");
		
		}
	}
		
	public class ProcessNameSuggest extends SuggestionImpl{

		private static final long serialVersionUID = -3692917437915852791L;

		public ProcessNameSuggest() { super(); }

		@Override
		protected Collection<String> loadValues() {
			Collection<String> result = new ArrayList<String>();
			for (XsamsProcessCode code:XsamsProcessCode.values())
				result.add(code.getProcessName());
			return result;
		}
		
		@Override
		public void selected() {
			String process = processName.getValue();

			XsamsProcessCode code = getCodeFromName(process);
			if (code!=null){
				processDescription.setValue(code.getDescription());
				xsamsProcCode.setValue(code.name());
			}
			
			
		}
		
		private XsamsProcessCode getCodeFromName(String name){
			for (XsamsProcessCode code:XsamsProcessCode.values()){
				if (code.getProcessName().equals(name))
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
		
		public String getSummary(){
			return speciesForm.getSummary();
		}
	}

	public Collection<SpeciesFacade> getSpecies(){
		Collection<SpeciesFacade> result = new ArrayList<SpeciesFacade>();
		for(Form form:queryData.getSpeciesForms())
			result.add(new SpeciesFacade(form,0));
		return result;
	}
	
	@Override
	public void clear(){
		super.clear();
		clearPrefixes();
	}
	
	@Override
	public void delete(){
		super.delete();
		clearPrefixes();
	}
	
	private void clearPrefixes() {
		for(Form form:queryData.getSpeciesForms()){
			form.setPrefix("");
			form.setPrefixIndex(0);
		}
	}
	
}
