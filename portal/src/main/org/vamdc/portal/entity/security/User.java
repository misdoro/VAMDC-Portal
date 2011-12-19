package org.vamdc.portal.entity.security;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = -7576699084782852262L;
	
	private Integer userId;
	private String username;
	private String passwordHash;
	private String email;
	private Set<Role> roles;
	

	@Id @GeneratedValue
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }

	@UserPrincipal
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	@UserPassword(hash = "sha")
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

	
	public String getEmail(){ return this.email; }
	public void setEmail(String email){ this.email = email;};

	
	@UserRoles
	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "UserRoles", 
	joinColumns = @JoinColumn(name = "UserId"),
	inverseJoinColumns = @JoinColumn(name = "RoleId"))
	public Set<Role> getRoles() { return roles; }
	public void setRoles(Set<Role> roles) { this.roles = roles; }

}