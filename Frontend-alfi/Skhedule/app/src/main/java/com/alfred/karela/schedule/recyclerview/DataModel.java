package com.alfred.karela.schedule.recyclerview;

/**
 * Created by Bineesh P Babu on 03-12-2016.
 */

public class DataModel {

    String name;
    String version;
    String id_;
    int image;

    public DataModel(String name, String version, String id_, int image) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public String getId() {
        return id_;
    }
}
