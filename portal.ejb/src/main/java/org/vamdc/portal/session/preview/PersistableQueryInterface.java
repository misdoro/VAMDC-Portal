package org.vamdc.portal.session.preview;

import java.util.List;

import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.security.User;
import org.vamdc.portal.session.queryBuilder.FormHolder;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryLog.QueryLog;

public interface PersistableQueryInterface {
	public List<HttpHeadResponse> getNodesResponse();
	public User getUser();
	public QueryLog getQueryLog();
	public QueryData getQueryData();
}
