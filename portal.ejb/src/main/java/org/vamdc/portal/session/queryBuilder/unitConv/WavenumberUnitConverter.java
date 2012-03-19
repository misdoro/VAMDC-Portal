package org.vamdc.portal.session.queryBuilder.unitConv;

public class WavenumberUnitConverter extends AbstractUnitConverter{

	
	
	public WavenumberUnitConverter() {
		super(WavenumberConvert.CM1);
	}

	private static final long serialVersionUID = 3599889437685237848L;
	
	enum WavenumberConvert implements AbstractUnitConverter.EnumConverter{
		CM1("cm-1"),
		;

		WavenumberConvert(String display){
			this.display = display;
		}
		
		private String display;
		@Override
		public String getDisplay() { return display; }

		@Override
		public Double convert(Double value) { return value; }

		@Override
		public EnumConverter[] getValues() { return WavenumberConvert.values(); }
		
		
	}
	
}
