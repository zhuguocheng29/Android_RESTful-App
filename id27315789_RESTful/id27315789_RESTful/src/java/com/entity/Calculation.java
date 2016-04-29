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
public class Calculation {

    float burned;
    float food;

    public float getBurned() {
        return burned;
    }

    public float getFood() {
        return food;
    }

    public void setBurned(float burned) {
        this.burned = burned;
    }

    public void setFood(float food) {
        this.food = food;
    }

    //calculate BMR
    public float calBMR(User user) {
        float bmr = 0;
        float weight = user.getWeight();
        float height = user.getHeight();
        int age = user.getAge();
        String gender = user.getGender();
        if (gender.equals("1")) {
            bmr = (float) ((13.75 * weight) + (5 * height) - (6.76 * age) + 66);
        } else {
            bmr = (float) ((9.56 * weight) + (1.85 * height) - (4.68 * age) + 655);
        }
        return bmr;
    }

    //calculate rest calorie
    public float calRest(User user) {
        float bmr = 0f;
        float weight = user.getWeight();
        float height = user.getHeight();
        int age = user.getAge();
        int level = user.getLevelOfActivity();
        String gender = user.getGender();
        float calorieNeed = 0f;
        if (gender.equals("1")) {
            bmr = (float) ((13.75 * weight) + (5 * height) - (6.76 * age) + 66);
        } else {
            bmr = (float) ((9.56 * weight) + (1.85 * height) - (4.68 * age) + 655);
        }

        switch (level) {
            case 1:
                calorieNeed = bmr * 1.2f;
                break;
            case 2:
                calorieNeed = bmr * 1.375f;
                break;
            case 3:
                calorieNeed = bmr * 1.55f;
                break;
            case 4:
                calorieNeed = bmr * 1.725f;
                break;
            case 5:
                calorieNeed = bmr * 1.9f;
                break;
            default:
                break;
        }
        return calorieNeed;
    }

    // calcualte per step consume how many calorie
    public float calSteps(User user) {
        //convert kg into lbs
        float weight = user.getWeight() * 2.2046f;
        int steps = user.getStepPerMile();
        float ratio = (float) (weight * 0.49 / steps);
        return ratio;
    }
        
    public static void main(String[] args)
    {
//        User user = new User();
//        //user.setUid(1);
//        user.setName("zhu");
//       // user.setPassword("123456");
//        user.setAge(24);
//        user.setHeight(179f);
//        user.setWeight(80f);
//        user.setGender("1");
//        user.setLevelOfActivity(3);
//        user.setStepPerMile(2200);
//        Calculation cal = new Calculation();
//       
//        System.out.print( cal.calSteps(user));
//        String test2 = "2016-13-31";
//        Boolean bol1 = test2.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])");
//        System.out.println("111"+bol1);
//        String test = "2016-03-31 26:19:20";
//        Boolean bol = test.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])\\p{Blank}(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})");
//        System.out.print(bol);
    }
     
    
}
