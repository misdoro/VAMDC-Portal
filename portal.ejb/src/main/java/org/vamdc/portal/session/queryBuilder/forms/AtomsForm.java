package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.TextField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import java.lang.Integer;

public class AtomsForm extends AbstractForm implements Form, SpeciesForm {


	private static final long serialVersionUID = -795296288400049729L;
    private static Integer formCount = null;
    private int position;
	@Override
	public String getTitle() { return "Atom "+ position; }
	@Override
	public Integer getOrder() { return Order.Atoms; }
	@Override
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }     
    
    public Integer getPosition(){
        return position;
    }
   
    public void decreasePosition(){
        position -= 1;
    }
    
    public void decreaseFormCount(){
        AtomsForm.formCount -= 1;
    }    
    
    public static void initFormCount(){
        formCount = null;
    }
	
	public AtomsForm(){
		super();
        addForm();
        position = formCount.intValue();
		addField(new SimpleField(Restrictable.AtomSymbol,"Atom symbol"));
		addField(new RangeField(Restrictable.AtomMassNumber,"Mass number"));
		addField(new RangeField(Restrictable.AtomNuclearCharge,"Nuclear charge"));
		addField(new RangeField(Restrictable.IonCharge,"Ion charge"));
		addField(new TextField(Restrictable.InchiKey,"InChIKey"));
		addField(new UnitConvRangeField(
				Restrictable.StateEnergy, "State energy", new EnergyUnitConverter()));
   
	}
    
    public static void addForm(){
        if( formCount == null){
            formCount = 1;        
        }else
            formCount += 1;
    }
}
