package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm;
import org.vamdc.portal.session.queryBuilder.forms.EnvironmentForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.TransitionsForm;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.NodeFilter;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.tapservice.vss2.impl.QueryImpl;

public class QueryLoader {
	public static boolean loadQuery(QueryData queryData,String queryString) {
		Query qp;
		try{
			qp = new QueryImpl(queryString,null);
		}catch (IllegalArgumentException e){
			return false;
		}

		if (qp.getRestrictsTree()==null)
			return false;

		addSingleForm(queryData, qp, new TransitionsForm());
		addSingleForm(queryData, qp, new CollisionsForm());
		addSingleForm(queryData, qp, new EnvironmentForm());
		
		
		return true;
	}

	private static void addSingleForm(QueryData queryData, Query qp, Form form) {
		Collection<Restrictable> keys = form.getSupportedKeywords();
		LogicNode root = NodeFilter.filterKeywords(qp.getRestrictsTree(),keys);
		if (root!=null){
			System.out.println(root.toString());
			form.loadFromQuery(root);
		}

		if (hasData(form))
			queryData.addForm(form);
	}

	private static boolean hasData(Form form) {
		return form.getQueryPart().length()>0;
	}
}
