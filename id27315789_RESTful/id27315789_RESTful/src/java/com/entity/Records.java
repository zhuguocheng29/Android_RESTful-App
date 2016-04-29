/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

/**
 *
 * @author Think
 */
public class Records {
       
    private float foodCalorie;
    private float burned;
    private float goal ;
    private float remaining;
    private String date;

    public Records() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    public float getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(float foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    public float getBurned() {
        return burned;
    }

    public void setBurned(float burned) {
        this.burned = burned;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }
 
    
}
