package repository;

import entities.Cart;
import entities.Item;
import entities.Orders;
import entities.UserGroups;
import entities.Users;
import interfaces.UserInterfaceRemote;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
 * @author Shicheng Ai
 */


@Stateless
public class UserEJB implements UserInterfaceRemote {

    @PersistenceContext(unitName = "FIT5042Final-ejbPU")
    private EntityManager em;

    @Override
    public Users createUser(Users user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGroupname(UserGroups.USERS_GROUP);
        em.persist(user);
        em.persist(group);
        return user;
    }

    @Override
    public Users findUserByEmail(String email) {
        TypedQuery<Users> query = em.createNamedQuery("findUserByEmail", Users.class);
        query.setParameter("email", email);
        Users user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return user;
    }

    @Override
    public UserGroups findUserGroupByEmail(String email) {
        TypedQuery<UserGroups> query = em.createNamedQuery("findUserGroupByEmail", UserGroups.class);
        query.setParameter("email", email);
        UserGroups userGroups = null;
        try {
            userGroups = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return userGroups;
    }

    @Override
    public void editUser(Users user) {
        em.merge(user);
    }

    @Override
    public Users findUserByID(int userid) {
        return em.find(Users.class, userid); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addToCart(Cart cart) throws Exception {
//        Cart findcart = findCartItem(cart.getUserid().getUserid(), cart.getItemid().getItemid());
//        if(findcart!=null){
//            cart.setNumber(cart.getNumber()  + 1);
//            Item item = findItemById(cart.getItemid().getItemid());
//            cart.setTotal(cart.getTotal() + item.getPrice());
//            em.persist(cart);
//        } else {
//            em.persist(cart);
//        }
        em.merge(cart);
    }

    @Override
    public Cart findCartItem(int userid, int itemid) {
        TypedQuery<Cart> query = em.createNamedQuery("Cart.findAll", Cart.class);

        Cart cart = null;
        try {
            List<Cart> listCart = query.getResultList();

            for (Cart c : listCart) {
                if (c.getItemid().getItemid() == itemid && c.getUserid().getUserid() == userid) {
                    cart = c;
                    System.out.println("zhaodao cart");
                }
            }
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return cart;

    }

    @Override
    public Item findItemById(int itemid) throws Exception {
        return em.find(Item.class, itemid);
    }

    @Override
    public List<Cart> findAllCart(int userid) throws Exception {
        TypedQuery<Cart> query = em.createNamedQuery("Cart.findAll", Cart.class);
        List<Cart> cart = new ArrayList<>();
        try {
            List<Cart> listCart = query.getResultList();

            for (Cart c : listCart) {
                if (c.getUserid().getUserid() == userid) {
                    cart.add(c);
                }
            }
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return cart;
    }

    @Override
    public List<Orders> findAllOrders(int userid) throws Exception {
        TypedQuery<Orders> query = em.createNamedQuery("Orders.findAll", Orders.class);

        List<Orders> orders = new ArrayList<>();
        try {
            List<Orders> listOrders = query.getResultList();

            for (Orders o : listOrders) {
                if (o.getBuyerid().getUserid() == userid) {
                    orders.add(o);
                }
            }
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return orders;

    }

    @Override
    public void removeCart(int cartid) throws Exception {
        Cart cart = em.find(Cart.class, cartid);
        em.remove(cart);
    }

    @Override
    public void removeOrders(int ordersid) throws Exception {
        Orders orders = em.find(Orders.class, ordersid);
        em.remove(orders);
    }

    @Override
    public void createOrders(Orders order) throws Exception {
        em.persist(order);
    }

    @Override
    public List<Item> searchItem(String title) throws Exception {
        TypedQuery<Item> query = em.createNamedQuery("Item.findByTitle", Item.class);
        title = "《" + title + "》";
        query.setParameter("title", title);
        List<Item> result = new ArrayList<>();
        try {
            List<Item> list = query.getResultList();
            for (Item i : list) {
                if(i.isStatus()){
                    result.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
