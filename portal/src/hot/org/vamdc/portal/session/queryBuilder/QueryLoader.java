package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import org.mvel2.Operator;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm;
import org.vamdc.portal.session.queryBuilder.forms.EnvironmentForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm;
import org.vamdc.portal.session.queryBuilder.forms.ParticlesForm;
import org.vamdc.portal.session.queryBuilder.forms.TransitionsForm;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.NodeFilter;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.tapservice.vss2.RestrictExpression;
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

		Collection<LogicNode> subtrees = getSpeciesFormsSubtrees(qp);
		for (LogicNode subtree:subtrees){
			Form resultForm = findBestMatchingForm(subtree);
			if (resultForm!=null)
				queryData.addForm(resultForm);
			
		}


		return true;
	}

	private static Form findBestMatchingForm(LogicNode subtree) {
		int count=0,newCount;
		Form resultForm=null;
		
		for (Form newForm:getAllNewSpeciesForms()){
			newCount = loadForm(newForm, subtree);
			if (newCount>count){
				count=newCount;
				resultForm = newForm;
			}
		}
		return resultForm;
	}

	private static Collection<Form> getAllNewSpeciesForms(){
		Collection<Form> result = new ArrayList<Form>();
		result.add(new ParticlesForm());
		result.add(new MoleculesForm());
		result.add(new AtomsForm());
		return result;
	}

	private static Collection<LogicNode> getSpeciesFormsSubtrees(Query qp) {
		Collection<LogicNode> subtrees = new ArrayList<LogicNode>();

		Collection<Prefix> prefs = qp.getPrefixes();

		Collection<Restrictable> speciesFormsKeywords = getSpeciesFormsKeywords();
		LogicNode speciesTree = NodeFilter.filterKeywords(qp.getRestrictsTree(), speciesFormsKeywords);

		Collection<VSSPrefix> speciesPrefixes = EnumSet.of(VSSPrefix.COLLIDER,VSSPrefix.TARGET,VSSPrefix.PRODUCT,VSSPrefix.REACTANT);

		for (Prefix pref:prefs){
			if (speciesPrefixes.contains(pref.getPrefix())){
				subtrees.add(NodeFilter.filterPrefix(speciesTree, pref));
			}
		}

		LogicNode unprefixed = (NodeFilter.filterPrefix(speciesTree, new Prefix(null,0)));
		if (unprefixed!=null && unprefixed.getOperator().equals(Operator.OR)){
			for (Object sub :unprefixed.getValues()){
				subtrees.add((LogicNode) sub);
			}
		}else if (unprefixed!=null){
			subtrees.add(unprefixed);
		}
		return subtrees;
	}

	private static Collection<Restrictable> getSpeciesFormsKeywords() {
		Collection<Restrictable> speciesFormsKeywords = EnumSet.noneOf(Restrictable.class);
		for (Form speciesForm:getAllNewSpeciesForms()){
			speciesFormsKeywords.addAll (speciesForm.getSupportedKeywords());
		}
		return speciesFormsKeywords;
	}

	private static void addSingleForm(QueryData queryData, Query qp, Form form) {
		if (loadForm(form,qp.getRestrictsTree())>0)
			queryData.addForm(form);
	}
	
	private static int loadForm(Form newForm, LogicNode subtree) {
		LogicNode formTree = NodeFilter.filterKeywords(subtree, newForm.getSupportedKeywords());
		if (formTree!=null)
			return newForm.loadFromQuery(formTree);
		return 0;
	}
	
}
