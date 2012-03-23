package org.vamdc.portal.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.vamdc.portal.entity.species.SpeciesIso;
import org.vamdc.portal.entity.species.SpeciesSpecies;
import org.vamdc.portal.entity.species.SpeciesSpeciesname;
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
		for (SpeciesSpecies species:EntityQuery.getSpeciesFromStoichFormulaWild(entityManager, value.trim())){
			result.add(species.getStoichiometricFormula());
		}
		return result;
	}


	public static Collection<String> suggestOrdinaryFormula(
			EntityManager entityManager, String value) {
		if (!checkValue(value))
			return Collections.emptyList();

		Collection<String> result = new ArrayList<String>();
		for (SpeciesSpecies species:EntityQuery.getSpeciesFromOrdinaryFormulaWild(entityManager, value.trim())){
			result.add(species.getOrdinaryFormula());
		}
		return result;
	}
	
	
	public static Collection<String> suggestChemicalName(
			EntityManager entityManager, String value) {
		if (!checkValue(value))
			return Collections.emptyList();

		Collection<String> result = new ArrayList<String>();
		for (SpeciesSpeciesname element:EntityQuery.getSpeciesFromNameWild(entityManager, value.trim())){
			result.add(element.getName());
		}
		return result;
	}
	
	
	private static boolean checkValue(String value){
		return (value!=null && value.length()>0 && value.trim().length()>0);
	}

	public static List<MoleculeInfo> loadMoleculesFromName(
			EntityManager entityManager, String value) {
		Integer speciesID = getSpeciesIDfromName(entityManager, value);
		return loadMolecules(entityManager,speciesID);
	}

	public static List<MoleculeInfo> loadMoleculesFromStoichForm(
			EntityManager entityManager, String value) {
		Integer molecID = getSpeciesIDfromStoichForm(entityManager, value);
		return loadMolecules(entityManager,molecID);
	}
	
	public static List<MoleculeInfo> loadMoleculesFromOrdForm(
			EntityManager entityManager, String value) {
		Integer molecID = getSpeciesIDfromOrdForm(entityManager, value);
		return loadMolecules(entityManager,molecID);
	}
	
	public static Integer getSpeciesIDfromName(EntityManager entityManager, String name){
		SpeciesSpeciesname molec = EntityQuery.getSpeciesFromName(entityManager, name);
		if (molec!=null)
			return molec.getSpeciesId();
		return 0;
	}
	
	public static Integer getSpeciesIDfromStoichForm(EntityManager entityManager, String formula){
		SpeciesSpecies molec = EntityQuery.getSpeciesFromStoichFormula(entityManager, formula);
		if (molec!=null)
			return molec.getId();
		return 0;
	}
	
	public static Integer getSpeciesIDfromOrdForm(EntityManager entityManager, String formula){
		SpeciesSpecies molec = EntityQuery.getSpeciesFromOrdFormula(entityManager, formula);
		if (molec!=null)
			return molec.getId();
		return 0;
	}
	
	private static List<MoleculeInfo> loadMolecules(EntityManager entityManager, Integer speciesID){
		ArrayList<MoleculeInfo> result = new ArrayList<MoleculeInfo>();
		for (Object iso:EntityQuery.getIsotopologuesByspeciesId(entityManager, speciesID)){
			result.add(new IsotopologueFacade((SpeciesIso) iso));
		}
		if (result.size()==0){
			MoleculeInfo species = EntityFacade.getMolecInfoFromID(entityManager, speciesID);
			if (species!=null)
				result.add(species);
		}
		return result;
	}


	public static MoleculeInfo getMolecInfoFromID(EntityManager entityManager,
			Integer speciesID) {
		SpeciesSpecies molecule = EntityQuery.getSpeciesFromID(entityManager, speciesID);
		if (molecule!=null)
			return new SpeciesFacade(molecule);
		return null;
	}
	
}
