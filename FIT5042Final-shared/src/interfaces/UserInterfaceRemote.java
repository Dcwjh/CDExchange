/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Cart;
import entities.Item;
import entities.Orders;
import entities.UserGroups;
import entities.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Shicheng Ai
 */


@Remote
public interface UserInterfaceRemote {
    
    public Users createUser(Users user)throws Exception;;
    
    public void editUser(Users user) throws Exception;;
    
    public Users findUserByID(int userid) throws Exception;;
    
    public Users findUserByEmail(String email) throws Exception;;
    
    public UserGroups findUserGroupByEmail(String email) throws Exception; 
    
    public void addToCart(Cart cart) throws Exception;
    
    public Cart findCartItem(int userid, int itemid)throws Exception; 
    
    public Item findItemById(int itemid)  throws Exception; 
    
    public List<Cart> findAllCart(int userid) throws Exception; 
    
    public List<Orders> findAllOrders(int userid) throws Exception; 
    
    public void removeCart(int cartid) throws Exception; 
    
    public void removeOrders(int orders) throws Exception; 
    
    public void createOrders(Orders order)  throws Exception; 
    
    public List<Item> searchItem(String title) throws Exception;
    
    
}
