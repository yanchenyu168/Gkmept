package com.nasserver.gkmept;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nasserver.gkmept.Adapter.AdapterFolderFile;
import com.nasserver.gkmept.Dao.FolderFile;
import com.nasserver.gkmept.Until.UntilSMB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileShowActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFileShow;
    private AdapterFolderFile adapterFolderFile;
    private List<FolderFile> folderFileList = new ArrayList<>();
    private String stringPath = "gkemt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_show);
        recyclerViewFileShow = (RecyclerView) findViewById(R.id.recyclerviewFileShow);
        File[] listFiles = Environment.getExternalStorageDirectory().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().substring(0, 1).equals(".") == false) {
                    if (!file.getName().substring(0, 1).equals(".")) {
                        FolderFile folderFile = new FolderFile();
                        if (file.isDirectory()) {
                            folderFile.setStringFileName(file.getName().substring(0, file.getName().length() - 1));
                            folderFile.setStringFilePath(stringPath);
                            folderFile.setStringFileType(0);
                        } else {
                            folderFile.setStringFileName(file.getName());
                            folderFile.setStringFilePath(stringPath);
                            switch (file.getName().substring(file.getName().lastIndexOf(".") + 1)) {
                                //region 图片文件
                                case "png":
                                    folderFile.setStringFileType(1);
                                    break;
                                case "jpg":
                                    folderFile.setStringFileType(1);
                                    break;
                                //endregion
                                //region 音频文件
                                case "mp3":
                                    folderFile.setStringFileType(2);
                                    break;
                                //endregion
                                //region 视频文件
                                case "avi":
                                    folderFile.setStringFileType(3);
                                    break;
                                //endregion
                                //region
                                case "pdf":
                                    folderFile.setStringFileType(4);
                                    break;
                                //endregion
                            }
                        }
                        folderFileList.add(folderFile);
                    }
                }
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewFileShow.setLayoutManager(gridLayoutManager);
        adapterFolderFile = new AdapterFolderFile(folderFileList);
        recyclerViewFileShow.setAdapter(adapterFolderFile);
    }
}
