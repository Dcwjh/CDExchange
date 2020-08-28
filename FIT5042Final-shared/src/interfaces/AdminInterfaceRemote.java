/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Item;
import entities.UserGroups;
import entities.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Shicheng Ai
 */

@Remote
public interface AdminInterfaceRemote {
    
    public Users createAdmin(Users user) throws Exception;
    
    public Users createUser(Users user) throws Exception;
    
    public void editItem(Item item) throws Exception;
    
    public void editUser(Users user) throws Exception;
    
    public void removeItem(int itemId) throws Exception;
    
    public void removeUser(int userId) throws Exception;
    
    public UserGroups findByEmail(String eamil) throws Exception;
    
    
    public Item findItemById(int itemId) throws Exception;
    
    public Users findUserById(int userId) throws Exception;
    
    
    public List<Item> findAllItem() throws Exception;

    public List<Users> findAllUsers() throws Exception; 
}
