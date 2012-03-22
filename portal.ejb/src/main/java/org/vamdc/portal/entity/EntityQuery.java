package org.vamdc.portal.entity;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.EntityManager;

import org.vamdc.portal.entity.species.SpeciesIso;
import org.vamdc.portal.entity.species.SpeciesSpecies;
import org.vamdc.portal.entity.species.SpeciesSpeciesname;

/**
 * Collection of static methods to get some species objects
 * @author doronin
 *
 */
class EntityQuery{

	@SuppressWarnings("unchecked")
	static Collection<SpeciesSpeciesname> getSpeciesFromNameWild(EntityManager em, String name){
		if (em==null) return Collections.emptyList();
		return em.createQuery("SELECT s FROM SpeciesSpeciesname s WHERE s.name LIKE :molecName")
				.setParameter("molecName", "%"+name+"%")
				.setMaxResults(20)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	static Collection<SpeciesSpecies> getSpeciesFromStoichFormulaWild(EntityManager em, String formula){
		if (em==null) return Collections.emptyList();
		return em.createQuery("SELECT s FROM SpeciesSpecies s WHERE s.stoichiometricFormula LIKE :formula")
				.setParameter("formula", "%"+formula+"%")
				.setMaxResults(20)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	static Collection<SpeciesSpecies> getSpeciesFromOrdinaryFormulaWild(EntityManager em, String formula){
		if (em==null) return Collections.emptyList();
		return em.createQuery("SELECT s FROM SpeciesSpecies s WHERE s.ordinaryFormula LIKE :formula")
				.setParameter("formula", "%"+formula+"%")
				.setMaxResults(20)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	static Collection<SpeciesIso> getIsotopologuesByspeciesId(
			EntityManager em, Integer speciesID) {
		if (em==null) return Collections.emptyList();
		return em.createQuery("SELECT i from SpeciesIso i where i.speciesId = :speciesId")
				.setParameter("speciesId", speciesID)
				.getResultList();
	}

	static SpeciesSpeciesname getSpeciesFromName(
			EntityManager em, String name) {
		if (em==null) return null;
		return (SpeciesSpeciesname) em.createQuery("SELECT s FROM SpeciesSpeciesname s WHERE s.name = :molecName")
				.setParameter("molecName", name)
				.getSingleResult();
	}

	public static SpeciesSpecies getSpeciesFromStoichFormula(
			EntityManager em, String formula) {
		if (em==null) return null;
		return (SpeciesSpecies) em.createQuery("SELECT s FROM SpeciesSpecies s WHERE s.stoichiometricFormula = :formula")
				.setParameter("formula",formula)
				.getSingleResult();
	}

	public static SpeciesSpecies getSpeciesFromOrdFormula(
			EntityManager em, String formula) {
		if (em==null) return null;
		return (SpeciesSpecies) em.createQuery("SELECT s FROM SpeciesSpecies s WHERE s.ordinaryFormula = :formula")
				.setParameter("formula",formula)
				.getSingleResult();
	}
}
