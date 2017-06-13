package com.doapps.petservices.Network.Models;

import java.io.Serializable;

/**
 * Created by NriKe on 12/06/2017.
 */

public class PostResponse implements Serializable{
    private String description;
    private String type;
    private String id;
    private String userId;
    private Photo image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Photo getImage() {
        return image;
    }

    public void setImage(Photo image) {
        this.image = image;
    }


}
