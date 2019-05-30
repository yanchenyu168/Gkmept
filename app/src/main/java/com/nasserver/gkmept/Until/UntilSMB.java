package com.nasserver.gkmept.Until;

import com.nasserver.gkmept.Dao.FolderFile;
import com.nasserver.gkmept.Dao.ImageFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class UntilSMB {

    //region 通过smb协议将文件上传
    /**
     *
     * @param stringLocal
     * @param stringUserName
     * @param stringPassword
     * @param stringIp
     * @param stringPath
     * @throws Exception
     */
    public boolean smbUpload(String stringLocal,String stringUserName,String stringPassword,String stringIp,String stringPath,String stringFileName)throws Exception{
        File fileLocal=new File(stringLocal);
        InputStream inputStream=new FileInputStream(fileLocal);
        String stringRemote="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath+"/"+stringFileName;
        SmbFile smbFile=new SmbFile(stringRemote);
        if(smbFile.exists()){
            return false;
        }else {
            smbFile.connect();
            OutputStream outputStream=new SmbFileOutputStream(smbFile);
            byte[] buffer=new byte[4096];
            int len=0;
            while ((len=inputStream.read(buffer,0,buffer.length))!=-1){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            return true;
        }
    }
    //endregion
    //region 获取指定目录下的所有文件名以及文件夹名称
    /**
     *
     * @param stringUserName smb服务器用户名
     * @param stringPassword smb服务器密码
     * @param stringIp smb服务器IP
     * @param stringPath smb服务器路径
     * @param stringListDir 目录下文件名以及文件夹列表
     * @return
     */
    public List<FolderFile> smbDir(String stringUserName,String stringPassword,String stringIp,String stringPath,List<FolderFile> stringListDir){
        try {
            String stringDirUrl=null;
            if(stringPath.equals("")){
                 stringDirUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/";
            }else {
                 stringDirUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath+"/";
            }
            SmbFile smbFile=new SmbFile(stringDirUrl);
            SmbFile[] smbFiles=smbFile.listFiles();
            for(SmbFile file:smbFiles) {
                if(!file.getName().substring(0,1).equals(".")){
                    FolderFile folderFile=new FolderFile();
                    if(file.isDirectory()){
                        folderFile.setStringFileName(file.getName().substring(0,file.getName().length()-1));
                        folderFile.setStringFilePath(stringPath);
                        folderFile.setStringFileType(0);
                    }else {
                        folderFile.setStringFileName(file.getName());
                        folderFile.setStringFilePath(stringPath);
                        switch (file.getName().substring(file.getName().lastIndexOf(".")+1)){
                            //region 图片文件
                            case "png":
                                folderFile.setStringFileType(1);
                                break;
                            case "PNG":
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
                                default:
                                    folderFile.setStringFileType(1);
                                    break;
                            //endregion
                        }
                    }
                    stringListDir.add(folderFile);
                    }
            }
        }catch (Exception smbexception){
            smbexception.printStackTrace();
        }
        return stringListDir;
    }
    //endregion
    //region 创建文件夹
    public void smbMkDir(String stringUserName,String stringPassword,String stringIp,String stringPath,String stringFolderName)throws Exception {
        String stringDirUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath+"/"+ stringFolderName;
        SmbFile smbFileFolder = new SmbFile(stringDirUrl);
        if (!smbFileFolder.exists()) {
            smbFileFolder.mkdir();
        }
    }
    //endregion
    //region 删除文件
    /**
     *
     * @param stringUserName
     * @param stringPassword
     * @param stringIp
     * @param stringPath
     * @param stringFileName
     * @throws Exception
     */
    public void smbDelete(String stringUserName,String stringPassword,String stringIp,String stringPath,String stringFileName) throws Exception{
        String stringUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath+"/"+stringFileName;
        SmbFile smbFile=new SmbFile(stringUrl);
        if(smbFile.exists()){
            smbFile.delete();
        }
    }
    //endregion
    //region 下载文件
    public boolean smbDownload(String stringLocalPath,String stringUserName,String stringPassword,String stringIp,String stringPath,String stringFileName){
        InputStream inputStream=null;
        OutputStream outputStream=null;
        boolean isDownload=false;
        String stringUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath+"/"+stringFileName;
        try {
            SmbFile smbFile=new SmbFile(stringUrl);
            if(smbFile.exists()){
                File fileLocal=new File(stringLocalPath+File.separator+stringFileName);
                inputStream=new BufferedInputStream(new SmbFileInputStream(smbFile));
                outputStream=new BufferedOutputStream(new FileOutputStream(fileLocal));
                byte[] buffer=new byte[1024];
                while (inputStream.read(buffer)!=-1){
                    outputStream.write(buffer);
                    buffer=new byte[1024];
                }
                inputStream.close();
                outputStream.close();
                isDownload=true;
            }
        }catch (Exception exception){
            String string=exception.toString();
            isDownload=false;
        }
        return isDownload;
    }
    //endregion
    //region 创建本地文件夹
    public void creatFolder(String stringPath){
        File fileFolder=new File(stringPath);
        if(!fileFolder.exists()){
            fileFolder.mkdirs();
        }
    }
    //endregion
    //region 遍历获取文件
    public List<FolderFile> smbDirAllFile( String stringUserName,String stringPassword,String stringIp,String stringPath,List<FolderFile> stringListDir){
        try {
            String stringDirUrl="smb://"+stringUserName+":"+stringPassword+"@"+stringIp+"/"+stringPath;
            SmbFile smbFile=new SmbFile(stringDirUrl);
            SmbFile[] smbFiles=smbFile.listFiles();
            for(SmbFile file:smbFiles) {
                if(!file.getName().substring(0,1).equals(".")){
                    FolderFile folderFile=new FolderFile();
                    if(file.isDirectory()){
                        String stringPathAll=stringPath+file.getName();
                        smbDirAllFile(stringUserName,stringPassword,stringIp,stringPathAll,stringListDir);
                    }else {
                        folderFile.setStringFileName(file.getName());
                        folderFile.setStringFilePath(stringPath);
                        switch (file.getName().substring(file.getName().lastIndexOf(".")+1)){
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
                                default:
                                    folderFile.setStringFileType(4);
                                    break;
                            //endregion
                        }
                        stringListDir.add(folderFile);
                    }
                }
            }
        }catch (Exception smbexception){
            smbexception.printStackTrace();
        }
        return stringListDir;
    }
    //endregion
}
