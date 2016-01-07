package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;

import org.vamdc.dictionary.Requestable;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.SpeciesForm;

/**
 * An interface collecting all the methods required by forms from their holder
 * @author doronin
 *
 */
public interface FormHolder {

	void resetCaches();

	void deleteForm(Form Form);

	Collection<SpeciesForm> getSpeciesForms();

	String getQueryString();

	void setCustomQueryString(String newQueryString);

	String getComments();

	void setComments(String comments);

	void setRequest(Collection<Requestable> requestFor);
	
	Integer getFormTypeCount(Form form);

}
