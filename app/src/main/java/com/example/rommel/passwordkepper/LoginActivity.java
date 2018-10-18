package com.example.rommel.passwordkepper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    //按钮
    private Button login;
    private Button quit;
    private Button register;
    //输入框
    private EditText userName;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidget();
    }

    /**
     * 初始化按钮和输入框
     */
    private void initWidget(){
        Log.d(TAG,"init widget");
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        quit = findViewById(R.id.quit);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        userName.setFocusable(true);
        userName.requestFocus();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUserNameAndPassword() == Define.NO_ERROR){
                    String userNameString = userName.getText().toString();
                    String userPasswordString = userPassword.getText().toString();
                    String userDBName = getUserDataFileName(userNameString,userPasswordString);
                    SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                    boolean userAlreadyExists = sharedPreferences.getBoolean(userDBName,false);
                    if(!userAlreadyExists){
                        Toast.makeText(LoginActivity.this,R.string.loginActivity_userNotRegister,Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("data_pass",userDBName);
                        startActivity(intent);

                    }
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameString = userName.getText().toString();
                String userPasswordString = userPassword.getText().toString();
                if(checkUserNameAndPassword() == Define.NO_ERROR){
                    if(registerUser(userNameString,userPasswordString) != Define.NO_ERROR){
                        Toast.makeText(LoginActivity.this,R.string.loginActivity_userAlreadyExists,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,R.string.loginActivity_userRegisterSuccess,Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取保存该用户数据的文件的文件名
     * @param name 用户名
     * @param password 用户密码
     * @return 保存该用户的文件的文件名的字符串，不带文件名后缀
     */
    private String getUserDataFileName(String name,String password){
        StringBuffer fileName = new StringBuffer();
        fileName.append(name);
        for(char i : password.toCharArray()){
            fileName.append(Integer.valueOf((int)i));
        }
        return fileName.toString();
    }

    /**
     * 注册用户
     * @param name 用户名
     * @param password 用户密码
     * @return Define.USER_ALREADY_REGISTER 该用户已注册
     *         Define.NO_ERROR 注册成功
     */
    private int registerUser(String name,String password){
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        String userDBName = getUserDataFileName(name,password);
        boolean userAlreadyExists = sharedPreferences.getBoolean(userDBName,false);
        if(userAlreadyExists){
            Log.d(TAG,"user already exists");
            return Define.USER_ALREADY_REGISTER;
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(userDBName,true);
            editor.apply();
            return Define.NO_ERROR;
        }
    }

    /**
     * 检查用户名和密码是否合法
     * @return Define.USER_NAME_ERROR 用户名错误或不够6个字符
     *         Define.USER_PASSWORD_ERROR 用户密码非法或不够6个字符
     *         Define.NO_ERROR 用户名和密码合法
     */
    private int checkUserNameAndPassword(){
        String userNameString = userName.getText().toString();
        String userPasswordString = userPassword.getText().toString();
        if(userNameString.equals("") || userNameString.length() < 6){
            Log.d(TAG,"user name illegal");
            Toast.makeText(LoginActivity.this,R.string.loginActivity_userNameError,Toast.LENGTH_SHORT).show();
            return Define.USER_NAME_ERROR;
        }
        else if(userPasswordString.equals("") || userPasswordString.length() < 6){
            Log.d(TAG,"user password illegal");
            Toast.makeText(LoginActivity.this,R.string.loginActivity_userPasswordError,Toast.LENGTH_SHORT).show();
            return Define.USER_PASSWORD_ERROR;
        }
        else {
            return Define.NO_ERROR;
        }
    }

}
