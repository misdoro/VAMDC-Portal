package org.vamdc.portal.entity;

import java.util.Collection;

import javax.persistence.EntityManager;

/**
 * Collection of static methods to get some species objects
 * @author doronin
 *
 */
class EntityQuery{

	@SuppressWarnings("unchecked")
	static Collection<String> suggestSpeciesName(EntityManager em,
			String name) {
		return em.createQuery("SELECT distinct vsn.name from VamdcSpeciesNames vsn " +
				"WHERE vsn.name LIKE :molecName and vsn.vamdcMarkupTypes.id=1 order by length(vsn.name), vsn.searchPriority")
				.setParameter("molecName", "%"+name+"%")
				.setMaxResults(20)
				.getResultList();
				
	}

	@SuppressWarnings("unchecked")
	static Collection<String> suggestStoichForm(EntityManager em, String formula){
		return em.createQuery("SELECT distinct vs.stoichiometricFormula from VamdcSpecies vs " +
				"WHERE vs.stoichiometricFormula LIKE :formula order by length(vs.stoichiometricFormula)")
				.setParameter("formula", "%"+formula+"%")
				.setMaxResults(20)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	static Collection<String> suggestStructForm(EntityManager em, String formula){
		return em.createQuery("SELECT distinct vssf.formula from VamdcSpeciesStructFormulae vssf " +
				"WHERE vssf.formula LIKE :formula and vssf.vamdcMarkupTypes.id=1 order by length(vssf.formula), vssf.searchPriority")
				.setParameter("formula", "%"+formula+"%")
				.setMaxResults(20)
				.getResultList();
	}

	
}
