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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shicheng Ai
 */
@Entity
@Table(name = "USER_GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findAllUserGroups", query = "SELECT u FROM UserGroups u")
    , @NamedQuery(name = "findUserGroupByUserGroupId", query = "SELECT u FROM UserGroups u WHERE u.usergroupid = :usergroupid")
    , @NamedQuery(name = "findUserGroupByEmail", query = "SELECT u FROM UserGroups u WHERE u.email = :email")
    , @NamedQuery(name = "findUserGroupByGroupName", query = "SELECT u FROM UserGroups u WHERE u.groupname = :groupname")})
public class UserGroups implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String USERS_GROUP = "user";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USERGROUPID")
    private Integer usergroupid;
    @Basic(optional = false)
    @Column(name = "EMAIL", nullable=false, length=255)
    private String email;
    @Basic(optional = false)
    @Column(name = "GROUPNAME", nullable=false, length=32)
    private String groupname;

    public UserGroups() {
    }

    public UserGroups(Integer usergroupid) {
        this.usergroupid = usergroupid;
    }
    
    public UserGroups(String email, String groupname) {
        this.email = email;
        this.groupname = groupname;
    }

    public UserGroups(Integer usergroupid, String email, String groupname) {
        this.usergroupid = usergroupid;
        this.email = email;
        this.groupname = groupname;
    }

    public Integer getUsergroupid() {
        return usergroupid;
    }

    public void setUsergroupid(Integer usergroupid) {
        this.usergroupid = usergroupid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usergroupid != null ? usergroupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroups)) {
            return false;
        }
        UserGroups other = (UserGroups) object;
        if ((this.usergroupid == null && other.usergroupid != null) || (this.usergroupid != null && !this.usergroupid.equals(other.usergroupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserGroups[ usergroupid=" + usergroupid + " ]";
    }
    
}
