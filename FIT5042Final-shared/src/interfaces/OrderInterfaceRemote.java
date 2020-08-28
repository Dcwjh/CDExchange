/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Users;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.criteria.Order;

/**
 *
 * @author Jianhai Wang
 */
@Remote
public interface OrderInterfaceRemote {
    
    public void createOrder(Order order) throws Exception;
    
    public void removeOrder(int orderid) throws Exception;
    
    public List<Order> findOrderByUserId(int userid) throws Exception;
    
    public Order findOrderById(int orderId) throws Exception;
    

}
