package com.example.cfloo.can_i_eat_it.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<UploadedImage> uploadedImages;
    private Bitmap newImage;
    private List<Bitmap> pictureList = new ArrayList<>();

    public void setNewImage(Bitmap newImage) {
        pictureList.add(newImage);
    }

    public List<Bitmap> getNewImage() {
        return pictureList;
    }
}
