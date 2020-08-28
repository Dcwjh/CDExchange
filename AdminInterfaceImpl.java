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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MECHREVO
 */
@Stateless
public class AdminInterfaceImpl implements AdminInterfaceRemote {

    public static final String USERS_GROUP = "admin";

    @PersistenceContext(unitName = "FIT5042Final-ejbPU")
    private EntityManager em;

    @Override
    public void createAdmin(Users user) throws Exception {
        UserGroups userGroup = new UserGroups();
        userGroup.setEmail(user.getEmail());
        userGroup.setGroupname(USERS_GROUP);
        em.persist(user);
        em.persist(userGroup);
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
        Query query = em.createQuery("SELECT i FROM item i", Item.class);
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
        Query query = em.createQuery("SELECT u FROM users u", Users.class);
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
            em.remove(user);
        }
    }
}
