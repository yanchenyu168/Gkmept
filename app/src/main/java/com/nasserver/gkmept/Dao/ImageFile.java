package com.nasserver.gkmept.Dao;

public class ImageFile {

    private String stringImageName;
    private String stringImagePath;
    private String stringImageLon;
    private String stringImageLat;
    private String stringImageTime;
    private int anIntTitle;
    private int anIntImage;//是否为图片

    //region get/set/tostring

    public String getStringImageName() {
        return stringImageName;
    }

    public void setStringImageName(String stringImageName) {
        this.stringImageName = stringImageName;
    }

    public String getStringImagePath() {
        return stringImagePath;
    }

    public void setStringImagePath(String stringImagePath) {
        this.stringImagePath = stringImagePath;
    }

    public String getStringImageLon() {
        return stringImageLon;
    }

    public void setStringImageLon(String stringImageLon) {
        this.stringImageLon = stringImageLon;
    }

    public String getStringImageLat() {
        return stringImageLat;
    }

    public void setStringImageLat(String stringImageLat) {
        this.stringImageLat = stringImageLat;
    }

    public String getStringImageTime() {
        return stringImageTime;
    }

    public void setStringImageTime(String stringImageTime) {
        this.stringImageTime = stringImageTime;
    }

    public int getAnIntTitle() {
        return anIntTitle;
    }

    public void setAnIntTitle(int anIntTitle) {
        this.anIntTitle = anIntTitle;
    }

    public int getAnIntImage() {
        return anIntImage;
    }

    public void setAnIntImage(int anIntImage) {
        this.anIntImage = anIntImage;
    }

    @Override
    public String toString() {
        return "ImageFile{" +
                "stringImageName='" + stringImageName + '\'' +
                ", stringImagePath='" + stringImagePath + '\'' +
                ", stringImageLon='" + stringImageLon + '\'' +
                ", stringImageLat='" + stringImageLat + '\'' +
                ", stringImageTime='" + stringImageTime + '\'' +
                ", anIntTitle=" + anIntTitle +
                ", anIntImage=" + anIntImage +
                '}';
    }

    //endregion

}
