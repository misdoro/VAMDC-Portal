package org.vamdc.portal.session.queryBuilder.unitConv;

public class PressureUnitConverter extends AbstractUnitConverter{
	
	
	
	enum PressConvert implements AbstractUnitConverter.Convert{
		BAR("bar",javax.measure.unit.NonSI.BAR.getConverterTo(javax.measure.unit.SI.PASCAL)),
		PASCAL("Pa",javax.measure.unit.SI.PASCAL.getConverterTo(javax.measure.unit.SI.PASCAL)),
		TORR("torr",new TorrToPascalConverter()),
		;
		
		
		
		private String display;
		private javax.measure.converter.UnitConverter convert;
		
		PressConvert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
		}
		
		public String getDisplay(){
			return this.display;
		}
		
		
		
		public Double convert(Double value){
			if (convert == null || value==null)
				return value;
			return convert.convert(value);
		}

		public Convert[] getValues() {
			return PressConvert.values();
		}
		
	}
	
	public PressureUnitConverter() {
		super(PressConvert.PASCAL);
	}

}
