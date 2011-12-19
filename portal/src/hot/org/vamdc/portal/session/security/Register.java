package org.vamdc.portal.session.security;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.IdentityManagementException;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.JpaIdentityStore;
import org.jboss.seam.international.StatusMessages;
import org.vamdc.portal.entity.security.User;

@Name("Register")
public class Register
{
	@Logger private Log log;

	@In private StatusMessages statusMessages;
	@In private IdentityManager identityManager;
	@In private EntityManager entityManager;


	private String username;
	private String password;
	private String verifyPassword;
	private String email;

	public void register()
	{
		try {
			new RunAsOperation() {
				public void execute() {
					identityManager.createUser(username,password);
				}
			}.addRole("admin").run();
			statusMessages.add("User #0 registered successfully with the password #1.",
				username, password);
		} catch (IdentityManagementException e) {
			statusMessages.add(e.getMessage());
		}
		// implement your business logic here
		log.info("Register.register() action called with: #{Register.value}");
		statusMessages.add("register #{Register.value}");
	}


	@Observer(JpaIdentityStore.EVENT_PRE_PERSIST_USER)
	public void onPrePersist(User user) {
		log.info("***** Pre-persist observer called. Setting user properties *****");
		user.setEmail("test");
		log.info("***** User properties set *****");
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getVerifyPassword() {
		return verifyPassword;
	}


	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

}
