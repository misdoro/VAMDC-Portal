package org.vamdc.portal.session.queryBuilder.unitConv;


public class CustomConverters {

	interface Converter{
		public Double convert(Double value);
	}
	
	class WnToWnConverter implements Converter{
		public Double convert(Double value) { return value; }
	}
	public static WnToWnConverter WnToWn(){ return new CustomConverters().new WnToWnConverter(); }
	
	class EVToWnConverter implements Converter{
		public Double convert(Double value) { return 8065.54429*value; }
	}
	public static EVToWnConverter EVToWn(){ return new CustomConverters().new EVToWnConverter(); }
	
	class RydToWnConverter implements Converter{
		public Double convert(Double value) { return 109737.31568539*value; }
	}
	public static RydToWnConverter RydToWn(){ return new CustomConverters().new RydToWnConverter(); }
	
}
