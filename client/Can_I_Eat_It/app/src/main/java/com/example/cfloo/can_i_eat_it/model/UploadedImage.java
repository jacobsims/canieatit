package com.example.cfloo.can_i_eat_it.model;

import java.util.Date;

public class UploadedImage {
    private int id;
    private Date uploadDate;
    private float result;
    public UploadedImage(int id, long uploadDate, float result) {
        this.id = id;
        this.uploadDate = new Date(uploadDate*1000);
        this.result = result;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + result + ", " + uploadDate + "]";
    }
}
