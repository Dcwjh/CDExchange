/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Item;
import entities.UserGroups;
import entities.Users;
import interfaces.AdminInterfaceRemote;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import util.AuthenticationUtils;

/**
 *
 * @author Jianhai Wang
 */
@Stateless
public class AdminInterfaceImpl implements AdminInterfaceRemote {

    

    @PersistenceContext(unitName = "FIT5042Final-ejbPU")
    private EntityManager em;

    @Override
    public Users createAdmin(Users user) throws Exception {
        
        try {
			user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		UserGroups group = new UserGroups();
		group.setEmail(user.getEmail());
		group.setGroupname("admin");
		em.persist(user);
		em.persist(group);
		return user;
    }

     @Override
    public Users createUser(Users user)  {
        try {
			user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		UserGroups group = new UserGroups();
		group.setEmail(user.getEmail());
		group.setGroupname("user");
		em.persist(user);
		em.persist(group);
                return user;
		
    }
    
    @Override
    public void editItem(Item item) throws Exception {
        em.merge(item);
    }

    @Override
    public void editUser(Users user) throws Exception {
        em.merge(user);
    }

    @Override
    public List<Item> findAllItem() throws Exception {
        TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
        return query.getResultList();
    }

    @Override
    public Item findItemById(int itemId) throws Exception {
        return em.find(Item.class, itemId);
    }

    @Override
    public Users findUserById(int userId) throws Exception {
        return em.find(Users.class, userId);
    }

    @Override
    public List<Users> findAllUsers() throws Exception {
        TypedQuery<Users> query = em.createNamedQuery("findAllUsers", Users.class);
        return query.getResultList();   
    }

    @Override
    public void removeItem(int itemId) throws Exception {
        Item item = this.findItemById(itemId);
        if (item != null) {
            em.remove(item);
        }
    }

    @Override
    public void removeUser(int userid) throws Exception {
        Users user = this.findUserById(userid);
        if (user != null) {
            UserGroups group = findByEmail(user.getEmail());
            em.remove(user);
            em.remove(group);
        }
    }

    @Override
    public UserGroups findByEmail(String email) throws Exception {
        TypedQuery<UserGroups> query = em.createNamedQuery("findUserGroupByEmail", UserGroups.class);
       query.setParameter("email", email);
        return query.getSingleResult();
    }
    
    
    

   
}
