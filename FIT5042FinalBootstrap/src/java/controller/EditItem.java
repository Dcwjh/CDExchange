package controller;

import entities.Genre;
import entities.Item;
import entities.Users;
import interfaces.ItemInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jianhai Wang
 */

@RequestScoped
@Named("editItem")
public class EditItem implements Serializable{
    
     @EJB
    private ItemInterfaceRemote itemfaces;
    
    @EJB
    private UserInterfaceRemote userface;  
    private static int STATICITEMID;
    
    private Users user;  
    private String title;
    private double price;
    private String description;
    private String quality;
    private int stock;
    private String genrename;
    private int itemid;
    
    private int genreid;
    
    public EditItem(){
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        System.out.println("editIitem" + user.toString());
    }
    
    
    public String getPareItemId(int itemid){
        STATICITEMID = itemid;
        System.out.println("edit itemid" + this.getItemid());
        return "/item/edit.xhtml";
    }

    public String updateItem() {
        try {

//            int itemid = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("itemid"));
//            System.out.println("UpdateItem itemid" + itemid);

            System.out.println("updateItem itemid " + STATICITEMID);
            Item item = itemfaces.findItemById(STATICITEMID);
            item.setTitle(title);
            item.setPrice(price);
            item.setDescription(description);
            item.setQuality(quality);
            item.setStock(stock);
            item.setUserid(user);

            Genre genre = null;
            try {

                System.out.println("************************************************");
                genre = itemfaces.findGenreById(genreid);
                System.out.println(genre.toString());

            } catch (Exception ex) {
                Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
            }
            item.setGenreid(genre);

            try {
                itemfaces.editItem(item);
            } catch (Exception ex) {
                Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "/user_sell?faces-redirect=true";

        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/user_sell?faces-redirect=true";

    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }
    
    
    
    
        
    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }
    
}
