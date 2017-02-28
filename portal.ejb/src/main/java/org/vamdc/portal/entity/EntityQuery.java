package org.vamdc.portal.entity;

import java.util.Collection;
import javax.persistence.EntityManager;

/**
 * Collection of static methods to get some species objects
 * @author doronin
 *
 */
class EntityQuery{
	
	private EntityQuery(){}

	@SuppressWarnings("unchecked")
	static Collection<String> suggestSpeciesName(EntityManager em,
			String name) {
		return em.createQuery("SELECT distinct vsn.name from VamdcSpeciesNames vsn " +
				"WHERE vsn.name LIKE :molecName and vsn.vamdcMarkupTypes.id=1 order by length(vsn.name), vsn.searchPriority")
				.setParameter("molecName", "%"+name+"%")
				.setMaxResults(20)
				.getResultList();
				
	}
	
	static Collection<String> suggestAtomName(EntityManager em,
			String name) {
		return suggestRestrictedSpeciesName(em, name, 1);	
	
	}
	
	static Collection<String> suggestMoleculeName(EntityManager em,
			String name) {
		return suggestRestrictedSpeciesName(em, name, 2);	
	}
	
	@SuppressWarnings("unchecked")
	private static Collection<String> suggestRestrictedSpeciesName(EntityManager em,
			String name, Integer speciesType) {		
		return em.createQuery("SELECT DISTINCT vsn.name from VamdcSpeciesNames vsn JOIN vsn.vamdcSpecies vsp " +
				"WHERE vsn.name LIKE :speciesName and vsn.vamdcMarkupTypes.id=1 and vsp.speciesType=:speciesType "
				+ " order by length(vsn.name), vsn.searchPriority")
				.setParameter("speciesName", "%"+name+"%")
				.setParameter("speciesType", speciesType)
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


	static Collection<String> suggestMoleculeStoichForm(EntityManager em, String formula){
		return suggestRestrictedStoichForm(em, formula, 2);
	}
	
	@SuppressWarnings("unchecked")
	private static Collection<String> suggestRestrictedStoichForm(EntityManager em, String formula, Integer speciesType){
		return em.createQuery("SELECT distinct vs.stoichiometricFormula from VamdcSpecies vs " +
				"WHERE vs.stoichiometricFormula LIKE :formula AND vs.speciesType=:speciesType  order by length(vs.stoichiometricFormula)")
				.setParameter("formula", "%"+formula+"%")
				.setParameter("speciesType", speciesType)
				.setMaxResults(20)
				.getResultList();
	}

	static Collection<String> suggestMoleculeStructForm(EntityManager em, String formula){
		return suggestRestrictedStructForm(em, formula, 2);
	}
	
	@SuppressWarnings("unchecked")
	private static Collection<String> suggestRestrictedStructForm(EntityManager em, String formula, Integer speciesType){
		return em.createQuery("SELECT distinct vssf.formula from VamdcSpeciesStructFormulae vssf JOIN vssf.vamdcSpecies vs " +
				"WHERE vssf.formula LIKE :formula and vssf.vamdcMarkupTypes.id=1 and vs.speciesType=:speciesType order by length(vssf.formula), vssf.searchPriority")
				.setParameter("formula", "%"+formula+"%")
				.setParameter("speciesType", speciesType)
				.setMaxResults(20)
				.getResultList();
	}
	
}
