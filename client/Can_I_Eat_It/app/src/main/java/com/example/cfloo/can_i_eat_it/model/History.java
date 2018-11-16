package com.example.cfloo.can_i_eat_it.model;

import android.graphics.Bitmap;

import java.util.List;

public class History {
    private List<UploadedImage> uploadedImages;
    private Bitmap newImage;

    public void setNewImage(Bitmap newImage) {
        this.newImage = newImage;
    }

    public Bitmap getNewImage() {
        return newImage;
    }
}
