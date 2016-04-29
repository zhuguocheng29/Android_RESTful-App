package edu.monash.fit5183.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.monash.fit5183.Model.Step;
import edu.monash.fit5183.Model.User;

/**
 * Created by Think on 4/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CalorieDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_STATEMENT);
        db.execSQL(Step.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Step.TABLE_NAME);
        onCreate(db);
    }

    //add step
    public void addStep(Step step)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            ContentValues values = new ContentValues();
            values.put(Step.COLUMN_UID, step.getUid());
            values.put(Step.COLUMN_STEP,step.getStep());
            values.put(Step.COLUMN_DATETIME,step.getDatetime());
            db.insert(Step.TABLE_NAME,null,values);
        }catch (Exception e)
        {
            Log.e("Error","Error",e);
        }finally {
            db.close();
        }

    }

    //User database methods
    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();;
        try {
            ContentValues values = new ContentValues();
            values.put(User.COLUMN_NAME, user.getName());
            values.put(User.COLUMN_PASSWORD, user.getPassword());
            values.put(User.COLUMN_DATE, user.getDate());
            values.put(User.COLUMN_LATITUDE, user.getLatitude());
            values.put(User.COLUMN_LONGITUDE, user.getLongitude());
            db.insert(User.TABLE_NAME, null, values);

        }catch (Exception e)
        {
            Log.e("Error","Error",e);
        }finally {
            db.close();
        }
    }

    public List<String> getuserName()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + User.TABLE_NAME, null);
        List<String> uname = new ArrayList<String>();
        while (cursor.moveToNext())
        {
            String name = cursor.getString(1);
            uname.add(name);
        }
        cursor.close();
        db.close();
        return uname;
    }

    public int getUserId(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + User.TABLE_NAME + " where uname = ?" ,new String[]{name});
        int uid = 0;
        while (cursor.moveToNext())
        {
             uid = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return uid;
    }


    public List<HashMap<String,String>> getStepRecord(String name)
    {
        List<HashMap<String,String>> list_steps = new ArrayList<HashMap<String,String>>();
        int uid = getUserId(name);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? order by sid desc", new String[]{uid + ""});
        int i = 0;

        while (cursor.moveToNext())
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("Steps",cursor.getInt(2)+"");
           // System.out.println("add steps:" + cursor.getInt(2));
            map.put("DateTime", cursor.getString(3));
           // System.out.println("add dateime:" + cursor.getString(3));
            list_steps.add(map);
            //System.out.println("list_steps: " + list_steps);
        }
        cursor.close();
        db.close();
        return list_steps;
    }




    public boolean queryDayExist(String name, String steps_date)
    {
        int uid = getUserId(name);
        SQLiteDatabase db = this.getReadableDatabase();

        //System.out.println("steps_date1111  " + steps_date);
        Cursor cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? ", new String[]{uid + "", steps_date+"%"});
        if (cursor.moveToFirst() == false)
        {
            cursor.close();
            db.close();
            return false;
        }
        else
        {
            cursor.close();
            db.close();
            return true;
        }
    }






    public int queryDaySteps(String name, String steps_date)
    {
        int uid = getUserId(name);
        SQLiteDatabase db = this.getReadableDatabase();

        int dayStep = 0;
        //System.out.println("steps_date1111  " + steps_date);
        Cursor cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? ", new String[]{uid + "", steps_date+"%"});
        if (cursor.moveToFirst() == false)
        {
            cursor.close();
            db.close();
            return 0;
        }
        else if (cursor.moveToFirst() == true)
        {
            do {
                int every_steps = cursor.getInt(2);
                dayStep += every_steps;
                //System.out.println("7777777 +queryDaySteps  " + every_steps );
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
            return dayStep;
        }
        else
        {
            cursor.close();
            db.close();
            return dayStep;
        }
    }


    public List<HashMap<String,Integer>> getStepsPeriod(String name, String steps_date)
    {
        List<HashMap<String,Integer>> list_steps = new ArrayList<HashMap<String,Integer>>();
        int uid = getUserId(name);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? order by sid desc", new String[]{uid + "", steps_date + "%"});
        if (cursor1.moveToFirst() == false)
        {
            cursor1.close();
            db.close();
            return null;
        }
        else
        {
            Cursor cursor=null;
            for (int i = 0 ;i<24;i++)
            {
                if (i < 10)
                {
                    cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? order by sid desc", new String[]{uid + "", steps_date+" 0"+i+"%"});
                    int dayStep = 0;
                    while (cursor.moveToNext())
                    {
                        int every_steps = cursor.getInt(2);
                        dayStep += every_steps;
                    }
                    HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
                    hashMap.put("DateTime",i);
                    hashMap.put("Steps",dayStep);
                    list_steps.add(hashMap);
                }
                else
                {
                    cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? order by sid desc", new String[]{uid + "", steps_date+" "+i+"%"});
                    int dayStep = 0;
                    while (cursor.moveToNext())
                    {
                        int every_steps = cursor.getInt(2);
                        dayStep += every_steps;
                    }
                    HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
                    hashMap.put("DateTime",i);
                    hashMap.put("Steps",dayStep);
                    list_steps.add(hashMap);
                }

            }
            cursor.close();
            db.close();
           // System.out.println("1111" + list_steps.toString());
            return list_steps;
        }


    }



    public List<HashMap<String,String>> getStepRecordDate(String name, String steps_date)
    {
        List<HashMap<String,String>> list_steps = new ArrayList<HashMap<String,String>>();
        int uid = getUserId(name);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Step.TABLE_NAME + " where uid = ? and recordtime like ? order by sid desc", new String[]{uid + "", steps_date+"%"});
        int i = 0;

        while (cursor.moveToNext())
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("Steps",cursor.getInt(2)+"");
            map.put("DateTime", cursor.getString(3));
            list_steps.add(map);
            //System.out.println("111111 " + list_steps);
        }
        cursor.close();
        db.close();
        return list_steps;
    }

    public ArrayList<Float> getLaAlo(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + User.TABLE_NAME + " where uname = ?", new String[]{name});
        ArrayList<Float> arrayList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            //get lat
            float a = cursor.getFloat(4);
            arrayList.add(a);
            //System.out.println("aaaaaaaa" + cursor.getInt(4));
            //get lon
            float b = cursor.getFloat(5);
            arrayList.add(b);
            //System.out.println("bbbbbb" + b);
        }
        return arrayList;
    }
}
