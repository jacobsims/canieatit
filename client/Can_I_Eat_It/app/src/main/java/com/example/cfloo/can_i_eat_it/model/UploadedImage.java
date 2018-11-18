package com.example.cfloo.can_i_eat_it.model;

import com.example.cfloo.can_i_eat_it.controller.SeeFoodApi;

import java.util.Date;

public class UploadedImage {
    private static String[] strings = {
            "Don't eat it!!",
            "Don't eat it!",
            "Don't eat it.",
            "Don't eat it",
            "... Don't eat it.",
            "... Eat it.",
            "Eat it",
            "Eat it.",
            "Eat it!",
            "Eat it!!"
    };

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

    public int getId() {
        return id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public float getResult() {
        return result;
    }

    public String getUrl() {
        return SeeFoodApi.imageUrl(id);
    }
    public String describeResult() {
        int resultStringChosen = (int) (result * 10);
        // Special choice just in case result = 1
        if (resultStringChosen >= 10) resultStringChosen = 9;
        return strings[resultStringChosen];
    }
}
