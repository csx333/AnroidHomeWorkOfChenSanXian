package es.source.code.model;

public class IconOfSCOS {
    private int imageId;
    private String iName;

    public IconOfSCOS() {
    }
    public IconOfSCOS(int imageId, String iName) {
        this.imageId = imageId;
        this.iName = iName;
    }
    public int getImageId() {
        return imageId;
    }

    public String getIName() {
        return iName;
    }
    public void setImageiId(int iId) {
        this.imageId = iId;
    }
    public void setIName(String iName) {
        this.iName = iName;
    }
}