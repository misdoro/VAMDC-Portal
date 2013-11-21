package org.vamdc.portal.session.queryBuilder.forms;

import java.io.Serializable;
import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.tapservice.vss2.LogicNode;

/**
 * Interface used to build forms
 * @author doronin
 */
public interface SpeciesForm extends Serializable{
	public Integer getPosition();
    public void decreasePosition();
    public void decreaseFormCount();
}
