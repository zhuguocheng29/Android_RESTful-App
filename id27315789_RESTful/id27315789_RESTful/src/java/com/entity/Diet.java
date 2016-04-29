/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Think
 */
@Entity
@Table(name = "diet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diet.findAll", query = "SELECT d FROM Diet d"),
    @NamedQuery(name = "Diet.findByDid", query = "SELECT d FROM Diet d WHERE d.did = :did"),
    @NamedQuery(name = "Diet.findByDayTime", query = "SELECT d FROM Diet d WHERE d.dayTime = :dayTime")})
public class Diet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "did")
    private Integer did;
    @Column(name = "day_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayTime;
    @JoinColumn(name = "qid", referencedColumnName = "qid")
    @ManyToOne
    private Quantity qid;
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

    public Diet() {
    }

    public Diet(Integer did) {
        this.did = did;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

    public Quantity getQid() {
        return qid;
    }

    public void setQid(Quantity qid) {
        this.qid = qid;
    }

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (did != null ? did.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diet)) {
            return false;
        }
        Diet other = (Diet) object;
        if ((this.did == null && other.did != null) || (this.did != null && !this.did.equals(other.did))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Diet[ did=" + did + " ]";
    }
    
}
