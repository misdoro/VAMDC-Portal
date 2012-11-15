package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Comparator;

public class Order implements Comparator<Form>{
	final static Integer Atoms=1;
	final static Integer Molecules=2;
	final static Integer Particles=3;
	public final static Integer SPECIES_LIMIT=4;//Species go below this number
	
	final static Integer Environment=7;

	public final static Integer SINGLE_LIMIT=10;//Forms above this number can occur once
	final static Integer Process = 12;
	final static Integer Util = 15;
	final static Integer Branches = 18;
	final static Integer Query = 19;
	static final Integer Comments = 20;
	
	@Override
	public int compare(Form o1, Form o2) {
		if (o1==null || o2==null)
			return 0;
		if (o1.getOrder()!=o2.getOrder())
			return o1.getOrder().compareTo(o2.getOrder());
		return o1.getInsertOrder().compareTo(o2.getInsertOrder());
	}
}
