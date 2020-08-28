/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.UserGroups;
import entities.Users;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shicheng Ai
 */

@ManagedBean
@SessionScoped
public class LoginView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginView.class.getName());
	//@EJB
        @EJB
	private UserInterfaceRemote userfaces;
        
	private String email;
	private String password;
	private Users user;
        private UserGroups userGroup;
        private String ERRORMESSAGE;
        
	public String login() throws Exception {
                ERRORMESSAGE = "";
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.login(email, password);
		} catch (ServletException e) {
//			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
                        ERRORMESSAGE = "Incorrect user or password!!!";
//			return "signin";
                        return "login";
		}
                
		Principal principal = request.getUserPrincipal();
		this.user = userfaces.findUserByEmail(principal.getName());
		log.info("Authentication done for user: " + principal.getName());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("User", user);
                // user role check
                this.userGroup = userfaces.findUserGroupByEmail(principal.getName());
		if (userGroup.getGroupname().equals("user")) {
                    System.out.println("登录成功");
                    return "user_index?faces-redirect=true";
		} 
                else if (userGroup.getGroupname().equals("admin")) {
                    return "admin_index?faces-redirect=true";
                }
                else {
			return "login?faces-redirect=true";
		}
	}
        
        
       
        
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			this.user = null;
			request.logout();
			// clear the session
			((HttpSession) context.getExternalContext().getSession(false)).invalidate();
		} catch (ServletException e) {
			log.log(Level.SEVERE, "Failed to logout user!", e);
		}
		return "login?faces-redirect=true";
	}
	public Users getAuthenticatedUser() {
		return user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
        
        public String getMessage() {
		return ERRORMESSAGE;
	}
}