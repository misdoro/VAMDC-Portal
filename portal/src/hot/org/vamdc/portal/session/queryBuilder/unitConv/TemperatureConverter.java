package org.vamdc.portal.session.queryBuilder.unitConv;

public class TemperatureConverter extends AbstractUnitConverter{

	enum TempConvert implements AbstractUnitConverter.Convert{
		KELVIN("K",javax.measure.unit.SI.KELVIN.getConverterTo(javax.measure.unit.SI.KELVIN)),
		CELSIUS("C",javax.measure.unit.SI.CELSIUS.getConverterTo(javax.measure.unit.SI.KELVIN)),
		FARENHEIT("F",javax.measure.unit.NonSI.FAHRENHEIT.getConverterTo(javax.measure.unit.SI.KELVIN)),
		;
		
		private String display;
		private javax.measure.converter.UnitConverter convert;
		
		TempConvert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
		}
		
		public String getDisplay(){
			return this.display;
		}
		
		public Convert valueOfShort(String value){
			for (Convert opt:TempConvert.values()){
				if (opt.getDisplay().equals(value))
						return opt;
			}
			return null;
		}
		
		public Double convert(Double value){
			if (convert == null || value==null)
				return value;
			return convert.convert(value);
		}

		public Convert[] getValues() {
			return TempConvert.values();
		}
		
	}
	
	public TemperatureConverter(){
		super(TempConvert.KELVIN);
	}
	
}
