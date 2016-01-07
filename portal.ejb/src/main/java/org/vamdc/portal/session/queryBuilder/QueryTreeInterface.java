package org.vamdc.portal.session.queryBuilder;

import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeFormInterface;



public interface QueryTreeInterface {
public <T extends AbstractForm & TreeFormInterface> void addForm( T form);
public Boolean getSubmitable();
public <T extends AbstractForm & TreeFormInterface> void removeForm(T form);
public Integer getFormCount();
}
