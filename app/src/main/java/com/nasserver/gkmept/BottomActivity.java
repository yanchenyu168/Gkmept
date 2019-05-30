package com.nasserver.gkmept;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nasserver.gkmept.FragmentShow.FileFragment;
import com.nasserver.gkmept.FragmentShow.MineFragment;
import com.nasserver.gkmept.FragmentShow.PhotoFragment;
import com.nasserver.gkmept.FragmentShow.RecentFragment;

public class BottomActivity extends AppCompatActivity {

    //region 初始化控件
//    private TextView mTextMessage;
    private MineFragment mineFragment;
    private FileFragment fileFragment;
    private PhotoFragment photoFragment;
    private RecentFragment recentFragment;
    private PopupWindow popupWindow;
    private Button buttonImageVideo;
    private Button buttonFile;
    private Button buttonFolder;
    private ImageButton imageButtonImageVideo;
    private ImageButton imageButtonFile;
    private ImageButton imageButtonFolder;
    private ImageButton imageButtonClose;
    //endregion

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recent:
//                    mTextMessage.setText(R.string.title_recent);
                    showFragment(R.id.navigation_recent);
                    return true;
                case R.id.navigation_file:
//                    mTextMessage.setText(R.string.title_file);
                    showFragment(R.id.navigation_file);
                    return true;
                case R.id.navigation_windows:
                    popuwindowShow();
                    return true;
                case R.id.navigation_photo:
//                    mTextMessage.setText(R.string.title_photo);
                    showFragment(R.id.navigation_photo);
                    return true;
                case R.id.navigation_mine:
//                    mTextMessage.setText(R.string.title_mine);
                    showFragment(R.id.navigation_mine);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initFragment();
//        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initFragment(){
        recentFragment=new RecentFragment();
        fileFragment=new FileFragment();
        photoFragment=new PhotoFragment();
        mineFragment=new MineFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainContent,recentFragment).add(R.id.mainContent,fileFragment)
                .add(R.id.mainContent,photoFragment).add(R.id.mainContent,mineFragment);
        fragmentTransaction.hide(fileFragment).hide(photoFragment).hide(mineFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showFragment(int navigationId){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        switch (navigationId){
            case R.id.navigation_recent:
                fragmentTransaction.hide(fileFragment).hide(photoFragment).hide(mineFragment);
                fragmentTransaction.show(recentFragment);
                break;
            case R.id.navigation_file:
                fragmentTransaction.hide(recentFragment).hide(photoFragment).hide(mineFragment);
                fragmentTransaction.show(fileFragment);
                break;
            case R.id.navigation_photo:
                fragmentTransaction.hide(fileFragment).hide(recentFragment).hide(mineFragment);
                fragmentTransaction.show(photoFragment);
                break;
            case R.id.navigation_mine:
                fragmentTransaction.hide(fileFragment).hide(photoFragment).hide(recentFragment);
                fragmentTransaction.show(mineFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void popuwindowShow(){
        View contentView= LayoutInflater.from(BottomActivity.this).inflate(R.layout.popuwindow,null);
        contentView.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(contentView);


        buttonImageVideo=(Button)contentView.findViewById(R.id.buttonImageVideo);
        buttonImageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImageVideo=new Intent(BottomActivity.this,ImageShowActivity.class);
                startActivity(intentImageVideo);
            }
        });
        imageButtonImageVideo=(ImageButton)contentView.findViewById(R.id.imageButtonImageVideo);
        imageButtonImageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImageVideo=new Intent(BottomActivity.this,ImageShowActivity.class);
                startActivity(intentImageVideo);
            }
        });



        buttonFile=(Button)contentView.findViewById(R.id.buttonFile);
        buttonFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFile=new Intent(BottomActivity.this,FileShowActivity.class);
                startActivity(intentFile);
            }
        });
        imageButtonFile=(ImageButton)contentView.findViewById(R.id.imageButtonFile);
        imageButtonFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFile=new Intent(BottomActivity.this,FileShowActivity.class);
                startActivity(intentFile);
            }
        });

        buttonFolder=(Button)contentView.findViewById(R.id.buttonFolder);
        buttonFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageButtonFolder=(ImageButton)contentView.findViewById(R.id.imageButtonFolder);
        imageButtonFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        imageButtonClose=(ImageButton)contentView.findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        View rootview = LayoutInflater.from(BottomActivity.this).inflate(R.layout.activity_bottom, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);
    }

}
