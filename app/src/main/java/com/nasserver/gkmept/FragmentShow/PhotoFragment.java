package com.nasserver.gkmept.FragmentShow;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nasserver.gkmept.Adapter.AdapterFolderFile;
import com.nasserver.gkmept.Adapter.AdapterImageFile;
import com.nasserver.gkmept.Dao.FolderFile;
import com.nasserver.gkmept.Dao.ImageFile;
import com.nasserver.gkmept.R;
import com.nasserver.gkmept.Until.UntilSMB;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jcifs.smb.SmbFile;

/**
 *
 */
public class PhotoFragment extends Fragment {

    private RecyclerView recyclerViewPhoto;
    private List<ImageFile> imageFileList = new ArrayList<>();
    private List<ImageFile> smbFileLIst=new ArrayList<>();
    private List<FolderFile> folderFileList=new ArrayList<>();
    private AdapterFolderFile adapterFolderFile;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String stringIp="192.168.0.107";
    private String stringUserName="ycy";
    private String stringPassword="yzh140309yzh";
    private String stringPath="";

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerViewPhoto = (RecyclerView) view.findViewById(R.id.recyclerviewPhoto);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewPhoto.setLayoutManager(gridLayoutManager);
        adapterFolderFile=new AdapterFolderFile(folderFileList);
        recyclerViewPhoto.setAdapter(adapterFolderFile);
        new Thread(runnableDirAll).start();
        //region 获取持久化数据
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        stringIp=sharedPreferences.getString("IP","192.168.0.107");
        stringUserName=sharedPreferences.getString("UserName","ycy");
        stringPassword=sharedPreferences.getString("Password","yzh140309yzh");
        stringPath=sharedPreferences.getString("Path","");
        //endregion
        //region 获取本地相册图片并展示
//        Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        Cursor cursorImage = getActivity().getContentResolver().query(photoUri, null, null, null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
//        cursorImage.moveToFirst();
//        long longImageLastDay=new File(cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))).lastModified();//最新图像的时间
//        String stringImageLastDay = simpleDateFormat.format(longImageLastDay);
//        if (cursorImage == null || cursorImage.getCount() <= 0) {
//            Toast.makeText(getActivity(), "没有图片文件", Toast.LENGTH_SHORT).show();
//        } else {
//            ImageFile imageFileFirst = new ImageFile();
//            imageFileFirst.setAnIntTitle(1);//标记该记录为标题
//            imageFileFirst.setStringImageTime(stringImageLastDay);
//            boolean bIsTitle = false;
//            imageFileList.add(imageFileFirst);
//            do {
//                ImageFile imageFile = new ImageFile();
//                String stringFilePath = cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                String stringFileName=stringFilePath.substring(stringFilePath.lastIndexOf("/")+1,stringFilePath.length());
//                File file = new File(stringFilePath);
//                stringTime = simpleDateFormat.format(file.lastModified());
//                if (!stringTime.equals(stringImageLastDay)) {
//                    if (bIsTitle == true) {
//                        imageFileList.remove(imageFileList.size() - 1);
//                    }
//                    if (!stringTime.equals("1970-01-01")) {
//                        imageFile.setAnIntTitle(1);
//                        imageFile.setStringImageTime(stringTime);
//                        stringImageLastDay = stringTime;
//                        imageFileList.add(imageFile);
//                    }
//                    bIsTitle = true;
//                } else {
//                    imageFile.setAnIntTitle(0);
//                    imageFile.setAnIntImage(1);
//                    imageFile.setStringImagePath(stringFilePath);
//                    imageFile.setStringImageName(stringFileName);
//                    imageFileList.add(imageFile);
//                    smbFileLIst.add(imageFile);
//                    bIsTitle = false;
//                }
//            } while (cursorImage.moveToNext());
//        }
//        AdapterImageFile adapterImageFile=new AdapterImageFile(imageFileList);
//        recyclerViewPhoto.setAdapter(adapterImageFile);
        //endregion

        return view;
    }

    Handler handlerSmb=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    adapterFolderFile.notifyDataSetChanged();
                    break;
            }
        }
    };

    Runnable runnableDirAll=new Runnable() {
        @Override
        public void run() {
            UntilSMB untilSMB=new UntilSMB();
            try {
                folderFileList=untilSMB.smbDirAllFile(stringUserName,stringPassword,stringIp,stringPath,folderFileList);
                Message message=new Message();
                message.what=1;
                handlerSmb.sendMessage(message);
            }catch (Exception ex){

            }
        }
    };

    //region 自动更新图片视频文件夹
    Runnable runnableUpload=new Runnable() {
        @Override
        public void run() {
                UntilSMB untilSMB=new UntilSMB();
                try{
                    for(ImageFile imageFile:smbFileLIst){
                        untilSMB.smbUpload(imageFile.getStringImagePath(),stringUserName,stringPassword,stringIp,stringPath,imageFile.getStringImageName());
                    }
                    Message message=new Message();
                    message.what=0;
                    handlerSmb.sendMessage(message);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
    };
    //endregion



}
