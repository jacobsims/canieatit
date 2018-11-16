package com.example.cfloo.can_i_eat_it.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.cfloo.can_i_eat_it.R;
import com.example.cfloo.can_i_eat_it.model.History;
import com.example.cfloo.can_i_eat_it.model.UploadedImage;
import com.example.cfloo.can_i_eat_it.view.CanIEatItView;
import com.example.cfloo.can_i_eat_it.view.ResultView;
import com.example.cfloo.can_i_eat_it.view.TakePhotoView;
import com.example.cfloo.can_i_eat_it.view.TitleView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ImagesController {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_FILE = 2;
    private CanIEatItView currentView;
    private Activity activity;
    private History history;

    public ImagesController(Activity activity) {
        this.activity = activity;
    }

    public void initialize() {
        activity.setContentView(R.layout.title_layout);
        TitleView tv = new TitleView(activity);
        history = new History();
        tv.getTakePictureBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTakePhoto();
            }
        });
        tv.getUploadImageBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGalleryIntent();
            }
        });
        currentView = tv;
    }

    public void beginTakePhoto() {
        TakePhotoView takePhotoView = new TakePhotoView(activity);
        takePhotoView.getCameraBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraIntent();
            }
        });
        takePhotoView.getNextBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAndShowResults();
            }
        });
        currentView = takePhotoView;
        startCameraIntent();
    }

    public void startGalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), SELECT_FILE);
    }

    public void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void uploadAndShowResults() {
        final ResultView rv = new ResultView(activity);

        rv.getImageView().setImageBitmap(history.getNewImage());

        new ShowResultsFromUpload(history.getNewImage(), rv).execute();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            history.setNewImage(imageBitmap);
            ((TakePhotoView)currentView).getImageView().setImageBitmap(imageBitmap);
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {

            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
                history.setNewImage(imageBitmap);
                uploadAndShowResults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ShowResultsFromUpload extends AsyncTask<Void, Void, Void> {
        Bitmap bmp;
        ResultView rv;

        ShowResultsFromUpload(Bitmap bmp, ResultView rv) {
            this.bmp = bmp;
            this.rv = rv;
        }

        @Override
        protected Void doInBackground(Void... params) {
            UploadedImage img = new SeeFoodApi().detectImage(bmp);
            rv.getResultView().setText(img.toString());
            return null;
        }
    }
}
