package org.vamdc.portal.entity.security;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.annotations.security.management.RoleName;

@Entity
public class Role {
  private Integer roleId;
  private String rolename;
  
  @Id @GeneratedValue
  public Integer getRoleId() { return roleId; }
  public void setRoleId(Integer roleId) { this.roleId = roleId; }
  
  @RoleName
  public String getRolename() { return rolename; }
  public void setRolename(String rolename) { this.rolename = rolename; }
}