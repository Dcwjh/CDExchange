/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Item;
import entities.Users;
import interfaces.AdminInterfaceRemote;
import interfaces.ItemInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jianhai Wang
 */
@Named(value = "adminController")
@RequestScoped
public class AdminController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AdminInterfaceRemote adminfaces;

    @EJB
    private UserInterfaceRemote userfaces;

    private Users user;

    private List<Users> userList;
    private List<Item> itemList;
    private String message;

    public AdminController() {
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        System.out.println(user.toString());
    }

    @PostConstruct
    public void getInformation() {
        try {
            userList = adminfaces.findAllUsers();
            itemList = adminfaces.findAllItem();
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRole(String email) {
        try {
            return adminfaces.findByEmail(email).getGroupname();
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user";
    }

    public Users detailUser() {
        try {
            int userid = Integer.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap()
                    .get("userid"));
            return adminfaces.findUserById(userid);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String removeUser(int userid) {
        try {
            if (userid != user.getUserid()) {
                adminfaces.removeUser(userid);
            } else {
                this.setMessage("Can't delete yourself!!!");
                System.out.println(this.getMessage());
                return "admin_manage_user";
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin_manage_user?faces-redirect=true";
    }

    public void removeItem(int itemId) {
        try {
            adminfaces.removeItem(itemId);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Item detailItem() {
        try {
            int itemid = Integer.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap()
                    .get("itemid"));
            return adminfaces.findItemById(itemid);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void changeStatus(int itemid) {
        try {
            Item item = adminfaces.findItemById(itemid);
            item.setStatus(item.isStatus() == false ? true : false);
            adminfaces.editItem(item);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void save() {
        try {
            itemList = adminfaces.findAllItem();
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
