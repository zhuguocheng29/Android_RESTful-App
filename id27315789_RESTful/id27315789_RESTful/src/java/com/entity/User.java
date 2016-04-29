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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUid", query = "SELECT u FROM User u WHERE u.uid = :uid"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByAge", query = "SELECT u FROM User u WHERE u.age = :age"),
    @NamedQuery(name = "User.findByHeight", query = "SELECT u FROM User u WHERE u.height = :height"),
    @NamedQuery(name = "User.findByWeight", query = "SELECT u FROM User u WHERE u.weight = :weight"),
    @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
    @NamedQuery(name = "User.findByLevelOfActivity", query = "SELECT u FROM User u WHERE u.levelOfActivity = :levelOfActivity"),
    @NamedQuery(name = "User.findByStepPerMile", query = "SELECT u FROM User u WHERE u.stepPerMile = :stepPerMile"),
    @NamedQuery(name = "User.findByCalorieSetGoal", query = "SELECT u FROM User u WHERE u.calorieSetGoal = :calorieSetGoal")})

public class User implements Serializable {

    @Column(name = "calorie_set_goal")
    private Integer calorieSetGoal;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uid")
    private Integer uid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "age")
    private int age;
    @Basic(optional = false)
    @NotNull
    @Column(name = "height")
    private float height;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight")
    private float weight;
    @Size(max = 2)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "level_of_activity")
    private int levelOfActivity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "step_per_mile")
    private int stepPerMile;
    @OneToMany(mappedBy = "uid")
    private Collection<Diet> dietCollection;
    @OneToMany(mappedBy = "uid")
    private Collection<Report> reportCollection;

    public User() {
    }

    public User(Integer uid) {
        this.uid = uid;
    }

    public User(Integer uid, String name, String password, int age, float height, float weight, int levelOfActivity, int stepPerMile) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.levelOfActivity = levelOfActivity;
        this.stepPerMile = stepPerMile;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLevelOfActivity() {
        return levelOfActivity;
    }

    public void setLevelOfActivity(int levelOfActivity) {
        this.levelOfActivity = levelOfActivity;
    }

    public int getStepPerMile() {
        return stepPerMile;
    }

    public void setStepPerMile(int stepPerMile) {
        this.stepPerMile = stepPerMile;
    }

    @XmlTransient
    public Collection<Diet> getDietCollection() {
        return dietCollection;
    }

    public void setDietCollection(Collection<Diet> dietCollection) {
        this.dietCollection = dietCollection;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.User[ uid=" + uid + " ]";
    }

    public Integer getCalorieSetGoal() {
        return calorieSetGoal;
    }

    public void setCalorieSetGoal(Integer calorieSetGoal) {
        this.calorieSetGoal = calorieSetGoal;
    }
    
}
