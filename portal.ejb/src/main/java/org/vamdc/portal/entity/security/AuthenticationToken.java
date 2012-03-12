package org.vamdc.portal.entity.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.annotations.security.TokenUsername;
import org.jboss.seam.annotations.security.TokenValue;

@Entity
public class AuthenticationToken implements Serializable {  

	private static final long serialVersionUID = 3843639852459603166L;
	
	private Integer tokenId;
	private String username;
	private String value;

	@Id @GeneratedValue
	public Integer getTokenId() { return tokenId; }
	public void setTokenId(Integer tokenId) { this.tokenId = tokenId; }

	@TokenUsername
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	@TokenValue
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }

}
