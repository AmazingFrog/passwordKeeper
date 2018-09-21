package com.example.rommel.passwordkepper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShowDataActivity extends AppCompatActivity {
    private static final String TAG = "ShowDataActivity";

    private EditText remake;
    private EditText password;
    private EditText name;

    private Button confirm;
    private Button modify;

    boolean isModify;
    int modifySubscript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget(){
        isModify = false;
        remake = findViewById(R.id.show_remake);
        password = findViewById(R.id.show_password);
        name = findViewById(R.id.show_name);
        Intent intent = getIntent();
        PassRecord passRecord = intent.getParcelableExtra("data_pass");
        modifySubscript = intent.getIntExtra("subscript",-1);
        remake.setText(passRecord.getRemake());
        remake.setFocusable(false);
        password.setText(passRecord.getPassword());
        password.setFocusable(false);
        name.setText(passRecord.getName());
        name.setFocusable(false);

        confirm = findViewById(R.id.confirm);
        modify = findViewById(R.id.modify);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isModify){
                    Intent intent = new Intent();
                    intent.putExtra("isUpdate",false);
                    setResult(RESULT_OK,intent);
                }
                else {
                    if(checkUserNameAndPassword() == Define.NO_ERROR){
                        PassRecord passRecord = new PassRecord(remake.getText().toString(),password.getText().toString(),name.getText().toString());
                        Intent intent = new Intent();
                        intent.putExtra("isUpdate",true);
                        intent.putExtra("subscript",modifySubscript);
                        intent.putExtra("data_return",passRecord);
                        setResult(RESULT_OK,intent);
                    }
                }
                finish();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remake.setFocusable(true);
                remake.setFocusableInTouchMode(true);
                password.setFocusable(true);
                password.setFocusableInTouchMode(true);
                name.setFocusable(true);
                name.setFocusableInTouchMode(true);
                isModify = true;
            }
        });
    }

    /**
     * 检查用户名和密码是否合法
     * @return Define.USER_NAME_ERROR 用户名错误
     *         Define.USER_PASSWORD_ERROR 用户密码非法
     *         Define.NO_ERROR 用户名和密码合法
     */
    private int checkUserNameAndPassword(){
        String userNameString = name.getText().toString();
        String userPasswordString = password.getText().toString();
        if(userNameString.equals("")){
            Log.d(TAG,"user name illegal");
            Toast.makeText(ShowDataActivity.this,R.string.loginActivity_userNameError,Toast.LENGTH_SHORT).show();
            return Define.USER_NAME_ERROR;
        }
        else if(userPasswordString.equals("")){
            Log.d(TAG,"user password illegal");
            Toast.makeText(ShowDataActivity.this,R.string.loginActivity_userPasswordError,Toast.LENGTH_SHORT).show();
            return Define.USER_PASSWORD_ERROR;
        }
        else {
            return Define.NO_ERROR;
        }
    }
}
