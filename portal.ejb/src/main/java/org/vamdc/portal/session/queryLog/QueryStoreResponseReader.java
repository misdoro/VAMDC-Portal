package org.vamdc.portal.session.queryLog;

import org.json.JSONException;
import org.json.JSONObject;

public class QueryStoreResponseReader {
	
	public final static String SUCCESSFULL_REQUEST = "UUIDCorrectlyAssociated";
	public final static String HEAD_ASSOCIATION_FAILED = "UUIDInErrorOnHeadQuery";
	public final static String GET_ASSOCIATION_FAILED = "UUIDInErrorOnGetQuery";

	public final static QueryStoreResponse parseResponse(String json) throws JSONException{
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
