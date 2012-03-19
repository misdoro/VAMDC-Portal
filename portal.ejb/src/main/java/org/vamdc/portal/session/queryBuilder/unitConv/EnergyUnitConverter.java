package org.vamdc.portal.session.queryBuilder.unitConv;

import org.vamdc.portal.session.queryBuilder.unitConv.Converter;

public class EnergyUnitConverter extends AbstractUnitConverter{
	
	private static final long serialVersionUID = 7036929222571544931L;
	enum EnergyConvert implements AbstractUnitConverter.EnumConverter{
		WL("1/cm",CustomConverters.Direct()),
		EV("eV",CustomConverters.EVToWn()),
		RYD("ryd",CustomConverters.RydToWn()),
		;

		
		EnergyConvert(String display,Converter converter){
			this.display = display;
			this.convert = converter;
		}
		
		private Converter convert;
		private String display;
		@Override
		public String getDisplay() { return display; }
		@Override
		public EnumConverter[] getValues() {return EnergyConvert.values();}

		@Override
		public Double convert(Double value) {
			return convert.convert(value);
		}


		public EnumConverter valueOfShort(String value) {
			
			return null;
		}
		
	}
	
	public EnergyUnitConverter(){
		super(EnergyConvert.WL);
	}
	
}
