package org.vamdc.portal.session.queryLog;

import org.json.JSONException;
import org.json.JSONObject;

public class QueryStoreResponseReader {

	public static final String SUCCESSFULL_REQUEST = "UUIDCorrectlyAssociated";
	public static final String HEAD_ASSOCIATION_FAILED = "UUIDInErrorOnHeadQuery";
	public static final String GET_ASSOCIATION_FAILED = "UUIDInErrorOnGetQuery";
	
	private QueryStoreResponseReader(){}

	public static final QueryStoreResponse parseResponse(String json) {
		JSONObject jsonObject = new JSONObject(json);		
		
		if(jsonObject.keySet().contains(SUCCESSFULL_REQUEST))
			return new QueryStoreResponse(QueryStoreResponse.STATUS_SUCCESS, jsonObject.getString(SUCCESSFULL_REQUEST), "");
		else if(jsonObject.keySet().contains(HEAD_ASSOCIATION_FAILED))
			return new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, jsonObject.getString(SUCCESSFULL_REQUEST), "Head association failed");
		else if(jsonObject.keySet().contains(GET_ASSOCIATION_FAILED))
			return new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, jsonObject.getString(SUCCESSFULL_REQUEST), "Get association failed");
		else
			throw new JSONException("Unknown field in JSON object");		
	}
	
}
