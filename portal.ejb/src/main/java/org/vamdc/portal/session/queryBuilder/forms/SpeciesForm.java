package org.vamdc.portal.session.queryBuilder.forms;

/**
 * 
 * @author doronin
 */
public abstract class SpeciesForm extends AbstractForm{
	private static final long serialVersionUID = 2775326654582002862L;
	private static Integer formCount = null;
	protected int position;
    public SpeciesForm(){
    	super();
    	addForm();
        position = formCount.intValue();
    }
    
    public static void initFormCount(){
        formCount = null;
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
       formCount -= 1;
    } 
    
}
