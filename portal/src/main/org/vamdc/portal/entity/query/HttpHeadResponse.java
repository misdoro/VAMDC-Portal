package org.vamdc.portal.entity.query;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HttpHeadResponse implements Serializable{

	private static final long serialVersionUID = 7243615545353337911L;
	public enum Response{
		OK,
		EMPTY,
		FAIL,
		TIMEOUT,
		;
		
	}
	
	private String ivoaID;
	private String fullQueryURL;
	private Response status;
	private Query query;
	
	private Integer recordID;
	
	private int species;
	private int states;
	private int processes;
	private int radiative;
	private int nonRadiative;
	private int collisions;
	
	
	public HttpHeadResponse(){
	}
	
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

	public void setIvoaID(String ivoaID) {
		this.ivoaID = ivoaID;
	}

	public void setFullQueryURL(String fullQueryURL) {
		this.fullQueryURL = fullQueryURL;
	}

	public void setStatus(Response status) {
		this.status = status;
	}

	public void setSpecies(int species) {
		this.species = species;
	}

	public void setStates(int states) {
		this.states = states;
	}

	public void setProcesses(int processes) {
		this.processes = processes;
	}

	public void setRadiative(int radiative) {
		this.radiative = radiative;
	}

	public void setNonRadiative(int nonRadiative) {
		this.nonRadiative = nonRadiative;
	}

	public void setCollisions(int collisions) {
		this.collisions = collisions;
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="queryID")
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	@Id @GeneratedValue
	public Integer getRecordID() {
		return recordID;
	}

	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	
}
