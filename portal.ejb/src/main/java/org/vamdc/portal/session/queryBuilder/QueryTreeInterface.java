package org.vamdc.portal.session.queryBuilder;

import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.formsTree.SearchMode;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeFormInterface;



public interface QueryTreeInterface {
public <T extends AbstractForm & TreeFormInterface> void addForm( T form);
public Boolean getSubmitable();
//public void removeForm(AbstractForm form);
public Integer getFormCount();
public void setSelectionMode(SearchMode searchMode);
}
