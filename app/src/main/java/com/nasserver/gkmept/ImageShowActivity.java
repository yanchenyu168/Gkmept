package com.nasserver.gkmept;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nasserver.gkmept.Adapter.AdapterImageFile;
import com.nasserver.gkmept.Dao.ImageFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImageShowActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPhoto;
    private List<ImageFile> imageFileList = new ArrayList<>();
    private List<ImageFile> smbFileLIst=new ArrayList<>();
    private String stringTime;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String stringIp="192.168.0.107";
    private String stringUserName="ycy";
    private String stringPassword="yzh140309yzh";
    private String stringPath="gkemt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        recyclerViewPhoto = (RecyclerView) findViewById(R.id.recyclerviewImageVideoShow);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewPhoto.setLayoutManager(gridLayoutManager);

        //region 获取持久化数据
        SharedPreferences sharedPreferences=this.getSharedPreferences("data", Context.MODE_PRIVATE);
        stringIp=sharedPreferences.getString("IP","192.168.0.107");
        stringUserName=sharedPreferences.getString("UserName","ycy");
        stringPassword=sharedPreferences.getString("Password","yzh140309yzh");
        stringPath=sharedPreferences.getString("Path","gkemt");
        //endregion
        //region 获取本地相册图片并展示
        Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursorImage = this.getContentResolver().query(photoUri, null, null, null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
        cursorImage.moveToFirst();
        long longImageLastDay=new File(cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))).lastModified();//最新图像的时间
        String stringImageLastDay = simpleDateFormat.format(longImageLastDay);
        if (cursorImage == null || cursorImage.getCount() <= 0) {
            Toast.makeText(this, "没有图片文件", Toast.LENGTH_SHORT).show();
        } else {
            ImageFile imageFileFirst = new ImageFile();
            imageFileFirst.setAnIntTitle(1);//标记该记录为标题
            imageFileFirst.setStringImageTime(stringImageLastDay);
            boolean bIsTitle = false;
            imageFileList.add(imageFileFirst);
            do {
                ImageFile imageFile = new ImageFile();
                String stringFilePath = cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String stringFileName=stringFilePath.substring(stringFilePath.lastIndexOf("/")+1,stringFilePath.length());
                File file = new File(stringFilePath);
                stringTime = simpleDateFormat.format(file.lastModified());
                if (!stringTime.equals(stringImageLastDay)) {
                    if (bIsTitle == true) {
                        imageFileList.remove(imageFileList.size() - 1);
                    }
                    if (!stringTime.equals("1970-01-01")) {
                        imageFile.setAnIntTitle(1);
                        imageFile.setStringImageTime(stringTime);
                        stringImageLastDay = stringTime;
                        imageFileList.add(imageFile);
                    }
                    bIsTitle = true;
                } else {
                    imageFile.setAnIntTitle(0);
                    imageFile.setAnIntImage(1);
                    imageFile.setStringImagePath(stringFilePath);
                    imageFile.setStringImageName(stringFileName);
                    imageFileList.add(imageFile);
                    smbFileLIst.add(imageFile);
                    bIsTitle = false;
                }
            } while (cursorImage.moveToNext());
        }
        AdapterImageFile adapterImageFile=new AdapterImageFile(imageFileList);
        recyclerViewPhoto.setAdapter(adapterImageFile);
        //endregion
    }
}
