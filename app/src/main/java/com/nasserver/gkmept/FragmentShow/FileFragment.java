package com.nasserver.gkmept.FragmentShow;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nasserver.gkmept.Adapter.AdapterFolderFile;
import com.nasserver.gkmept.Dao.FolderFile;
import com.nasserver.gkmept.Dao.ImageFile;
import com.nasserver.gkmept.MainActivity;
import com.nasserver.gkmept.R;
import com.nasserver.gkmept.Until.UntilSMB;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends Fragment {

    private RecyclerView recyclerViewFile;
    private AdapterFolderFile adapterFolderFile;
    private List<FolderFile> folderFileList=new ArrayList<>();
    private String stringIp="192.168.0.107";
    private String stringUserName="ycy";
    private String stringPassword="yzh140309yzh";
    private String stringPath="";

    public FileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //region 获取持久化数据
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        stringIp=sharedPreferences.getString("IP","192.168.0.107");
        stringUserName=sharedPreferences.getString("UserName","ycy");
        stringPassword=sharedPreferences.getString("Password","yzh140309yzh");
        stringPath=sharedPreferences.getString("Path","");
        //endregion

        View view=inflater.inflate(R.layout.fragment_file, container, false);
        recyclerViewFile=(RecyclerView)view.findViewById(R.id.recyclerviewFile);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
//        recyclerViewFile.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewFile.setLayoutManager(gridLayoutManager);
        adapterFolderFile=new AdapterFolderFile(folderFileList);
        recyclerViewFile.setAdapter(adapterFolderFile);
        new Thread(runnableDir).start();
        return view;
    }

    private Handler smbHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapterFolderFile.notifyDataSetChanged();
            }
        }
    };

    Runnable runnableDir=new Runnable() {
        @Override
        public void run() {
            try{
                folderFileList.clear();
                UntilSMB untilSMB=new UntilSMB();
                folderFileList=untilSMB.smbDir(stringUserName,stringPassword,stringIp,stringPath,folderFileList);
                Message message=new Message();
                message.what=0;
                smbHandler.sendMessage(message);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    };

}
