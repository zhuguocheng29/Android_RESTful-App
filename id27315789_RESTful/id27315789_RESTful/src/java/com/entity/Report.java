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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Think
 */
@Entity
@Table(name = "report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByRid", query = "SELECT r FROM Report r WHERE r.rid = :rid"),
    @NamedQuery(name = "Report.findByTotalSteps", query = "SELECT r FROM Report r WHERE r.totalSteps = :totalSteps"),
    @NamedQuery(name = "Report.findByStepsCalorie", query = "SELECT r FROM Report r WHERE r.stepsCalorie = :stepsCalorie"),
    @NamedQuery(name = "Report.findByRestCalorie", query = "SELECT r FROM Report r WHERE r.restCalorie = :restCalorie"),
    @NamedQuery(name = "Report.findByTotalCalorieConsumed", query = "SELECT r FROM Report r WHERE r.totalCalorieConsumed = :totalCalorieConsumed"),
    @NamedQuery(name = "Report.findByTotalFoodCalorie", query = "SELECT r FROM Report r WHERE r.totalFoodCalorie = :totalFoodCalorie"),
 //   @NamedQuery(name = "Report.findByCalorieSetGoal", query = "SELECT r FROM Report r WHERE r.calorieSetGoal = :calorieSetGoal"),
    @NamedQuery(name = "Report.findByRemaining", query = "SELECT r FROM Report r WHERE r.remaining = :remaining"),
    @NamedQuery(name = "Report.findByReportDate", query = "SELECT r FROM Report r WHERE r.reportDate = :reportDate"),
    @NamedQuery(name = "Report.findByReportCalorieGoal", query = "SELECT r FROM Report r WHERE r.calorieGoal = :calorieGoal")})


public class Report implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "total_steps")
    private int totalSteps;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "steps_calorie")
    private float stepsCalorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rest_calorie")
    private float restCalorie;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_calorie_consumed")
    private float totalCalorieConsumed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_food_calorie")
    private float totalFoodCalorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calorie_goal")
    private int calorieGoal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "remaining")
    private float remaining;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Column(name = "report_date")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

    public Report() {
    }

    public Report(Integer rid) {
        this.rid = rid;
    }
//
//    public Report(Integer rid, float calorieSetGoal) {
//        this.rid = rid;
//        this.calorieSetGoal = calorieSetGoal;
//    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Float getStepsCalorie() {
        return stepsCalorie;
    }

    public void setStepsCalorie(Float stepsCalorie) {
        this.stepsCalorie = stepsCalorie;
    }

    public Float getRestCalorie() {
        return restCalorie;
    }

    public void setRestCalorie(Float restCalorie) {
        this.restCalorie = restCalorie;
    }

    public Float getTotalCalorieConsumed() {
        return totalCalorieConsumed;
    }

    public void setTotalCalorieConsumed(Float totalCalorieConsumed) {
        this.totalCalorieConsumed = totalCalorieConsumed;
    }

    public Float getTotalFoodCalorie() {
        return totalFoodCalorie;
    }

    public void setTotalFoodCalorie(Float totalFoodCalorie) {
        this.totalFoodCalorie = totalFoodCalorie;
    }


    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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
        hash += (rid != null ? rid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Report[ rid=" + rid + " ]";
    }


    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }
    
}
