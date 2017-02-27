package org.vamdc.portal.entity.query;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class HttpHeadResponse implements Serializable{

	private static final long serialVersionUID = 7243615545353337914L;
	public enum Response{
		OK("Node has data corresponding the query"),
		TRUNCATED("Query is too unspecific for the node, output will be truncated"),
		EMPTY("Node has no data corresponding the query"),
		FAIL("Node failed to respond"),
		TIMEOUT("It took too long for the node to respond"),
		;

		private String description;
		Response(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return this.description;
		}
		
	}

	private String ivoaID;
	private String fullQueryURL;
	private Response status;
	private Date lastmodified;
	private Query query;
	private String requestToken;

	private Integer recordID;

	private int species;
	private int states;
	private int processes;
	private int radiative;
	private int nonRadiative;
	private int collisions;
	private int truncated;


	public HttpHeadResponse(){
	}

	public HttpHeadResponse(String ivoaID, HttpURLConnection connection) {
		this.ivoaID = ivoaID;
		this.fullQueryURL = connection.getURL().toString();
		this.status = retrieveStatus(connection);
		retrieveHeaders(connection);
		if (truncated>0 && truncated<100)
			this.status=Response.TRUNCATED;
	}


	private void retrieveHeaders(HttpURLConnection connection) {
		species = getValue(connection,"VAMDC-COUNT-SPECIES");
		states = getValue(connection,"VAMDC-COUNT-STATES");
		collisions = getValue(connection,"VAMDC-COUNT-COLLISIONS");
		radiative = getValue(connection,"VAMDC-COUNT-RADIATIVE");
		nonRadiative = getValue(connection,"VAMDC-COUNT-NONRADIATIVE");
		processes = collisions+radiative+nonRadiative;
		truncated = getTruncatedValue(connection,"VAMDC-TRUNCATED");
		lastmodified = extractLastModified(connection);
		requestToken = connection.getHeaderField("VAMDC-REQUEST-TOKEN");		
	}

	private Date extractLastModified(HttpURLConnection connection) {
		long lastmod = connection.getLastModified();
		if (lastmod>0)
			return new Date(lastmod);
		return null;
	}

	private int getValue( HttpURLConnection connection,String headerName){
		try{
			return Integer.valueOf(connection.getHeaderField(headerName));
		}catch (NumberFormatException e){
			return 0;
		}
	}
	
	private int getTruncatedValue( HttpURLConnection connection,String headerName ){
		try{
			String val = connection.getHeaderField(headerName);
			if (val==null || val.length()==0)
				return 0;
			String[] valPart = val.split("[\\s%]");
			if (valPart.length==0)
				return 0;
			String valStr = valPart[0].trim();
			Double valDouble = Double.valueOf(valStr);
			if (valDouble>=0. && valDouble<=1.)
				valDouble=1.;
			return valDouble.intValue();
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

	@Id @GeneratedValue
	public Integer getRecordID() { return recordID; }
	public void setRecordID(Integer recordID) { this.recordID = recordID; }
	
	public String getIvoaID() { return ivoaID; }
	public void setIvoaID(String ivoaID) { this.ivoaID = ivoaID; }
	
	public Response getStatus() { return status; }
	public void setStatus(Response status) { this.status = status; }
	@Transient
	public String getStatusDescription() {return status.getDescription();}
	@Transient
	public Date getModifyDate(){
		return this.lastmodified;
	}
	@Transient
	public boolean isLastModSet(){
		return (this.lastmodified!=null);
	}

	public int getSpecies() { return species; }	
	public int getStates() { return states; }
	public int getProcesses() { return processes; }
	public int getRadiative() { return radiative; }
	public int getNonRadiative() { return nonRadiative; }
	public int getCollisions() { return collisions; }
	public String getRequestToken(){ return requestToken; }
	
	@Transient
	public int getTruncated() { return truncated; }
	
	public void setSpecies(int species) { this.species = species; }
	public void setStates(int states) { this.states = states; }
	public void setProcesses(int processes) { this.processes = processes; }
	public void setRadiative(int radiative) { this.radiative = radiative; }
	public void setNonRadiative(int nonRadiative) { this.nonRadiative = nonRadiative; }
	public void setCollisions(int collisions) { this.collisions = collisions; }
	public void setRequestToken(String token){ this.requestToken = token; }
	
	@Lob 
	public String getFullQueryURL() { return fullQueryURL; }
	public void setFullQueryURL(String fullQueryURL) { this.fullQueryURL = fullQueryURL; }


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="queryID")
	public Query getQuery() { return query; }
	public void setQuery(Query query) { this.query = query; }

	@Transient
	public boolean isOk(){
		return this.status==Response.OK || this.status == Response.TRUNCATED;
	}
}
