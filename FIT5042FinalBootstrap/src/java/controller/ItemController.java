/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Genre;
import entities.Item;
import entities.Users;
import interfaces.ItemInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jianhai Wang
 */

@Named(value = "itemController")
@RequestScoped
public class ItemController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @EJB
    private ItemInterfaceRemote itemfaces;   
    
    @EJB
    private UserInterfaceRemote userfaces;
    
    private Users user;
    
    private int genreid;
    
    private  List<Item> userOwnItem;
    
    private List<Item>  allItems;

    private List<Genre> genreList;
    
    private Item item;
    
    
    public ItemController(){
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
         System.out.println(user.toString());
    }


   @PostConstruct
    public void init() {
         try {
             this.setGenreList(itemfaces.findAllGenre());
             
         } catch (Exception ex) {
             Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
   
    public List<Item> getUserOweItems(){
        try {
            this.setUserOwnItem(itemfaces.findItemByUserId(user.getUserid()));
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("==============================================================");
        for(Item item :userOwnItem )
            System.out.println(item.getTitle() + " " + item.getPrice());
       return userOwnItem; 
    }
    
    public List<Item> getAllItems(){
        try {
            allItems = itemfaces.findAllItem();
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    public Item detailItem(){
        try {
            int itemid = Integer.valueOf(FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("itemid"));
            return itemfaces.findItemById(itemid);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void removeItem(int  itemId){
        try {
            itemfaces.removeItem(itemId);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Item> getUserOwnItem() {
        return userOwnItem;
    }

    public void setUserOwnItem(List<Item> userOwnItem) {
        this.userOwnItem = userOwnItem;
    }
    
    
    

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

 

    
    
    
    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
    }
       
}
