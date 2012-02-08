package org.vamdc.portal.session.queryBuilder.unitConv;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.converter.UnitConverter;

class TorrToPascalConverter extends javax.measure.converter.UnitConverter{
	
	private static final long serialVersionUID = 3461046439784490042L;
	private final static double torrsInPascal=133.322387415;
	
	@Override
	public double convert(double torr) {
		return torr/torrsInPascal;
	}

	@Override
	public BigDecimal convert(BigDecimal arg0, MathContext arg1)
			throws ArithmeticException { throw new RuntimeException("not implemented");}

	@Override
	public boolean equals(Object arg0) { return false; }

	@Override
	public int hashCode() { return 0; }

	@Override
	public UnitConverter inverse() { return null; }

	@Override
	public boolean isLinear() { return true; }
}
