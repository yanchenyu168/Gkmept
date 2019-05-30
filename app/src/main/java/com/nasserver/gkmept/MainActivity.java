package com.nasserver.gkmept;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region 初始化控件
    private Button buttonSubmit;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextIp;
    private String stringLocalFolder="/mnt/sdcard/gkmept";
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //region 创建存储目录
        File fileFolder=new File(stringLocalFolder);
        if(!fileFolder.exists()){
            fileFolder.mkdirs();
        }
        //endregion
        //region 实例化控件
        buttonSubmit=(Button)findViewById(R.id.buttonMainSubmit);
        editTextUserName=(EditText)findViewById(R.id.editMainUserName);
        editTextPassword=(EditText)findViewById(R.id.editMainPassword);
        editTextIp=(EditText)findViewById(R.id.editMainIp);
        //endregion
        //region 获取存储的用户名
        SharedPreferences sharedPreferences=MainActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        editTextIp.setText(sharedPreferences.getString("IP",""));
        editTextPassword.setText(sharedPreferences.getString("Password",""));
        editTextUserName.setText(sharedPreferences.getString("UserName",""));
        //endregion
        //region 获取存储和录音权限，防止闪退
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }//如果没有写外部扩展卡的授权，则申请写外部扩展卡的授权
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CHANGE_WIFI_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_WIFI_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.INTERNET);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WAKE_LOCK);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)
                !=PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            Toast.makeText(this, "请开启存储权限和录音权限", Toast.LENGTH_SHORT).show();
            return;
        }
        //endregion
        //region登陆按钮
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0,InputMethodManager.RESULT_SHOWN);
                if(editTextUserName.getText().toString().trim().length()>0
                        &&editTextPassword.getText().toString().trim().length()>0
                        &&editTextIp.getText().toString().trim().length()>0){
                    SharedPreferences.Editor editor=getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    editor.putString("IP",editTextIp.getText().toString().trim());
                    editor.putString("UserName",editTextUserName.getText().toString().trim());
                    editor.putString("Password",editTextPassword.getText().toString().trim());
                    editor.apply();
                    Intent intent=new Intent(MainActivity.this,BottomActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"用户名和密码不能为空",Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion
    }
}
