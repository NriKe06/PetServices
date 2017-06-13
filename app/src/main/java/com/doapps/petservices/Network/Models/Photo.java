package com.doapps.petservices.Network.Models;

/**
 * Created by NriKe on 13/06/2017.
 */

public class Photo{
    private String relativePath;
    private String url;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}