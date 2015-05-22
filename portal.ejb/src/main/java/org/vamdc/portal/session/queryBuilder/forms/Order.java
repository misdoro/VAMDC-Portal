package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Comparator;

public class Order implements Comparator<Form>{
	public final static Integer Atoms=1;
	public final static Integer Molecules=2;
	public final static Integer Particles=3;
	public final static Integer SPECIES_LIMIT=4;//Species go below this number
	
	final static Integer Environment=7;

	public final static Integer SINGLE_LIMIT=10;//Forms above this number can occur more than once
	public final static Integer Process = 12;
	public final static Integer Util = 15;
	public final static Integer Branches = 18;
	public final static Integer Query = 19;
	public static final Integer Comments = 20;
	
	public static final Integer GuidedRequestType =22;
	public static final Integer GuidedSpeciesType =23;
	public static final Integer GuidedRoot = 24;
	public final static Integer GuidedRadiative = 25;
	public static final Integer GuidedStates = 26;
	public final static Integer GuidedCollision = 27;
	public final static Integer GuidedCollisionConfiguration = 28;
	
	static final Integer Async = 21;
	
	@Override
	public int compare(Form o1, Form o2) {
		if (o1==null || o2==null)
			return 0;
		if (o1.getOrder()!=o2.getOrder())
			return o1.getOrder().compareTo(o2.getOrder());
		return o1.getPosition().compareTo(o2.getPosition());
	}
}
