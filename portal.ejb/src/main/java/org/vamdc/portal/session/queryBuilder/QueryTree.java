package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeForm;

@Name("queryTree")
@Scope(ScopeType.CONVERSATION)
public class QueryTree {

	@In(create=true) @Out QueryData queryData;
	
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
	
	public String saveQuery(){
		return RedirectPage.QUERY_LOG;
	}
	
	public String preview(){	
		if (isDone()){
			return RedirectPage.PREVIEW;
		}else 
			return RedirectPage.QUERY;
	}
}
