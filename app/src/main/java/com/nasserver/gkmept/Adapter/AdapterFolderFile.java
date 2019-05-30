package com.nasserver.gkmept.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nasserver.gkmept.Dao.FolderFile;
import com.nasserver.gkmept.EnlargeImageActivity;
import com.nasserver.gkmept.R;
import com.nasserver.gkmept.Until.OpenFile;
import com.nasserver.gkmept.Until.UntilSMB;

import java.io.File;
import java.util.List;

public class AdapterFolderFile extends RecyclerView.Adapter<AdapterFolderFile.FileViewHolder> {

    private List<FolderFile> folderFileList;
    private Context folderFileContext;
    private View folderFileView;
    private String stringIp="192.168.0.107";
    private String stringUserName="ycy";
    private String stringPassword="yzh140309yzh";
    private String stringPath="gkemt";

//    private String stringIp="192.168.0.106";
//    private String stringUserName="nas";
//    private String stringPassword="nas";
//    private String stringPath="nas";
    private String stringLocalpath="/mnt/sdcard/gkmept";
    private String stringFileName;
    private int anIntPosition;
    private int anIntFileType;

    public AdapterFolderFile(List<FolderFile> folderFiles){
        folderFileList=folderFiles;
    }

    class FileViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewFileIcon;
        TextView textViewName;
        View viewFileFragment;
        public FileViewHolder(View view){
            super(view);
            viewFileFragment=view;
            textViewName=view.findViewById(R.id.textViewFileName);
            imageViewFileIcon=view.findViewById(R.id.imageViewFileImage);
        }
    }



    @Override
    public int getItemCount() {
        return folderFileList.size();
    }


    @NonNull
    @Override
    public AdapterFolderFile.FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int postion) {
        folderFileContext=viewGroup.getContext();
        //region 获取持久化数据
        SharedPreferences sharedPreferences=folderFileContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        stringIp=sharedPreferences.getString("IP","192.168.0.107");
        stringUserName=sharedPreferences.getString("UserName","ycy");
        stringPassword=sharedPreferences.getString("Password","yzh140309yzh");
        stringPath=sharedPreferences.getString("Path","/");
        //endregion 长按弹出菜单
        final View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.folder_file_item,viewGroup,false);
        final FileViewHolder fileViewHolder=new FileViewHolder(view);
        fileViewHolder.viewFileFragment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final PopupMenu popupMenu=new PopupMenu(folderFileContext,view);
                popupMenu.getMenuInflater().inflate(R.menu.popumenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    UntilSMB untilSMB=new UntilSMB();
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menuPopupCheck:
                                break;
                            case R.id.menuPopupDownload:
                                stringPath=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFilePath();
                                stringFileName=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileName();
                                anIntPosition=fileViewHolder.getAdapterPosition();
                                new Thread(runnableDownload).start();
                                folderFileView=v;
                                break;
                            case R.id.menuPopupDelete:
                                stringPath=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFilePath();
                                stringFileName=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileName();
                                anIntPosition=fileViewHolder.getAdapterPosition();
                                new Thread(runnableDelete).start();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

        fileViewHolder.viewFileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anIntFileType=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileType();
                switch (folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileType()){
                    case 0:
                        stringPath=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFilePath()+"/"+folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileName();
                        new Thread(runnableDir).start();
                        break;
                    case 1:
                        stringPath=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFilePath();
                        stringFileName=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileName();
                        anIntPosition=fileViewHolder.getAdapterPosition();
                        new Thread(runnableDownload).start();
                        folderFileView=v;
                        break;
                    case 4:
                        stringPath=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFilePath();
                        stringFileName=folderFileList.get(fileViewHolder.getAdapterPosition()).getStringFileName();
                        anIntPosition=fileViewHolder.getAdapterPosition();
                        new Thread(runnableDownload).start();
                        folderFileView=v;
                        break;

                }

            }
        });
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFolderFile.FileViewHolder fileViewHolder, int position) {
        FolderFile folderFile=folderFileList.get(position);
        if(new File(folderFile.getStringFileName())!=null){
            fileViewHolder.textViewName.setText(folderFile.getStringFileName());
            switch (folderFile.getStringFileType()){
                case 0:
                    Glide.with(folderFileContext).load(R.drawable.foldernormal2x).into(fileViewHolder.imageViewFileIcon);
                    break;
                case 1:
                    Glide.with(folderFileContext).load(R.drawable.toolbarpic2x).into(fileViewHolder.imageViewFileIcon);
                    break;
                case 2:
                    Glide.with(folderFileContext).load(R.drawable.toolbarpic2x).into(fileViewHolder.imageViewFileIcon);
                    break;
                case 3:
                    Glide.with(folderFileContext).load(R.drawable.toolbarpic2x).into(fileViewHolder.imageViewFileIcon);
                    break;
                case 4:
                    Glide.with(folderFileContext).load(R.drawable.toolbarpic2x).into(fileViewHolder.imageViewFileIcon);
                    break;
            }
        }
    }

    //region smb协议删除指令
    Runnable runnableDelete=new Runnable() {
        @Override
        public void run() {
            UntilSMB untilSMB=new UntilSMB();
            try {
                untilSMB.smbDelete(stringUserName,stringPassword,stringIp,stringPath,stringFileName);
                Message message=new Message();
                message.what=1;
                handlerSamba.sendMessage(message);
            }catch (Exception exception){

            }
        }
    };
    //endregion
    //region smb协议获取目录下文件夹以及文件名
    Runnable runnableDir=new Runnable() {
        @Override
        public void run() {
                try {
                    UntilSMB untilSMB = new UntilSMB();
                    folderFileList.clear();
                    folderFileList = untilSMB.smbDir(stringUserName, stringPassword, stringIp, stringPath, folderFileList);
                    Message message=new Message();
                    message.what=0;
                    handlerSamba.sendMessage(message);
                } catch (Exception exception) {
                }
            }
    };
    //endregion
    //region smb协议下载文件
    Runnable runnableDownload=new Runnable() {
        @Override
        public void run() {
            try{
                UntilSMB untilSMB=new UntilSMB();
                untilSMB.smbDownload(stringLocalpath,stringUserName,stringPassword,stringIp,stringPath,stringFileName);
                Message message=new Message();
                message.what=2;
                handlerSamba.sendMessage(message);
            }catch (Exception exception){

            }
        }
    };
    //endregion

    private Handler handlerSamba=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    AdapterFolderFile.this.notifyDataSetChanged();
                    break;
                case 1:
                    folderFileList.remove(anIntPosition);
                    AdapterFolderFile.this.notifyDataSetChanged();
                    break;
                case 2:
                    switch (anIntFileType){
                        case 1:
                            Intent intent=new Intent(folderFileView.getContext(), EnlargeImageActivity.class);
                            intent.putExtra("ImagePath",stringLocalpath+"/"+stringFileName);
                            folderFileView.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)folderFileView.getContext(),folderFileView,"shareView").toBundle());
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            Intent intentPdf=OpenFile.getPdfFileIntent(stringLocalpath+"/"+stringFileName);
                            folderFileView.getContext().startActivity(intentPdf);
                            break;
                    }

            }
        }
    };
}
