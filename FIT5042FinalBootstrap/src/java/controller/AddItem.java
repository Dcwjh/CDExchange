/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Genre;
import entities.Item;
import entities.Status;
import entities.Users;
import interfaces.ItemInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jianhai Wang
 */
@RequestScoped
@Named("addItem")
public class AddItem implements Serializable {

    @EJB
    private ItemInterfaceRemote itemfaces;

    @EJB
    private UserInterfaceRemote userface;

    private Users user;
    private String title;
    private double price;
    private String description;
    private String quality;
    private int stock;
    private String genrename;

    private int genreid;

    public AddItem() {
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        System.out.println("additem" + user.toString());
    }

    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
    }

    public String createItem() {
        Item item = new Item();
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
            itemfaces.addItem(item);
        } catch (Exception ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/user_sell?faces-redirect=true";
    }

//    public String editItem(int itemId) throws Exception{
//        Item item = itemfaces.findItemById(itemId);
//        item.setTitle(title);
//        item.setPrice(price);
//        item.setDescription(description);
//        item.setQuality(quality);
//        item.setStock(stock);
//        item.setUserid(user);
//  
//         Genre genre = null;
//         try {      
//             System.out.println("-------------------------------------------------");
//                genre = itemfaces.findGenreById(genreid);
//                System.out.println(genre.toString());
//             
//         } catch (Exception ex) {
//             Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
//         }
//         item.setGenreid(genre);
//        
//         try {
//             itemfaces.editItem(item);
//         } catch (Exception ex) {
//             Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
//         }
//       ;  
//        return  "/sell?faces-redirect=true";   
//    }
    public Item findItem() {

        int itemid = Integer.valueOf(FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("editItemId"));
        System.out.println(" Add item -> findItem itemid  " + itemid);

        try {
            Item item = itemfaces.findItemById(itemid);
            return item;
        } catch (Exception ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

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
