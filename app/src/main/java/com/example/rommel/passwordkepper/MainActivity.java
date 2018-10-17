package com.example.rommel.passwordkepper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //控件
    private Button changeUser;
    private Button addRecord;

    public static DatabaseHelper db;
    private UserData userData;
    private RecyclerView show;
    RecordAdapter recordAdapter;
    private String dbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();

    }

    /**
     * 初始化控件
     */
    private void initWidget(){
        Log.d(TAG,"in initWidget");
        userData = new UserData();
        readData();
        recordAdapter = new RecordAdapter(userData.getL());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        show = findViewById(R.id.showTable);
        show.setAdapter(recordAdapter);
        show.setLayoutManager(linearLayoutManager);
        changeUser = findViewById(R.id.changeUser);
        addRecord = findViewById(R.id.add);
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveData();
                finish();
            }
        });
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,Define.DATA_RETURN);
            }
        });
    }

    /**
     * 从intent("data_pass")中取得用户文件路径后读取数据到userData
     */
    private void readData(){
        Intent intent = getIntent();
        dbName = intent.getStringExtra("data_pass");
        db = new DatabaseHelper(this,dbName+".db",null,1);
        userData = db.getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case Define.DATA_RETURN:
                if(resultCode == RESULT_OK){
                    PassRecord Data =  data.getParcelableExtra("data_return");
                    Record newRecord = new Record(Data.getRemark(),Data.getPassword(),Data.getName());
                    newRecord.setId(db.insert(newRecord));
                    userData.add(newRecord);
                    recordAdapter.add(userData.getL().size());

                }
                break;
            case Define.DATA_UPDATE:
                if(resultCode == RESULT_OK){
                    if(data.getBooleanExtra("isUpdate",true)){
                        PassRecord passRecord = data.getParcelableExtra("data_return");
                        Record update = new Record(passRecord.getRemark(),passRecord.getPassword(),passRecord.getName());
                        int subscript = data.getIntExtra("subscript",-1);
                        userData.update(subscript,update);
                        recordAdapter.change(subscript);
                        db.update(update);
                    }
                }
            default:
                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        userData.getL().clear();
        db.clear();
    }
}
