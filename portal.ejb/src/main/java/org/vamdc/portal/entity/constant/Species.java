package org.vamdc.portal.entity.constant;

public enum Species {
	ATOM(1),
	MOLECULE(2);
	
	private Integer id;
	
	Species(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}	
}
