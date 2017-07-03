package org.vamdc.portal.session.queryBuilder.unitConv;

import static javax.measure.unit.SI.METRE;

import javax.measure.unit.NonSI;

public class WavelengthUnitConverter extends AbstractUnitConverter{

	
	
	public WavelengthUnitConverter() {
		super(WavelengthConvert.ANGSTROM);
	}

	private static final long serialVersionUID = 3599889437685237848L;
	
	enum WavelengthConvert implements AbstractUnitConverter.EnumConverter{
		
		ANGSTROM("A",NonSI.ANGSTROM.getConverterTo(NonSI.ANGSTROM)),
		NM("nm",METRE.times(1e-9).getConverterTo(NonSI.ANGSTROM)),
		UM("um",METRE.times(1e-6).getConverterTo(NonSI.ANGSTROM)),
		MM("mm",METRE.times(1e-3).getConverterTo(NonSI.ANGSTROM)),
		;

		WavelengthConvert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
			javax.measure.converter.UnitConverter c = METRE.times(1e-3).getConverterTo(NonSI.ANGSTROM);
		}
		
		private javax.measure.converter.UnitConverter convert;
		private String display;
		@Override
		public String getDisplay() { return display; }

		@Override
		public Double convert(Double value) { 		
			// return int value of double if value is an integer
			// to prevent trailing decimal values (i.e 1.00000000001)
			if((value - Math.round(value)) == 0 ){
				return new Double(Math.round(convert.convert(value)));
			}else{
				return convert.convert(value);
			}
		}

		@Override
		public EnumConverter[] getValues() { return WavelengthConvert.values(); }
		
	}
	
}
