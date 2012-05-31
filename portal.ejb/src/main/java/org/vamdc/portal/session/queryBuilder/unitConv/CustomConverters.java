package org.vamdc.portal.session.queryBuilder.unitConv;

import java.io.Serializable;

/**
 * Custom simple converters, used both in in-field unit conversions and inter-field conversions 
 *
 *
 */
@SuppressWarnings("serial")
public class CustomConverters implements Serializable{
	
	private final static Double C = 2.99792458e5;
	

	class DirectConverter implements Converter{
		@Override
		public Double convert(Double value) { return value; }
		@Override
		public boolean isInverting(){ return false;};
	}
	public static DirectConverter Direct(){ return new CustomConverters().new DirectConverter(); }
	
	class EVToWnConverter implements Converter{
		@Override
		public Double convert(Double value) { return 8065.54429*value; }
		@Override
		public boolean isInverting(){ return false;};
	}
	public static EVToWnConverter EVToWn(){ return new CustomConverters().new EVToWnConverter(); }
	
	class RydToWnConverter implements Converter{
		@Override
		public Double convert(Double value) { return 109737.31568539*value; }
		@Override
		public boolean isInverting(){ return false;};
	}
	public static RydToWnConverter RydToWn(){ return new CustomConverters().new RydToWnConverter(); }
	
	class FreqToWavelengthConverter implements Converter{
		@Override
		public Double convert(Double value) {
			if (value!=null && value!=0)
				return C*1e7/value;
			else return Double.NaN;
		}
		@Override
		public boolean isInverting(){ return true;};
	}
	
	public static FreqToWavelengthConverter MHzToWl(){ return new CustomConverters().new FreqToWavelengthConverter(); }
	
	class WavenumberToWavelengthConverter implements Converter{
		@Override
		public Double convert(Double value) {
			if (value!=null && value!=0)
				return 1e8/value;
			else return Double.NaN;
		}
		@Override
		public boolean isInverting(){ return true;};

	}
	
	public static WavenumberToWavelengthConverter WnToWl(){ return new CustomConverters().new WavenumberToWavelengthConverter(); }
	
}
