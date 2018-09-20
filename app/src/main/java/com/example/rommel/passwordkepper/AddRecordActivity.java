package com.example.rommel.passwordkepper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity {


    private EditText remake;
    private EditText name;
    private EditText password;
    private Button add;
    private Record r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget(){
        remake = findViewById(R.id.addRemake);
        name = findViewById(R.id.addUserName);
        password = findViewById(R.id.addUserPassword);
        add = findViewById(R.id.confirm);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecord();
            }
        });
    }

    /**
     * 检查并生成record
     */
    private void initRecord(){
        if(password.getText().toString().equals("")) {
            Toast.makeText(AddRecordActivity.this,"password error",Toast.LENGTH_SHORT).show();
        }
        else if(remake.getText().toString().equals("")){
            Toast.makeText(AddRecordActivity.this,"remake error",Toast.LENGTH_SHORT).show();
        }
        else{
            r = new Record(remake.getText().toString(),password.getText().toString(),name.getText().toString());
            PassRecord passRecord = new PassRecord(r);
            Intent intent = new Intent();
            intent.putExtra("data_return",passRecord);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
