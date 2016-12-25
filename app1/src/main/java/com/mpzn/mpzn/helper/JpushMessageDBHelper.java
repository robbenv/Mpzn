package com.mpzn.mpzn.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by larry on 16-11-17.
 */

public class JpushMessageDBHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String CREATE_JPUSH = "create table jpush ("
            + "id integer primary key autoincrement, "
            +  "aid text, "
            + "type text)";

    public JpushMessageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_JPUSH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
