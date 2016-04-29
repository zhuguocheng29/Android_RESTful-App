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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Think
 */
@Entity
@Table(name = "food")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findByFid", query = "SELECT f FROM Food f WHERE f.fid = :fid"),
    @NamedQuery(name = "Food.findByName", query = "SELECT f FROM Food f WHERE f.name = :name"),
    @NamedQuery(name = "Food.findByCalorie", query = "SELECT f FROM Food f WHERE f.calorie = :calorie"),
    @NamedQuery(name = "Food.findByFat", query = "SELECT f FROM Food f WHERE f.fat = :fat"),
    @NamedQuery(name = "Food.findByServing", query = "SELECT f FROM Food f WHERE f.serving = :serving"),
    @NamedQuery(name = "Food.findByCategory", query = "SELECT f FROM Food f WHERE f.category = :category")
})
public class Food implements Serializable {

    @Size(max = 64)
    @Column(name = "category")
    private String category;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fid")
    private Integer fid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calorie")
    private int calorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fat")
    private int fat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "serving")
    private float serving;
    @OneToMany(mappedBy = "fid")
    private Collection<Quantity> quantityCollection;

    public Food() {
    }

    public Food(Integer fid) {
        this.fid = fid;
    }

    public Food(Integer fid, String name, int calorie, int fat, float serving) {
        this.fid = fid;
        this.name = name;
        this.calorie = calorie;
        this.fat = fat;
        this.serving = serving;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public float getServing() {
        return serving;
    }

    public void setServing(float serving) {
        this.serving = serving;
    }

    @XmlTransient
    public Collection<Quantity> getQuantityCollection() {
        return quantityCollection;
    }

    public void setQuantityCollection(Collection<Quantity> quantityCollection) {
        this.quantityCollection = quantityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fid != null ? fid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.fid == null && other.fid != null) || (this.fid != null && !this.fid.equals(other.fid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Food[ fid=" + fid + " ]";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
