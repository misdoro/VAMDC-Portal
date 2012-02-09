package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Comparator;

public class Order implements Comparator<Form>{
	final static Integer Atoms=1;
	final static Integer Molecules=2;
	final static Integer Particles=3;
	public final static Integer SPECIES_LIMIT=4;
	
	final static Integer Environment=7;
	final static Integer Transitions=5;
	final static Integer Collisions=6;
	
	public int compare(Form o1, Form o2) {
		if (o1==null || o2==null)
			return 0;
		if (o1.getOrder()!=o2.getOrder())
			return o1.getOrder().compareTo(o2.getOrder());
		return o1.getId().compareTo(o2.getId());
	}
}
