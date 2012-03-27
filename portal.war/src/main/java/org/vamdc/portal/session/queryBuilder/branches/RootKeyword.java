package org.vamdc.portal.session.queryBuilder.branches;

import org.vamdc.dictionary.DataType;
import org.vamdc.dictionary.Keyword;

public class RootKeyword implements Keyword{
	@Override
	public DataType getDataType() { return DataType.String; }
	@Override
	public String getDescription() { return "Root of the tree"; }
	@Override
	public String getInfo() { return "Root"; }
	@Override
	public String getUnits() { return ""; }
	@Override
	public String name() { return ""; }

}
