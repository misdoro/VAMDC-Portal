package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class QueryGenerator {


	private final static String OR = " OR ";
	private final static String AND = " AND ";


	public static String getFormsQuery(Collection<Form> forms){
		SortedSet<Form> sorted = new TreeSet<Form>(new FormComparator()); 
		sorted.addAll(forms);

		String result = "";
		Integer reactantIndex=0;
		Integer productIndex=0;

		String oldPrefix="";

		String modifier=AND;
		boolean inOrGroup=false;
		String orGroup="";

		for (Form form:sorted){

			if (form.getPrefix().equals("reactant")){
				form.setPrefixIndex(reactantIndex++);
			}else if (form.getPrefix().equals("product")){
				form.setPrefixIndex(productIndex++);
			}


			if ((form.getPrefix()==null
					|| form.getPrefix().length()==0 
					|| (form.getPrefix().equals(oldPrefix) 
							&& 
							(oldPrefix.equals("target")||oldPrefix.equals("collider"))
							)
					)
					&&form.getOrder()<=Order.SPECIES_LIMIT){
				modifier=OR;
				inOrGroup=true;
			}
			else
				modifier=AND;

			oldPrefix=form.getPrefix();


			if (inOrGroup){
				if (modifier==OR){
					orGroup=addQueryPart(orGroup, modifier, form.getQueryPart());
				}else{
					result=addQueryPart(result,modifier,orGroup);
					orGroup="";
					inOrGroup=false;
				}
			}else
				result = addQueryPart(result, modifier, form.getQueryPart());
		}
		return result;
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
}
