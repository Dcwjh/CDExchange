/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Users;
import interfaces.AdminInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Jianhai Wang
 */

@Named(value = "addUser")
@RequestScoped
public class AddUser implements Serializable {

    @EJB
    private AdminInterfaceRemote adminfaces;

    @EJB
    private UserInterfaceRemote userfaces;

    private Users user;

    private String username;
    private String email;
    private String lastname;
    private String firstname;
    private String gender;
    private String password;
    private String confirmpassword;
    private String phone;
    private String address;
    private String message;

    /**
     *
     */
    public AddUser() {
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        System.out.println("adduser" + user.toString());
    }

    public String createUser() {
        Users user1 = new Users(username, firstname, lastname, email, password, gender, phone, address);

        System.out.println("adduser createrUser " + this.getUsername() + " " + this.getEmail() + " " + this.getGender());
        try {
            adminfaces.createUser(user1);
        } catch (Exception ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin_manage_user?faces-redirect=true";

    }

    public String createAdmin() {
        Users user2 = new Users(username, firstname, lastname, email, password, gender, phone, address);

        try {
            adminfaces.createAdmin(user2);
        } catch (Exception ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "admin_manage_user?faces-redirect=true";
    }

    public void volidation(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        // get confirm password

        UIInput uiInputusername = (UIInput) components.findComponent("username");
        String username = uiInputusername.getLocalValue() == null ? ""
                : uiInputusername.getLocalValue().toString();

//        UIInput uiInputemail = (UIInput) components.findComponent("email");
//        String email = uiInputemail.getLocalValue() == null ? ""
//                : uiInputemail.getLocalValue().toString();

        UIInput uiInputfirstName = (UIInput) components.findComponent("firstname");
        String firstname = uiInputfirstName.getLocalValue() == null ? ""
                : uiInputfirstName.getLocalValue().toString();

        UIInput uiInputlastName = (UIInput) components.findComponent("lastname");
        String lastname = uiInputlastName.getLocalValue() == null ? ""
                : uiInputlastName.getLocalValue().toString();

        UIInput uiInputgender = (UIInput) components.findComponent("gender");
        String gender = uiInputgender.getLocalValue() == null ? ""
                : uiInputgender.getLocalValue().toString();

        UIInput uiInputphone = (UIInput) components.findComponent("phone");
        String phone = uiInputphone.getLocalValue() == null ? ""
                : uiInputphone.getLocalValue().toString();

        UIInput uiInputaddress = (UIInput) components.findComponent("address");
        String address = uiInputaddress.getLocalValue() == null ? ""
                : uiInputaddress.getLocalValue().toString();

        if (username != null && username != "") {
            user.setUsername(username);
        }

        if (lastname != null && lastname != "") {
            user.setLastName(lastname);
        }

        if (firstname != null && firstname != "") {
            user.setFirstName(firstname);
        }

        if (gender != null && gender != "") {
            user.setGender(gender);
        }

        if (phone != null && phone != "") {
            user.setPhone(phone);
        }
        if (address != null && address != "") {
            user.setAddress(address);
        }
        if (password != null && password != "") {
            try {
                user.setPassword(encodeSHA256(password));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String updateAdmin(){
          try {
              
              
            adminfaces.editUser(user);
        } catch (Exception ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin_detail?faces-redirect=true";
    }
    
    

    public static String encodeSHA256(String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return DatatypeConverter.printBase64Binary(digest).toString();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usename) {
        this.username = usename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
