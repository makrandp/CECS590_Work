package edu.csulb.android.cameraappassignment;

/**
 * Created by Mak on 2/27/2017.
 */

public class ImageInfo {
    private int id;
    private String caption;
    private String path;

    public ImageInfo(){}

    public ImageInfo(String caption, String path) {
        super();
        this.caption = caption;
        this.path = path;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return caption;
    }

}
