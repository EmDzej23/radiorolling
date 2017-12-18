package com.nevreme.rolling.model;

import java.io.Serializable;

public enum ImageType implements Serializable{
	POST(1),
    AVATAR(2);
     
    int imageType;
     
    private ImageType(int imageType){
        this.imageType = imageType;
    }
     
    public int getImageType(){
        return imageType;
    }
}
