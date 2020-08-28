/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Genre;
import entities.Item;
import entities.Status;
import entities.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Jianhai Wang
 */
@Remote
public interface ItemInterfaceRemote {
    
    public void addItem(Item item) throws Exception;
    
    public void removeItem(int itemId) throws Exception;
    
    public Genre findGenreByName(String name) throws Exception;
    
    public List<Genre> findAllGenre() throws Exception;
    
    
    public void editItem(Item itemId) throws Exception;
    
    public Item findItemById(int item) throws Exception;
    
    public List<Item> findItemByUserId(int userId) throws Exception;  //who publish
    
    public List<Item> findAllItem() throws Exception;
    
    public Genre findGenreById(int genreid) throws Exception;
    
    
           
}
