package com.example.baking.models;

import java.io.Serializable;

public class Steps implements Serializable {
    private int id;
    private String  shortDescription ;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Steps(int id,String shortDescription, String description, String videoURL, String thumbnailURL){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;

    }


    public int getId(){ return this.id;}
    public String getShortDescription(){ return this.shortDescription;}
    public String getDescription(){ return this.description; }
    public String getVideoURL(){ return this.videoURL; }
    public String getThumbnailURL(){ return this.thumbnailURL; }
}
