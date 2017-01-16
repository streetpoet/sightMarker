package com.spstudio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sp on 14/01/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVersion {
    private int major;
    private int minor;

    private String comment;

    public ProductVersion(){

    }

    public ProductVersion(int major, int minor){
        this.major = major;
        this.minor = minor;
    }
    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "productVersion: major " + major + ", minor " + minor;
    }
}
