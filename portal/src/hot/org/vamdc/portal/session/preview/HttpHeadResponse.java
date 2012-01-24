package org.vamdc.portal.session.preview;

import java.io.IOException;
import java.net.HttpURLConnection;


public class HttpHeadResponse {

	public enum Response{
		OK,
		EMPTY,
		FAIL,
		TIMEOUT,
		;
		
	}
	
	public enum Field{
		Species,
		States,
		Processes,
		Radiative,
		Collisions,
		NonRadiative,
		
		;
	}
	
	private String ivoaID;
	private String fullQueryURL;
	private Response status;
	
	private int species;
	private int states;
	private int processes;
	private int radiative;
	private int nonRadiative;
	private int collisions;
	
	
	public HttpHeadResponse(String ivoaID, HttpURLConnection connection) {
		this.ivoaID = ivoaID;
		this.fullQueryURL = connection.getURL().toString();
		this.status = retrieveStatus(connection);
		retrieveHeaders(connection);
		
	}
	
	
	private void retrieveHeaders(HttpURLConnection connection) {
		species = getValue(connection,"VAMDC-COUNT-SPECIES");
		states = getValue(connection,"VAMDC-COUNT-STATES");
		collisions = getValue(connection,"VAMDC-COUNT-COLLISIONS");
		radiative = getValue(connection,"VAMDC-COUNT-RADIATIVE");
		nonRadiative = getValue(connection,"VAMDC-COUNT-NONRADIATIVE");
		processes = collisions+radiative+nonRadiative;
		
	}

	private int getValue( HttpURLConnection connection,String headerName){
		try{
			return Integer.valueOf(connection.getHeaderField(headerName));
		}catch (NumberFormatException e){
			return 0;
		}
	}
	
	private Response retrieveStatus(HttpURLConnection connection){
		try {
			switch (connection.getResponseCode()){
			case HttpURLConnection.HTTP_OK:
				return Response.OK;
			case HttpURLConnection.HTTP_NO_CONTENT:
				return Response.EMPTY;
			default:
				return Response.FAIL;
			}
		} catch (IOException e) {
			return Response.TIMEOUT;
		}
	}
	
	
	public String getIvoaID() { return ivoaID; }
	public Response getStatus() { return status; }
	
	public int getSpecies() { return species; }
	public int getStates() { return states; }
	public int getProcesses() { return processes; }
	public int getRadiative() { return radiative; }
	public int getNonRadiative() { return nonRadiative; }
	public int getCollisions() { return collisions; }
	public String getFullQueryURL() { return fullQueryURL; }
	
}
