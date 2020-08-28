package repository;

import entities.Cart;
import entities.Item;
import interfaces.CartInterfaceRemote;
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
public class CartInterfaceImpl implements CartInterfaceRemote {
    
    @PersistenceContext(unitName="FIT5042Final-ejbPU")
    private EntityManager em;

    @Override
    public void addCart(Cart cart) throws Exception {
        em.persist(cart);
    }

    @Override
    public void removeCart(int cartId) throws Exception {
        Cart cart = this.findCartById(cartId);
        if(cart != null)
            em.remove(cart);
    }


    @Override
    public Cart findCartById(int cartId) throws Exception {
        return em.find(Cart.class, cartId);
    }

    @Override
    public List<Item> findAllCartItem() throws Exception {
        Query query = em.createQuery("SELECT c FROM cart c", Cart.class);
        return query.getResultList();
    }
    
}
