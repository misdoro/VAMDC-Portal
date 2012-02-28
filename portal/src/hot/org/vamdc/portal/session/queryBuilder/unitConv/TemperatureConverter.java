package org.vamdc.portal.session.queryBuilder.unitConv;

public class TemperatureConverter extends AbstractUnitConverter{

	private static final long serialVersionUID = 8886404808744416699L;

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
		
		public String getDisplay(){ return this.display; }
		public Convert[] getValues() { return TempConvert.values(); }
		
		public Double convert(Double value){
			if (convert == null || value==null)
				return value;
			return convert.convert(value);
		}

		
	}
	
	public TemperatureConverter(){
		super(TempConvert.KELVIN);
	}
	
}
