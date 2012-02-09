package org.vamdc.portal.session.queryBuilder;

import java.util.Comparator;

import org.vamdc.portal.session.queryBuilder.forms.Form;

class FormComparator implements Comparator<Form>{

	public int compare(Form o1, Form o2) {
		if (o1==null || o2==null)
			return 0;
		if (!o1.getPrefix().equals(o2.getPrefix()))
			return o1.getPrefix().compareTo(o2.getPrefix());
		if (!o1.getOrder().equals(o2.getOrder()))
			return o1.getOrder().compareTo(o2.getOrder());
		return o1.getId().compareTo(o2.getId());
	}

}
