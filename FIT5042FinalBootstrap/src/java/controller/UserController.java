/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.AddUser.encodeSHA256;
import entities.Cart;
import entities.Item;
import entities.Orders;
import entities.Users;
import interfaces.AdminInterfaceRemote;
import interfaces.ItemInterfaceRemote;
import interfaces.UserInterfaceRemote;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

/**
 *
 * @author Shicheng Ai
 */
@Named(value = "userController")
@RequestScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ItemInterfaceRemote itemfaces;

    @EJB
    private UserInterfaceRemote userfaces;

    @EJB
    private AdminInterfaceRemote adminfaces;

    private Users user;

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String phone;
    private String address;
    private String password;

    private static String message;

    private String key;
    private List<Item> buyList = new ArrayList<>();
    private List<Cart> cartList = new ArrayList<>();

    private List<Orders> ordersList = new ArrayList<>();
    
    private static List<Item> result;

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        System.out.println(user.toString());
    }

    @PostConstruct
    public void getInformation() {
        try {
            List<Item> list = adminfaces.findAllItem();
            for (Item item : list) {
                if (item.isStatus()) {
                    buyList.add(item);
                }
            }

            cartList = userfaces.findAllCart(user.getUserid());
            ordersList = userfaces.findAllOrders(user.getUserid());

        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void volidation(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        // get confirm password

        UIInput uiInputusername = (UIInput) components.findComponent("username");
        String username = uiInputusername.getLocalValue() == null ? ""
                : uiInputusername.getLocalValue().toString();

//        UIInput uiInputemail = (UIInput) components.findComponent("email");
//        String email = uiInputemail.getLocalValue() == null ? ""
//                : uiInputemail.getLocalValue().toString();
        UIInput uiInputfirstName = (UIInput) components.findComponent("firstname");
        String firstname = uiInputfirstName.getLocalValue() == null ? ""
                : uiInputfirstName.getLocalValue().toString();

        UIInput uiInputlastName = (UIInput) components.findComponent("lastname");
        String lastname = uiInputlastName.getLocalValue() == null ? ""
                : uiInputlastName.getLocalValue().toString();

        UIInput uiInputgender = (UIInput) components.findComponent("gender");
        String gender = uiInputgender.getLocalValue() == null ? ""
                : uiInputgender.getLocalValue().toString();

        UIInput uiInputphone = (UIInput) components.findComponent("phone");
        String phone = uiInputphone.getLocalValue() == null ? ""
                : uiInputphone.getLocalValue().toString();

        UIInput uiInputaddress = (UIInput) components.findComponent("address");
        String address = uiInputaddress.getLocalValue() == null ? ""
                : uiInputaddress.getLocalValue().toString();

        if (username != null && username != "") {
            user.setUsername(username);
        }

        if (lastname != null && lastname != "") {
            user.setLastName(lastname);
        }

        if (firstname != null && firstname != "") {
            user.setFirstName(firstname);
        }

        if (gender != null && gender != "") {
            user.setGender(gender);
        }

        if (phone != null && phone != "") {
            user.setPhone(phone);
        }
        if (address != null && address != "") {
            user.setAddress(address);
        }
        if (password != null && password != "") {
            try {
                user.setPassword(encodeSHA256(password));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String updateUser() {
        try {
            userfaces.editUser(user);
            System.out.println("User infor id: " + user.getUserid()
            + " username :" + user.getUsername()
            +"  firstname :" + user.getFirstName()
                      +"  Lasttname :" + user.getLastName()
                      +"  Gender :" + user.getGender()
                      +"  Phone :" + user.getPhone()
                      +"  Address :" + user.getAddress()
                     
            );
            System.out.println("update user bug");
        } catch (Exception ex) {
            
            System.out.println("update user bug");
            
             System.out.println("User infor id: " + user.getUserid()
            + " username :" + user.getUsername()
            +"  firstname :" + user.getFirstName()
                      +"  Lasttname :" + user.getLastName()
                      +"  Gender :" + user.getGender()
                      +"  Phone :" + user.getPhone()
                      +"  Address :" + user.getAddress()
                     
            );
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user_detail?faces-redirect=true";
    }

    public Double total() {
        try {
            double pay = 0.0;
            cartList = userfaces.findAllCart(user.getUserid());
            for (Cart c : cartList) {
                pay += c.getTotal();
            }
            return pay + 12.00;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String deleteCart(int cartid) {
        try {
            userfaces.removeCart(cartid);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user_cart?faces-redirect=true";
    }

    public String save() {
        try {
            List<Cart> orderCart = userfaces.findAllCart(user.getUserid());
            int size = orderCart.size();

            for (Cart c : orderCart) {
                Orders orders = new Orders(user, c.getItemid(), c.getNumber(), 12.00 / size, c.getTotal());
                Item mergerItem = c.getItemid();
                mergerItem.setStock(mergerItem.getStock() - c.getNumber());
                userfaces.createOrders(orders);
                userfaces.removeCart(c.getCardid());
                itemfaces.editItem(mergerItem);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user_order?faces-redirect=true";
    }

    public int category() {
        return ordersList.size();
    }

    public int numbers() {
        int n = 0;
        for (Orders order : ordersList) {
            n += order.getNumber();
        }
        return n;
    }

    public double freight() {
        double p = 0;
        for (Orders order : ordersList) {
            p += order.getFreight();
        }
        return p;
    }

    public double totalPay() {
        double p = 0;
        for (Orders order : ordersList) {
            p += order.getTotal();
            p += order.getFreight();
        }
        return p;
    }

    public String deleteOrders(int ordersid) {
        try {
            userfaces.removeOrders(ordersid);
            total();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user_order?faces-redirect=true";

    }

    public String search() {
        result = null;
        message = "";
        try {
            System.out.println("key " + key);
            if (userfaces.searchItem(key) != null) {
                result = userfaces.searchItem(key); 
                for(Item i : result){
                    System.out.println("Item " + i.getTitle() + " " + i.getItemid());
                }
            } else {
                message = "Cannot find 《" + key + "》，maybe no one released the item.";
                System.out.println("message " + message);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user_search?faces-redirect=true";
    }

    public void addToCart(int itemid) {
        try {
            Cart cart = userfaces.findCartItem(user.getUserid(), itemid);
            Item item = userfaces.findItemById(itemid);
            if (cart == null) {
                cart = new Cart(item, user, 1, item.getPrice());
                System.out.println("userController addToCart bucunzai");
            } else {
                cart.setNumber(cart.getNumber() + 1);
                cart.setTotal(cart.getTotal() + item.getPrice());
                System.out.println("userController addToCart zhaodaole");
            }
            userfaces.addToCart(cart);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void changeByPrice(){
        
       if(buyList.size() >= 2){
            Item i1 = buyList.get(0);
            Item i2 = buyList.get(1);
            if(i1.getStock() < i2.getStock()){
             Collections.sort(buyList,new Comparator<Item>() {
                 @Override
                 public int compare(Item o1, Item o2) {
                     return (int) (o2.getPrice() - o1.getPrice());
                 }
             });
                System.out.println("price降序");
            } else {
               Collections.sort(buyList,new Comparator<Item>() {
                 @Override
                 public int compare(Item o1, Item o2) {
                     return (int) (o1.getPrice() - o2.getPrice());
                 }
             });
                System.out.println("prices升序");
            }
        }
//       return "user_buy?faces-redirect=true";
    }
    
    public void changeByStock(){
       if(buyList.size() >= 2){
            Item i1 = buyList.get(0);
            Item i2 = buyList.get(1);
            if(i1.getStock() < i2.getStock()){
             Collections.sort(buyList,new Comparator<Item>() {
                 @Override
                 public int compare(Item o1, Item o2) {
                     return o2.getStock() - o1.getStock();
                 }
             });
             System.out.println("stock降序");
            } else {
               Collections.sort(buyList,new Comparator<Item>() {
                 @Override
                 public int compare(Item o1, Item o2) {
                     return  o1.getStock()- o2.getStock();
                 }
             });
                System.out.println("stock升序");
            }
        }
//       return "user_buy?faces-redirect=true";
    }

    public  List<Item> getResult() {
        return result;
    }

    
    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        UserController.message = message;
    }

    public List<Item> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<Item> buyList) {
        this.buyList = buyList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ItemInterfaceRemote getItemfaces() {
        return itemfaces;
    }

    public void setItemfaces(ItemInterfaceRemote itemfaces) {
        this.itemfaces = itemfaces;
    }

    public UserInterfaceRemote getUserfaces() {
        return userfaces;
    }

    public void setUserfaces(UserInterfaceRemote userfaces) {
        this.userfaces = userfaces;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
