package edu.monash.fit5183.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import edu.monash.fit5183.Util.DatabaseHelper;
import edu.monash.fit5183.Model.Step;

/**
 * Created by Think on 4/12/2016.
 */
public class testDB extends AndroidTestCase {
    public void testQuery(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Step.TABLE_NAME, null, null, null, null, null, null);
        //System.out.println("22222");
        while(cursor.moveToNext()){
            if (cursor.isNull(0) )
            {
                System.out.println("suc123");
            }
            long user_id = cursor.getLong(0);
            //long party_id = cursor.getLong(1);

            System.out.println(user_id + ":11111");

        }
    }
}
