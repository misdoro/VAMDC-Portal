package org.vamdc.portal.session.queryBuilder.unitConv;

public class PressureUnitConverter extends AbstractUnitConverter{

	private static final long serialVersionUID = -8696428690669550365L;

	enum PressConvert implements AbstractUnitConverter.EnumConverter{
		BAR("bar",javax.measure.unit.NonSI.BAR.getConverterTo(javax.measure.unit.SI.PASCAL)),
		PASCAL("Pa",null),
		TORR("torr",new TorrToPascalConverter()),
		;
		
		
		
		private String display;
		private javax.measure.converter.UnitConverter convert;
		
		PressConvert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
		}
		
		@Override
		public String getDisplay(){
			return this.display;
		}
		
		
		
		@Override
		public Double convert(Double value){
			if (convert == null || value==null)
				return value;
			return convert.convert(value);
		}

		@Override
		public EnumConverter[] getValues() {
			return PressConvert.values();
		}
		
	}
	
	public PressureUnitConverter() {
		super(PressConvert.PASCAL);
	}

}
