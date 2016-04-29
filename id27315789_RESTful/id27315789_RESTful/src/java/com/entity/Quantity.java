/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Think
 */
@Entity
@Table(name = "quantity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quantity.findAll", query = "SELECT q FROM Quantity q"),
    @NamedQuery(name = "Quantity.findByQid", query = "SELECT q FROM Quantity q WHERE q.qid = :qid"),
    @NamedQuery(name = "Quantity.findByQuantity", query = "SELECT q FROM Quantity q WHERE q.quantity = :quantity"),
    @NamedQuery(name = "Quantity.findByCal", query = "select q from Quantity q inner join q.fid f where q.fid.calorie = :calorie")})
public class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "qid")
    private Integer qid;
    @Column(name = "quantity")
    private Integer quantity;
    @OneToMany(mappedBy = "qid")
    private Collection<Diet> dietCollection;
    @JoinColumn(name = "fid", referencedColumnName = "fid")
    @ManyToOne
    private Food fid;

    public Quantity() {
    }

    public Quantity(Integer qid) {
        this.qid = qid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public Collection<Diet> getDietCollection() {
        return dietCollection;
    }

    public void setDietCollection(Collection<Diet> dietCollection) {
        this.dietCollection = dietCollection;
    }

    public Food getFid() {
        return fid;
    }

    public void setFid(Food fid) {
        this.fid = fid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qid != null ? qid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quantity)) {
            return false;
        }
        Quantity other = (Quantity) object;
        if ((this.qid == null && other.qid != null) || (this.qid != null && !this.qid.equals(other.qid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Quantity[ qid=" + qid + " ]";
    }
    
}
