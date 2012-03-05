package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.TransitionsForm;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.NodeFilter;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.tapservice.vss2.impl.QueryImpl;

public class QueryLoader {
	public static boolean loadQuery(QueryData query,String queryString) {
		Query qp;
		try{
			qp = new QueryImpl(queryString,null);
		}catch (IllegalArgumentException e){
			return false;
		}

		if (qp.getRestrictsTree()==null)
			return false;
		
		
		TransitionsForm form = new TransitionsForm();
		Collection<Restrictable> keys = form.getSupportedKeywords();
		LogicNode root = NodeFilter.filterKeywords(qp.getRestrictsTree(),keys);
		if (root!=null){
			System.out.println(root.toString());
			form.loadFromQuery(root);
		}
		query.addForm(form);
		
		return true;
	}
}
