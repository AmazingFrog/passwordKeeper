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

    private UserData userData;
    private RecyclerView show;
    RecordAdapter recordAdapter;
    private String filePath;

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
                saveData();
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
        filePath = intent.getStringExtra("data_pass");
        try{
            FileInputStream Fin=new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(Fin);
            BufferedReader Bin = new BufferedReader(reader);
            StringBuffer total = new StringBuffer();
            String line;
            while((line=Bin.readLine()) != null){
                total.append(line);
            }
            //初始化链表
            JSONObject j = new JSONObject(total.toString());
            JSONArray array = j.getJSONArray("root");
            for(int i=0; i<array.length();++i){
                JSONObject re = array.getJSONObject(i);
                userData.add(new Record(re.getString("remake"),re.getString("password"),re.getString("name")));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 将userData中的数据以json的格式保存到用户文件
     */
    private void saveData(){
        try{
            JSONArray array = new JSONArray();
            JSONObject data = new JSONObject();
            for(Record i:userData.getL()){
                JSONObject j = new JSONObject();
                j.put("remake",i.getRemake());
                j.put("password",i.getPassword());
                j.put("name",i.getName());
                array.put(j);
            }
            data.put("root",array);
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(data.toString().getBytes());
            fos.close();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case Define.DATA_RETURN:
                if(resultCode == RESULT_OK){
                    PassRecord Data =  data.getParcelableExtra("data_return");
                    Record newRecord = new Record(Data.getRemake(),Data.getPassword(),Data.getName());
                    userData.add(newRecord);
                    recordAdapter.add(userData.getL().size());
                }
                break;
            case Define.DATA_UPDATE:
                if(resultCode == RESULT_OK){
                    if(data.getBooleanExtra("isUpdate",true)){
                        PassRecord passRecord = data.getParcelableExtra("data_return");
                        Record update = new Record(passRecord.getRemake(),passRecord.getPassword(),passRecord.getName());
                        int subscript = data.getIntExtra("subscript",-1);
                        userData.update(subscript,update);
                        recordAdapter.change(subscript);
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
    }
}
