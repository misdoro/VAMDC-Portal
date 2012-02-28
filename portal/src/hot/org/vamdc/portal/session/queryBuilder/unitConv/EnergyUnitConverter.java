package org.vamdc.portal.session.queryBuilder.unitConv;

import org.vamdc.portal.session.queryBuilder.unitConv.CustomConverters.Converter;

public class EnergyUnitConverter extends AbstractUnitConverter{
	
	private static final long serialVersionUID = 7036929222571544931L;
	enum EnergyConvert implements AbstractUnitConverter.Convert{
		WL("1/cm",CustomConverters.WnToWn()),
		EV("eV",CustomConverters.EVToWn()),
		RYD("ryd",CustomConverters.RydToWn()),
		;

		
		EnergyConvert(String display,Converter converter){
			this.display = display;
			this.convert = converter;
		}
		
		private Converter convert;
		private String display;
		public String getDisplay() { return display; }
		public Convert[] getValues() {return EnergyConvert.values();}

		public Double convert(Double value) {
			return convert.convert(value);
		}


		public Convert valueOfShort(String value) {
			
			return null;
		}
		
	}
	
	public EnergyUnitConverter(){
		super(EnergyConvert.WL);
	}
	
}
