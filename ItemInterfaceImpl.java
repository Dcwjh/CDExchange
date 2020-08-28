/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Item;
import interfaces.ItemInterfaceRemote;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author MECHREVO
 */

@Stateless
public class ItemInterfaceImpl implements ItemInterfaceRemote{
    
    @PersistenceContext(unitName="FIT5042Final-ejbPU")
    private EntityManager em;

    @Override
    public void addItem(Item item) throws Exception {
         em.persist(item);
    }

    @Override
    public void removeItem(int itemId) throws Exception {
        Item item = this.findItemById(itemId);
        if(item != null)
            em.remove(item);
    }

    @Override
    public void editItem(Item item) throws Exception {
        em.merge(item);
    }

    @Override
    public Item findItemById(int itemId) throws Exception {
        return em.find(Item.class, itemId);
    }

    @Override
    public List<Item> findItemByUserId(int userId) throws Exception {     //
        Query query = em.createQuery("SELECT i FROM item i WHERE i.userid.userid = :userId", Item.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Item> findAllItem() throws Exception {
        TypedQuery<Item> query = em.createNamedQuery("findAll", Item.class);
        List<Item> items = new ArrayList<>();
        try{
            items = query.getResultList();
        } catch(Exception e){
            
        }
        return items;
    }
    
}
