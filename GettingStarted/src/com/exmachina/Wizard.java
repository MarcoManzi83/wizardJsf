package com.exmachina;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Wizard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TO_END = "TO_END";
	private final String TRANSIENT = "TRANSIENT";
	private final String IN_WIZARD_PAGE = "IN_PAGE";
	
	private String userIdForNextPage;
	private String state = "";
	private Map<String, User> objectInWizard = new HashMap<String, User>();

	public String startWizard() 
	{
		return "home?faces-redirect=true";	
	}
	
	public void setUser(User user) 
	{
		this.objectInWizard.put(user.getId(), user);
	}
	
	synchronized public void navigateToNextPage(User user) 
	{
		setUser(user);
		userIdForNextPage = user.getId();
		state = TRANSIENT;
	}
	
	synchronized public void endAfterNextPage(User user) {
		setUser(user);
		userIdForNextPage = user.getId();
		state = TO_END;
	}

	public User getLastUserSave() 
	{
		if (state.equals(TO_END)) {
			state = IN_WIZARD_PAGE;
			User toReturn = this.objectInWizard.get(userIdForNextPage);
			this.objectInWizard.remove(userIdForNextPage);
			return toReturn;
		} else if (state.equals(TRANSIENT)) {
			state = IN_WIZARD_PAGE;
			return this.objectInWizard.get(userIdForNextPage);
		} else
			return null;
	}

	
}
