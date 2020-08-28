/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Item;
import interfaces.OrderInterfaceRemote;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Order;

/**
 *
 * @author MECHREVO
 */

@Stateless
public class OrderInterfaceImpl implements OrderInterfaceRemote{
    
        @PersistenceContext(unitName="FIT5042Final-ejbPU")
	private EntityManager em;
    

    @Override
    public void createOrder(Order order) throws Exception {
        em.persist(order);
    }

    @Override
    public void removeOrder(int orderid) throws Exception {
        Order order = this.findOrderById(orderid);
        if(order != null)
            em.remove(order);
    }

    @Override
    public List<Order> findOrderByUserId(int userid) throws Exception {
        Query query = em.createQuery("SELECT o FROM order o where o.userid.userid = :userid", Item.class);
        query.setParameter("userid", userid);
        return query.getResultList();
    }

    @Override
    public Order findOrderById(int orderId) throws Exception {
        return em.find(Order.class, orderId);
    }
    
}
