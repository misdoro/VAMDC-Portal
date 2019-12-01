package org.vamdc.portal.entity.constant;

public enum Markup {
	TEXT(1),
	HTML(2),
	RST(3),
	LATEX(4);
	
	private Integer id;
	
	Markup(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;		
	}
	
}
