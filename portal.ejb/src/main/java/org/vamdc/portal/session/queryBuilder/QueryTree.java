package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeForm;

@Name("queryTree")
@Scope(ScopeType.CONVERSATION)
public class QueryTree {

	private Collection<TreeForm> forms;
	public QueryTree(){
		forms=new ArrayList<TreeForm>();
		forms.add(new RootForm());
		
	}
	
	public Collection<TreeForm> getForms(){
		return Collections.unmodifiableCollection(forms);
	}
	
	public boolean isDone(){
		return true;
	}
}
