package edu.monash.fit5183.Model;

import android.app.Application;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Think on 4/15/2016.
 */
public class ApplicationUser extends Application {
    private static String username;
    private static int steps;
/*
    private static List<HashMap<String,String>> ListArray;

    public static List<HashMap<String, String>> getListArray() {
        return ListArray;
    }

    public static void setListArray(List<HashMap<String, String>> listArray) {
        ListArray = listArray;
    }
*/

    public  int getSteps() {
        return steps;
    }

    public  void setSteps(int steps) {
        this.steps = steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
