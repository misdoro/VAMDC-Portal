package org.vamdc.portal.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.vamdc.portal.entity.species.VamdcSpecies;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm.MoleculeInfo;

/**
 * Collection of facade methods used to look up species database, 
 * interface is database structure agnostic since species database is a subject to change
 * @author doronin
 *
 */
public class EntityFacade {

	public static Collection<String> suggestMoleculeStoichiometricFormula(
			EntityManager entityManager, String value) {
		if (!checkValue(value) || entityManager==null)
			return Collections.emptyList();

		return EntityQuery.suggestMoleculeStoichForm(entityManager, value.trim());
	}


	public static Collection<String> suggestMoleculeOrdinaryFormula(
			EntityManager entityManager, String value) {
		if (!checkValue(value) || entityManager==null)
			return Collections.emptyList();

		return EntityQuery.suggestMoleculeStructForm(entityManager, value);
	}


	/*public static Collection<String> suggestChemicalName(
			EntityManager entityManager, String value) {
		if (!checkValue(value) || entityManager==null)
			return Collections.emptyList();

		return EntityQuery.suggestMoleculeName(entityManager, value.trim());
	}*/
	
	public static Collection<String> suggestMoleculeName(
			EntityManager entityManager, String value) {
		if (!checkValue(value) || entityManager==null)
			return Collections.emptyList();

		return EntityQuery.suggestMoleculeName(entityManager, value.trim());
	}


	public static List<MoleculeInfo> loadMoleculesFromName(EntityManager em,String value){
		String query = "SELECT distinct vs FROM VamdcSpecies vs " +
				"INNER JOIN vs.vamdcSpeciesNameses vsn " +
				"WHERE vsn.name = :Value " +
				"AND vs.speciesType = 2 ";
		return loadElements(em,query,value);
	}


	public static List<MoleculeInfo> loadMoleculesFromStoichForm(EntityManager em,String value){
		String query = "SELECT distinct vs FROM VamdcSpecies vs " +
				"WHERE vs.stoichiometricFormula = :Value";
		return loadElements(em,query,value);
	}

	public static List<MoleculeInfo> loadMoleculesFromOrdForm(EntityManager em, String value) {
		String query = "SELECT distinct vs FROM VamdcSpecies vs " +
				"INNER JOIN vs.vamdcSpeciesStructFormulaes vsf "   +
				"WHERE vsf.formula = :Value";
		return loadElements(em,query,value);
	}

	private static boolean checkValue(String value){
		return (value!=null && value.length()>0 && value.trim().length()>0);
	}

	private static List<MoleculeInfo> loadElements(EntityManager em, String query,String substitute){
		List<MoleculeInfo> result = new ArrayList<MoleculeInfo>();

		for(VamdcSpecies vs:doQuery(em, query, substitute)){
			result.add(new VamdcSpeciesFacade(vs));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static List<VamdcSpecies> doQuery(EntityManager em, String query, String substitute) {
		return em.createQuery(query).setParameter("Value", substitute).getResultList();
	}

}
