package com.exmachina;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String username;
	private String surname;

	
	public User() { 
		User saved = getWizard().getLastUserSave();
		if (saved == null) {
			this.setId(new Date().toString());
		} else {
			this.id = saved.getId();
			this.name = saved.getName();
			this.surname = saved.getSurname();
			this.username = saved.getUsername();	
		}
	}
	
	public void saveUserOnSession(AjaxBehaviorEvent event) {
		System.out.println("Saving User");
		getWizard().setUser(this);
		System.out.println(this.name);
		System.out.println(this.username);
	}
	
	public String goToWizardPage(String page) {
		if (page.equals("end")) {
			getWizard().endAfterNextPage(this);
		} else {
			getWizard().navigateToNextPage(this);	
		}
		return page + "?faces-redirect=true";
	}

	public void checkIfYouHaveToRestartWizard() throws IOException 
	{
		if (this.username == null)
			FacesContext.getCurrentInstance().
				getExternalContext().redirect("WizardStart.jsf");
    }
	
	public static Wizard getWizard() {
		return findBean("wizard");
	}
	
	@SuppressWarnings({"unchecked"})
	public static <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
