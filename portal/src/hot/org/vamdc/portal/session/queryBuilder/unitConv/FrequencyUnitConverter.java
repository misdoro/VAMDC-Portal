package org.vamdc.portal.session.queryBuilder.unitConv;

import javax.measure.unit.SI;

public class FrequencyUnitConverter extends AbstractUnitConverter{

	
	
	public FrequencyUnitConverter() {
		super(FrequencyConvert.HZ);
	}

	private static final long serialVersionUID = 3599889437685237848L;
	
	enum FrequencyConvert implements AbstractUnitConverter.EnumConverter{
		HZ("Hz",SI.HERTZ.getConverterTo(SI.HERTZ.times(10e6))),
		KHz("KHz",SI.HERTZ.times(10e3).getConverterTo(SI.HERTZ.times(10e6))),
		MHz("MHz",SI.HERTZ.times(10e6).getConverterTo(SI.HERTZ.times(10e6))),
		GHz("GHz",SI.HERTZ.times(10e9).getConverterTo(SI.HERTZ.times(10e6))),
		THz("THz",SI.HERTZ.times(10e12).getConverterTo(SI.HERTZ.times(10e6))),
		;

		FrequencyConvert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
		}
		
		private javax.measure.converter.UnitConverter convert;
		private String display;
		public String getDisplay() { return display; }

		public Double convert(Double value) { return convert.convert(value); }

		public EnumConverter[] getValues() { return FrequencyConvert.values(); }
		
		
	}
	
}
