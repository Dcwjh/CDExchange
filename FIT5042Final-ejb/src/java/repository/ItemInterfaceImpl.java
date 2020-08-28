/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Genre;
import entities.Item;
import entities.Status;
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
 * @author Jianhai Wang
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
    public List<Item> findItemByUserId(int userid) throws Exception {     //获取该用户发布的item
        Query query = em.createQuery("SELECT i FROM Item i WHERE i.userid.userid = :userid", Item.class);
        query.setParameter("userid", userid);
        return query.getResultList();
    }

    @Override
    public List<Item> findAllItem() throws Exception {   //获取全部的item
        TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
        return query.getResultList();      
    }

    @Override
    public Genre findGenreByName(String genrename) throws Exception {
       TypedQuery<Genre> query = em.createNamedQuery("Genre.findByGenrename", Genre.class);
       query.setParameter("genrename", genrename);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> findAllGenre() throws Exception {
        TypedQuery<Genre> query = em.createNamedQuery("Genre.findAll", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre findGenreById(int genreid) throws Exception {
        return em.find(Genre.class,genreid );
    }
    
    

}
