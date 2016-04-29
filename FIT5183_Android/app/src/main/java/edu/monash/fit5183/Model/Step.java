package edu.monash.fit5183.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Think on 4/12/2016.
 */
public class Step implements Parcelable {
    //Database constants
    public static final String TABLE_NAME = "steps";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_STEP = "step";
    public static final String COLUMN_DATETIME = "recordtime";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    COLUMN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_UID + " INT(11), " +
                    COLUMN_STEP + " INT(11) NOT NULL, " +
                    COLUMN_DATETIME + " DATETIME NOT NULL, " +
                    "FOREIGN KEY("+COLUMN_UID+")" + " REFERENCES users(" + COLUMN_UID +
                    "))";

    private int sid;
    private int uid;
    private int step;
    private String datetime;

    public Step(int uid, int step, String datetime) {
        this.uid = uid;
        this.step = step;
        this.datetime = datetime;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    protected Step(Parcel in) {
        sid = in.readInt();
        uid = in.readInt();
        step = in.readInt();
        datetime = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(sid);
        dest.writeInt(uid);
        dest.writeInt(step);
        dest.writeString(datetime);
    }
}
