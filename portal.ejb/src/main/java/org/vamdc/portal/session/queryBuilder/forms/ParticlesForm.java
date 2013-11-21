package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionImpl;

public class ParticlesForm extends AbstractForm implements Form, SpeciesForm{


	private static final long serialVersionUID = 6076734404479237682L;
    private static Integer formCount = null;
    private int position;
	@Override
	public String getTitle() { return "Particle "+ position; }
	@Override
	public Integer getOrder() { return Order.Particles; }
	@Override
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	
	public ParticlesForm(){
        addForm();
        position = formCount.intValue();
		addField(
				new SuggestionField(Restrictable.ParticleName,"Particle name",new ParticleNameSuggest()));
	}

    public static void addForm(){
        if( formCount == null){
            formCount = 1;        
        }else
            formCount += 1;
    }
    
    public Integer getPosition(){
        return position;
    }
    
    public void decreasePosition(){
        position -= 1;
    }
    
    public void decreaseFormCount(){
        ParticlesForm.formCount -= 1;
    }    
    
    public static void initFormCount(){
        formCount = null;
    }
	
	public class ParticleNameSuggest extends SuggestionImpl{

		private static final long serialVersionUID = 8545624978301193585L;
		@Override
		protected Collection<String> loadValues() {
			Collection<String> result = new ArrayList<String>(){
				private static final long serialVersionUID = -7876192603503355123L;
			{
				add("photon");
				add("electron");
				add("muon");
				add("positron");
				add("neutron");
				add("alpha");
			    add("cosmic");
			}};
			return result;
		}
	}
	
}
