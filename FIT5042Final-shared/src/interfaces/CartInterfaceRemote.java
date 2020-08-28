/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Cart;
import entities.Item;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Jianhai Wang
 */

@Remote
public interface CartInterfaceRemote {
    
    public void addCart(Cart cart) throws Exception;
    
    public void removeCart(int cartId) throws Exception;
    
    public Cart findCartById(int cartId) throws Exception;
    
    public List<Item> findAllCartItem() throws Exception;
    
    
}
