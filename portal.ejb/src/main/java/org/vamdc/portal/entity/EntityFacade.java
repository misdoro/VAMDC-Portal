package org.vamdc.portal.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.vamdc.portal.entity.molecules.IsotopologueFacade;
import org.vamdc.portal.entity.molecules.Isotopologues;
import org.vamdc.portal.entity.molecules.MoleculeNames;
import org.vamdc.portal.entity.molecules.Molecules;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm.MoleculeInfo;

/**
 * Collection of facade methods used to look up species database, 
 * interface is database structure agnostic since species database is a subject to change
 * @author doronin
 *
 */
public class EntityFacade {

	public static Collection<String> suggestStoichiometricFormula(
			EntityManager entityManager, String value) {
		if (!checkValue(value))
			return Collections.emptyList();

		
		Collection<String> result = new ArrayList<String>();
		for (Molecules molecule:EntityQuery.getMolecsFromFormulaWild(entityManager, value.trim())){
			result.add(molecule.getStoichiometricFormula());
		}

		return result;
	}

	public static Collection<String> suggestChemicalName(
			EntityManager entityManager, String value) {
		if (!checkValue(value))
			return Collections.emptyList();

		Collection<String> result = new ArrayList<String>();
		for (MoleculeNames molecule:EntityQuery.getMolecsFromNameWild(entityManager, value.trim())){
			result.add(molecule.getMolecName());
		}
		return result;
	}
	
	private static boolean checkValue(String value){
		return (value!=null && value.length()>0 && value.trim().length()>0);
	}

	public static List<MoleculeInfo> loadMoleculesFromName(
			EntityManager entityManager, String value) {
		Integer molecID = getMolecIDfromName(entityManager, value);
		return loadMolecules(entityManager,molecID);
	}

	public static List<MoleculeInfo> loadMoleculesFromStoichForm(
			EntityManager entityManager, String value) {
		Integer molecID = getMolecIDfromStoichForm(entityManager, value);
		return loadMolecules(entityManager,molecID);
		
	}
	
	private static Integer getMolecIDfromName(EntityManager entityManager, String name){
		MoleculeNames molec = EntityQuery.getMolecNamesFromName(entityManager, name);
		if (molec!=null)
			return molec.getMolecId();
		return 0;
	}
	
	private static Integer getMolecIDfromStoichForm(EntityManager entityManager, String formula){
		Molecules molec = EntityQuery.getMoleculeFromFormula(entityManager, formula);
		if (molec!=null)
			return molec.getId();
		return 0;
	}
	
	
	private static List<MoleculeInfo> loadMolecules(EntityManager entityManager, Integer molecID){
		ArrayList<MoleculeInfo> result = new ArrayList<MoleculeInfo>();
		for (Object isotopologue:EntityQuery.getIsotopologuesByMolecId(entityManager, molecID)){
			result.add(new IsotopologueFacade((Isotopologues) isotopologue));
		}
		return result;
	}
	
}
