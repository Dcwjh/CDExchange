/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Item;
import entities.UserGroups;
import entities.Users;
import interfaces.UserInterfaceRemote;
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
public class UserInterfaceImpl implements UserInterfaceRemote{
   

    @PersistenceContext(unitName="FIT5042Final-ejbPU")
    private EntityManager em;
    
    @Override
    public void editUser(Users user) {
        em.merge(user);
    }

    @Override
    public Users findUserByID(int userid) {
        return em.find(Users.class, userid);
    }

    @Override
    public Users findUserByEmail(String email) {
         TypedQuery<Users> query = em.createNamedQuery("findUserByEmail", Users.class);
		query.setParameter("email", email);
		Users users = null;
		try {
			users = query.getSingleResult();
		} catch (Exception e) {
			// getSingleResult throws NoResultException in case there is no user in DB
			// ignore exception and return NULL for user instead
		}
		return users;
    }
    


}

 
