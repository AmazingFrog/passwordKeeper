package com.example.rommel.passwordkepper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private Context context = null;
    private DatabaseHelper instance = null;
    private SQLiteDatabase sqlDB = null;

    DatabaseHelper(Context c, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(c,name,cursorFactory,version);
        if(instance == null){
            instance = this;
            context = c;
            sqlDB = instance.getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists t("+
                   "id integer primary key autoincrement,"+
                   "account text,"+
                   "password text,"+
                   "remark text"+
                   ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insert(Record r){
        ContentValues contentValues = new ContentValues();
        contentValues.put("account",r.getName());
        contentValues.put("password",r.getPassword());
        contentValues.put("remark",r.getRemark());
        sqlDB.insert("t",null,contentValues);
        Cursor c = sqlDB.query("t",null,"remark=? and account=? and password=?",new String[]{r.getRemark(),r.getName(),r.getPassword()},null,null,null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("id"));
    }

    public void update(Record r){
        ContentValues contentValues = new ContentValues();
        contentValues.put("account",r.getName());
        contentValues.put("password",r.getPassword());
        contentValues.put("remark",r.getRemark());
        sqlDB.update("t",contentValues,"id=?",new String[]{String.valueOf(r.getId())});
    }

    public void del(Record r){
        sqlDB.delete("t","id=?",new String[]{String.valueOf(r.getId())});
    }

    public UserData getData(){
        UserData ret = new UserData();
        Cursor c = sqlDB.query("t",null,null,null,null,null,null);
        if(c.moveToFirst()){
            do {

                int accountIdx = c.getColumnIndex("account");
                int passwordIdx = c.getColumnIndex("password");
                int remarkIdx = c.getColumnIndex("remark");
                Record r = new Record(c.getString(remarkIdx),c.getString(passwordIdx),c.getString(accountIdx));
                r.setId(c.getInt(c.getColumnIndex("id")));
                ret.add(r);
            }
            while(c.moveToNext());
        }
        return ret;
    }

    public void clear(){
        instance = null;
        sqlDB = null;
    }

}
