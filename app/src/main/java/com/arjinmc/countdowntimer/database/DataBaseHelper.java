package com.arjinmc.countdowntimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arjinmc.countdowntimer.pojo.MussEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 2016/8/11.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sky_event";
    public static final String TABLE_NAME = "event_table";
    public static final int VERSION = 2;
    public static final String KEY_ID = "_id";
    public static String KEY_NAME = "name";
    public static final String KEY_CURRENT_LEFT_TIME = "currentLeftTime";
    public static final String KEY_LIMIT = "limit_";
    public static final String KEY_RUNNING = "running";

    //建表语句
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + KEY_ID +
            " integer primary key autoincrement," + KEY_NAME + " text not null," +
            KEY_CURRENT_LEFT_TIME + " integer," + KEY_LIMIT + " integer," + KEY_RUNNING + " integer)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void addEvent(MussEvent mussEvent) {
        SQLiteDatabase db = this.getWritableDatabase();

        //使用ContentValues添加数据
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, mussEvent.getName());
        values.put(KEY_CURRENT_LEFT_TIME, mussEvent.getCurrentLeftTime());
        values.put(KEY_LIMIT, mussEvent.getLimit());
        values.put(KEY_RUNNING, mussEvent.isRunning());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public MussEvent getEventsByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CURRENT_LEFT_TIME, KEY_LIMIT, KEY_RUNNING},
                "", new String[]{name}, null, null, null, null);


        MussEvent mussEvent = null;
        //注意返回结果有可能为空
        if (cursor.moveToFirst()) {
            mussEvent = new MussEvent(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        }
        return mussEvent;
    }

    public MussEvent getEventById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CURRENT_LEFT_TIME, KEY_LIMIT, KEY_RUNNING},
                "", new String[]{String.valueOf(id)}, null, null, null, null);


        MussEvent mussEvent = null;
        //注意返回结果有可能为空
        if (cursor.moveToFirst()) {
            mussEvent = new MussEvent(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        }
        return mussEvent;
    }

    public int getEventsCount() {
        String selectQuery = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有events
    public List<MussEvent> getAllEvents() {
        List<MussEvent> mussEventList = new ArrayList<MussEvent>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MussEvent mussEvent = new MussEvent();
                mussEvent.setId(Integer.parseInt(cursor.getString(0)));
                mussEvent.setName(cursor.getString(1));
                mussEvent.setCurrentLeftTime(cursor.getInt(2));
                mussEvent.setLimit(cursor.getInt(3));
                mussEvent.setRunning(cursor.getInt(4));
                mussEventList.add(mussEvent);
            } while (cursor.moveToNext());
        }
        return mussEventList;
    }

    //更新events
//    public int updateStudent(MussEvent student) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, student.getName());
//        values.put(KEY_GRADE, student.getGrade());
//
//        return db.update(TABLE_NAME, values, KEY_ID += ?,
//        new String[]{String.valueOf(student.getId())});
//    }

    public void deleteEvent(MussEvent mussEvent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + mussEvent.getId(), null);
        db.close();
    }

    public void deleteAllEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "", null);
        db.close();
    }

}
