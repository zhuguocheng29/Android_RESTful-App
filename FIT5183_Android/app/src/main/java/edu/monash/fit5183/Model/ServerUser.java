package edu.monash.fit5183.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Think on 4/14/2016.
 */
public class ServerUser implements Parcelable{ //
    //properties
    private Integer uid;
    private String name;
    private String password;
    private int age;
    private float height;
    private float weight;
    private String gender;
    private int levelOfActivity;
    private int stepPerMile;
    private int calorieSetGoal;

/*    public ServerUser(String name, String password, int age, float height, float weight, String gender, int levelOfActivity, int stepPerMile) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.levelOfActivity = levelOfActivity;
        this.stepPerMile = stepPerMile;
    }*/


    public ServerUser(String name, String password, int age, float height, float weight, String gender, int levelOfActivity, int stepPerMile, int calorieSetGoal) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.levelOfActivity = levelOfActivity;
        this.stepPerMile = stepPerMile;
        this.calorieSetGoal = calorieSetGoal;
    }

    protected ServerUser(Parcel in) {
        uid = in.readInt();
        name = in.readString();
        password = in.readString();
        age = in.readInt();
        height = in.readFloat();
        weight = in.readFloat();
        gender = in.readString();
        levelOfActivity = in.readInt();
        stepPerMile = in.readInt();
        calorieSetGoal = in.readInt();
    }

    public static final Creator<ServerUser> CREATOR = new Creator<ServerUser>() {
        @Override
        public ServerUser createFromParcel(Parcel in) {
            return new ServerUser(in);
        }

        @Override
        public ServerUser[] newArray(int size) {
            return new ServerUser[size];
        }
    };

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getCalorieSetGoal() {
        return calorieSetGoal;
    }

    public void setCalorieSetGoal(int calorieSetGoal) {
        this.calorieSetGoal = calorieSetGoal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeInt(age);
        dest.writeFloat(height);
        dest.writeFloat(weight);
        dest.writeString(gender);
        dest.writeInt(levelOfActivity);
        dest.writeInt(stepPerMile);
        dest.writeInt(calorieSetGoal);
    }

}
