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
    private byte[] key = new byte[]{-39,115,50,28,32,-30,-111,69,-41,-90,-86,53,-103,-77,58,125,-114,-2,34,-1,-29,-86,-119,-42,-106,-31,-111,-59,47,-34,-45,-83};
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
        //将原数据加密
        if(oldVersion == 1){
            UserData u = new UserData();
            Cursor c = db.query("t",null,null,null,null,null,null);
            if(c.moveToFirst()){
                do {
                    int accountIdx = c.getColumnIndex("account");
                    int passwordIdx = c.getColumnIndex("password");
                    int remarkIdx = c.getColumnIndex("remark");
                    Record r = new Record(c.getString(remarkIdx),c.getString(passwordIdx),c.getString(accountIdx));
                    r.setId(c.getInt(c.getColumnIndex("id")));
                    u.add(r);
                }
                while(c.moveToNext());
            }
            for(Record i : u.getL()){
                ContentValues contentValues = new ContentValues();
                String encryptName = Base64Utils.encodedStr(AESUtils.encrypt(i.getName().getBytes(),key));
                String encryptPassword = Base64Utils.encodedStr(AESUtils.encrypt(i.getPassword().getBytes(),key));
                String encryptRemark = Base64Utils.encodedStr((AESUtils.encrypt(i.getRemark().getBytes(),key)));
                contentValues.put("account",encryptName);
                contentValues.put("password",encryptPassword);
                contentValues.put("remark",encryptRemark);
                db.update("t",contentValues,"id=?",new String[]{String.valueOf(i.getId())});
            }
        }
    }

    public int insert(Record r){
        ContentValues contentValues = new ContentValues();
        String encryptName = Base64Utils.encodedStr(AESUtils.encrypt(r.getName().getBytes(),key));
        String encryptPassword = Base64Utils.encodedStr(AESUtils.encrypt(r.getPassword().getBytes(),key));
        String encryptRemark = Base64Utils.encodedStr((AESUtils.encrypt(r.getRemark().getBytes(),key)));
        contentValues.put("account",encryptName);
        contentValues.put("password",encryptPassword);
        contentValues.put("remark",encryptRemark);
        sqlDB.insert("t",null,contentValues);
        String[] val = new String[]{encryptRemark,encryptName,encryptPassword};
        Cursor c = sqlDB.query("t",null,"remark=? and account=? and password=?",val,null,null,null);
        c.moveToFirst();
        int idIdx = c.getColumnIndex("id");
        int ret = c.getInt(idIdx);
        return ret;
    }

    public void update(Record r){
        ContentValues contentValues = new ContentValues();
        String encryptName = Base64Utils.encodedStr(AESUtils.encrypt(r.getName().getBytes(),key));
        String encryptPassword = Base64Utils.encodedStr(AESUtils.encrypt(r.getPassword().getBytes(),key));
        String encryptRemark = Base64Utils.encodedStr((AESUtils.encrypt(r.getRemark().getBytes(),key)));
        contentValues.put("account",encryptName);
        contentValues.put("password",encryptPassword);
        contentValues.put("remark",encryptRemark);
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
                String account = new String(AESUtils.decrypt(Base64Utils.decodedStr(c.getString(accountIdx)),key));
                String password = new String(AESUtils.decrypt(Base64Utils.decodedStr(c.getString(passwordIdx)),key));
                String remark = new String(AESUtils.decrypt(Base64Utils.decodedStr(c.getString(remarkIdx)),key));
                Record r = new Record(remark,password,account);
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
