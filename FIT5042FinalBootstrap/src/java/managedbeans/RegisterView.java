/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Users;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

/**
 *
 * @author Shicheng Ai
 */

@ManagedBean
@SessionScoped
public class RegisterView implements Serializable {

    private static final long serialVersionUID = 1685823449195612778L;
    private static Logger log = Logger.getLogger(RegisterView.class.getName());
    @EJB
    private UserInterfaceRemote userfaces;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String MESSAGE1;
    private String MESSAGE2;
    private String MESSAGE3;
    private String MESSAGE4;
    private String MESSAGE5;
    private String MESSAGE6;
    private String MESSAGE7;
    private String MESSAGE8;

    public void validatePassword(ComponentSystemEvent event) {
        MESSAGE1 = "";
        MESSAGE2 = "";
        MESSAGE3 = "";
        MESSAGE4 = "";
        MESSAGE5 = "";
        MESSAGE6 = "";
        MESSAGE7 = "";
        MESSAGE8 = "";

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        UIInput uiInputusername = (UIInput) components.findComponent("username");
        String username = uiInputusername.getLocalValue() == null ? ""
                : uiInputusername.getLocalValue().toString();

        UIInput uiInputemail = (UIInput) components.findComponent("email");
        String email = uiInputemail.getLocalValue() == null ? ""
                : uiInputemail.getLocalValue().toString();

        UIInput uiInputfirstName = (UIInput) components.findComponent("firstName");
        String firstName = uiInputfirstName.getLocalValue() == null ? ""
                : uiInputfirstName.getLocalValue().toString();

        UIInput uiInputlastName = (UIInput) components.findComponent("lastName");
        String lastName = uiInputlastName.getLocalValue() == null ? ""
                : uiInputlastName.getLocalValue().toString();

        // Let required="true" do its job.
        if (username == null || username.isEmpty()) {
            MESSAGE1 = "Please enter your usename";
        } else if (email == null || email.isEmpty()) {
            MESSAGE2 = "Please enter your email";

        } else if (firstName == null || firstName.isEmpty()) {
            MESSAGE3 = "Please enter your firstName";

        } else if (lastName == null || lastName.isEmpty()) {
            MESSAGE4 = "Please enter your lasttName";

        } else if (password == null || password.isEmpty()) {
            MESSAGE5 = "Please enter your password";

        } else if (confirmPassword == null || confirmPassword.isEmpty()) {
            MESSAGE6 = "Please enter your password";

        }

        if (!password.equals(confirmPassword)) {
//            FacesMessage msg = new FacesMessage("Confirm password does not match password");

//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            facesContext.addMessage(passwordId, msg);
//            facesContext.renderResponse();
            MESSAGE7 = "Confirm password does not match password";

        }
        try {
            if (userfaces.findUserByEmail(email) != null) {
//            FacesMessage msg = new FacesMessage("User with this e-mail already exists");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            facesContext.addMessage(passwordId, msg);
//            facesContext.renderResponse();
                MESSAGE8 = "User with this e-mail already exists";

            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String register() {
        Users user = new Users(username, firstName, lastName, email, password);
        try {
            userfaces.createUser(user);
            return "login?faces-redirect=true";
        } catch (Exception ex) {
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.log(Level.INFO, "New user created with e-mail: {0} and username: {1}", new Object[]{email, username});
        //log.info("New user created with e-mail: " + email + " and username: " + username);
        return "register?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String LastName) {
        this.lastName = LastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMessage1() {
        return MESSAGE1;
    }

    public String getMessage2() {
        return MESSAGE2;
    }

    public String getMessage3() {
        return MESSAGE3;
    }

    public String getMessage4() {
        return MESSAGE4;
    }

    public String getMessage5() {
        return MESSAGE5;
    }

    public String getMessage6() {
        return MESSAGE6;
    }

    public String getMessage7() {
        return MESSAGE7;
    }

    public String getMessage8() {
        return MESSAGE8;
    }
}
