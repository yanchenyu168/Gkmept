package com.nasserver.gkmept.Dao;

public class FolderFile {

    private String stringFileName;
    private int stringFileType;
    private String stringFilePath;
    private String stringFileTime;

    public String getStringFileName() {
        return stringFileName;
    }

    public void setStringFileName(String stringFileName) {
        this.stringFileName = stringFileName;
    }

    public int getStringFileType() {
        return stringFileType;
    }

    public void setStringFileType(int stringFileType) {
        this.stringFileType = stringFileType;
    }

    public String getStringFilePath() {
        return stringFilePath;
    }

    public void setStringFilePath(String stringFilePath) {
        this.stringFilePath = stringFilePath;
    }

    public String getStringFileTime() {
        return stringFileTime;
    }

    public void setStringFileTime(String stringFileTime) {
        this.stringFileTime = stringFileTime;
    }

    @Override
    public String toString() {
        return "FolderFile{" +
                "stringFileName='" + stringFileName + '\'' +
                ", stringFileType='" + stringFileType + '\'' +
                ", stringFilePath='" + stringFilePath + '\'' +
                ", stringFileTime='" + stringFileTime + '\'' +
                '}';
    }
}
