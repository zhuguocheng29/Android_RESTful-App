package edu.monash.fit5183.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Think on 4/12/2016.
 */
public class User implements Parcelable {
    //Database constants
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "uid";
    public static final String COLUMN_NAME = "uname";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DATE = "registrationdate";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " VARCHAR(64) NOT NULL, " +
                    COLUMN_PASSWORD + " VARCHAR(64) NOT NULL, " +
                    COLUMN_DATE + " DATE NOT NULL, " +
                    COLUMN_LATITUDE + " real DEFAULT 31.274077, " +
                    COLUMN_LONGITUDE + " real DEFAULT 120.753769, "+
                    " UNIQUE ("+COLUMN_NAME+") "+
                    ")";
   // decimal(s,p)decimal(10,6)
    //properties
    private Integer uid;
    private String name;
    private String password;
    private String date;
    private float latitude;
    private float longitude;

    private int age;
    private float height;
    private float weight;
    private String gender;
    private int levelOfActivity;
    private int stepPerMile;
    private Integer calorieSetGoal;

    public User(String name, String password, int age, float height, float weight, String gender, int levelOfActivity, int stepPerMile) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.levelOfActivity = levelOfActivity;
        this.stepPerMile = stepPerMile;
    }

    public User(String name, String password, int age, float height, float weight, String gender, int levelOfActivity, int stepPerMile, Integer calorieSetGoal) {
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

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User( String name, String password, String date, float latitude, float longitude) {
        this.name = name;
        this.password = password;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected User(Parcel in) {
        uid = in.readInt();
        name = in.readString();
        password = in.readString();
  //      date = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
        age = in.readInt();
        height = in.readFloat();
        weight = in.readFloat();
        gender = in.readString();
        levelOfActivity = in.readInt();
        stepPerMile = in.readInt();
        calorieSetGoal = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitide(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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

    public Integer getCalorieSetGoal() {
        return calorieSetGoal;
    }

    public void setCalorieSetGoal(Integer calorieSetGoal) {
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
       // dest.writeString(date);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeInt(age);
        dest.writeFloat(height);
        dest.writeFloat(weight);
        dest.writeString(gender);
        dest.writeInt(levelOfActivity);
        dest.writeInt(stepPerMile);
        dest.writeInt(calorieSetGoal);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", levelOfActivity=" + levelOfActivity +
                ", stepPerMile=" + stepPerMile +
                ", calorieSetGoal=" + calorieSetGoal +
                '}';
    }

    //    private Collection<Diet> dietCollection;
//
//    private Collection<Report> reportCollection;

}
