package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.vamdc.dictionary.Requestable;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class QueryGenerator {


	private final static String OR = " OR ";
	private final static String AND = " AND ";


	public static String getFormsQuery(Collection<Form> forms){

		SortedSet<Form> sorted = new TreeSet<Form>(new Order()); 
		sorted.addAll(forms);

		String result = "";
		Integer reactantIndex=0;
		Integer productIndex=0;

		String oldPrefix="";
		String orGroup="";

		for (Form form:sorted){
			
			if (form.getPrefix().equals("reactant")){
				form.setPrefixIndex(reactantIndex++);
			}else if (form.getPrefix().equals("product")){
				form.setPrefixIndex(productIndex++);
			}

			if (formIsPrefixChanged(oldPrefix,form)){
				result=addQueryPart(result,AND,orGroup);
				orGroup="";
			}	
			oldPrefix=form.getPrefix();

			if (formIsSpecies(form)&&
					(formHasNoPrefix(form)
					||formIsTargetOrCollider(form))){
				orGroup=addQueryPart(orGroup, OR, form.getQueryPart());
			}else{
				result = addQueryPart(result, AND, form.getQueryPart());
			}
			
		}
		//Add the last orGroup, if any
		result = addQueryPart(result, AND, orGroup);
		
		return result;
	}

	private static boolean formIsTargetOrCollider(Form form) {
		return form.getPrefix().equals("target")||form.getPrefix().equals("collider");
	}

	private static boolean formIsPrefixChanged(String oldPrefix, Form form) {
		return !form.getPrefix().equals(oldPrefix);
	}

	private static boolean formIsSpecies(Form form) {
		return form.getOrder()<=Order.SPECIES_LIMIT;
	}

	private static boolean formHasNoPrefix(Form form){
		return form.getPrefix()==null || form.getPrefix().length()==0; 
	}

	private static String addQueryPart(String result, String modifier, String queryPart) {
		if (queryPart.length()>0){
			queryPart="("+queryPart+")";
			if (result.length()>0)
				result+=modifier+queryPart;
			else
				result=queryPart;
		}
		return result;
	}
	
	public static String getRequestPart(Collection<Requestable> keywords){
		
		if (keywords==null || keywords.isEmpty())
			return "*";
		else{
			String result = "";
			for (Requestable key:keywords){
				result+=key.name()+",";
			}
			return result.substring(0,result.length()-1);
		}
	}
	
	public static String buildQueryString(Collection<Requestable> requestables,Collection<Form> forms){
		String result = "select ";
		result+=QueryGenerator.getRequestPart(requestables);
		
		String formsQuery = QueryGenerator.getFormsQuery(forms);
		if (formsQuery!=null && !formsQuery.isEmpty()){
			result+=" where "+formsQuery;
		}
		
		return result;
	}
}
