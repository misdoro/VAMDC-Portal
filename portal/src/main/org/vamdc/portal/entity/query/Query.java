package org.vamdc.portal.entity.query;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.vamdc.portal.entity.security.User;

/**
 * Saved query, user-specific, 
 * 
 */
@Entity
public class Query implements Serializable{

	private static final long serialVersionUID = -706403843777054424L;

	private Integer queryID;
	private String queryString;
	private String comments;
	private User user;
	private List<HttpHeadResponse> responses;
	
	@ManyToOne(optional=false) 
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	
	@Id @GeneratedValue
	public Integer getQueryID() { return queryID; }
	public void setQueryID(Integer queryID) { this.queryID = queryID; }

	@Lob
	public String getQueryString() { return queryString; }
	public void setQueryString(String queryString) { this.queryString = queryString; }
	
	@Lob
	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }
	
	
	@OneToMany(mappedBy="query")
	public List<HttpHeadResponse> getResponses() { return responses; }
	public void setResponses(List<HttpHeadResponse> responses) { this.responses = responses; }
	
}
