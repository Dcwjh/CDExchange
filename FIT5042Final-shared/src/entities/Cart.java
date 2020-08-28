/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jianhai Wang
 */
@Entity
@Table(name = "CART")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
    , @NamedQuery(name = "Cart.findByCardid", query = "SELECT c FROM Cart c WHERE c.cardid = :cardid")
    , @NamedQuery(name = "Cart.findByNumber", query = "SELECT c FROM Cart c WHERE c.number = :number")
    , @NamedQuery(name = "Cart.findByTotal", query = "SELECT c FROM Cart c WHERE c.total = :total")})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CARDID")
    private Integer cardid;
    @Column(name = "NUMBER")
    private Integer number;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private Double total;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne
    private Item itemid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private Users userid;

    public Cart() {
    }

    public Cart(Integer cardid) {
        this.cardid = cardid;
    }
    
     public Cart(Integer cardid, Item itemid,  Users userid, Integer number, Double total) {
        this.cardid = cardid;
        this.userid =userid;
        this.itemid = itemid;
        this.number = number;
        this.total = total;
        
    }
     
     public Cart(Item itemid,  Users userid, Integer number, Double total) {
        this.userid =userid;
        this.itemid = itemid;
        this.number = number;
        this.total = total;
        
    }



    public Integer getCardid() {
        return cardid;
    }

    public void setCardid(Integer cardid) {
        this.cardid = cardid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardid != null ? cardid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cardid == null && other.cardid != null) || (this.cardid != null && !this.cardid.equals(other.cardid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cart[ cardid=" + cardid + " ]";
    }
    
}
