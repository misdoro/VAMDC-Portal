package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeFormInterface;


@Name("queryTree")
@Scope(ScopeType.CONVERSATION)
public class QueryTree implements QueryTreeInterface {

	@In(create=true) @Out QueryData queryData;
	@Logger private Log log;
	
	private Collection<TreeFormInterface> forms;
	public QueryTree(){
		forms=new ArrayList<TreeFormInterface>();
		forms.add(new RootForm(this));
		
	}
	
	public Collection<TreeFormInterface> getForms(){
		return Collections.unmodifiableCollection(forms);
	}
	
	public boolean isDone(){
		return true;
	}
	
	public String saveQuery(){
		log.info("Mock save query");
		return RedirectPage.QUERY_LOG;
	}
	
	public String preview(){
		log.info("Mock preview query");
		if (isDone()){
			return RedirectPage.PREVIEW;
		}else 
			return RedirectPage.QUERY;
	}

	@Override
	public void addForm(TreeFormInterface form) {
		forms.add(form);
	}
}
