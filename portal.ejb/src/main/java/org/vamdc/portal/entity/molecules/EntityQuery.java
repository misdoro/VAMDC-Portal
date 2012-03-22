package org.vamdc.portal.entity.molecules;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.EntityManager;

/**
 * Collection of static methods to get some species objects
 * @author doronin
 *
 */
public class EntityQuery{
	public static Molecules getMoleculeFromFormula(EntityManager em, String formula){
		if (em==null) return null;
		return (Molecules) em.createNamedQuery("Molecules.findByStoichiometricFormula")
		.setParameter("stoichiometricFormula", formula)
		.getSingleResult();
	}
	
	public static MoleculeNames getMolecNamesFromName(EntityManager em, String name){
		if (em==null) return null;
		return (MoleculeNames) em.createNamedQuery("MoleculeNames.findByMolecName")
		.setParameter("molecName", name).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Isotopologues> getIsotopologuesByMolecId(EntityManager em,Integer molecID){
		if (em==null) return Collections.emptyList();
		return em.createNamedQuery("Isotopologues.findByMolecID")
		.setParameter("molecId", molecID)
		.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<MoleculeNames> getMolecsFromNameWild(EntityManager em, String name){
		if (em==null) return Collections.emptyList();
		return em.createNamedQuery("MoleculeNames.findByMolecNameWildcard")
		.setParameter("molecName", "%"+name+"%")
		.setMaxResults(20)
		.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Molecules> getMolecsFromFormulaWild(EntityManager em, String formula){
		if (em==null) return Collections.emptyList();
		return em.createNamedQuery("Molecules.findByStoichiometricFormulaWildcard")
		.setParameter("stoichiometricFormula", "%"+formula+"%")
		.setMaxResults(20)
		.getResultList();
	}
}
