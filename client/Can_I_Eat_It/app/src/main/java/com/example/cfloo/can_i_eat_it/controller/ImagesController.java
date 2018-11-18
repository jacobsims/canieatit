package com.example.cfloo.can_i_eat_it.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.cfloo.can_i_eat_it.R;
import com.example.cfloo.can_i_eat_it.model.History;
import com.example.cfloo.can_i_eat_it.model.UploadedImage;
import com.example.cfloo.can_i_eat_it.view.CanIEatItView;
import com.example.cfloo.can_i_eat_it.view.HistoryView;
import com.example.cfloo.can_i_eat_it.view.ResultView;
import com.example.cfloo.can_i_eat_it.view.TakePhotoView;
import com.example.cfloo.can_i_eat_it.view.TitleView;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.List;

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
        tv.getGoToHistoryBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHistory();
            }
        });
        currentView = tv;
    }

    public void goToHistory() {
        HistoryView historyView = new HistoryView(activity);
        currentView = historyView;
        Ion.getDefault(activity).getCache().clear();
        new HistoryRefresh(activity, historyView).execute();
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

        new ShowResultsFromUpload(activity, history.getNewImage(), rv).execute();
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
        Activity activity;
        Bitmap bmp;
        ResultView rv;

        ShowResultsFromUpload(Activity activity, Bitmap bmp, ResultView rv) {
            this.activity = activity;
            this.bmp = bmp;
            this.rv = rv;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final UploadedImage img = new SeeFoodApi().detectImage(bmp);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.getResultView().setText(img.describeResult());
                    }
                });
            } catch (IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,
                                "Failed to determine whether there is food in the image",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

    static class HistoryRefresh extends AsyncTask<Void, Void, Void> {
        Activity activity;
        HistoryView historyView;

        HistoryRefresh(Activity activity, HistoryView hv) {
            this.activity = activity;
            historyView = hv;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final List<UploadedImage> data = new SeeFoodApi().getHistoryList();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyView.updateDataWith(data);
                    }
                });
            } catch (IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,
                                "Failed to load history",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }
}
