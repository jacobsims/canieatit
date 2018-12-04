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
import com.example.cfloo.can_i_eat_it.view.GalleryUploadView;
import com.example.cfloo.can_i_eat_it.view.HistoryView;
import com.example.cfloo.can_i_eat_it.view.ResultView;
import com.example.cfloo.can_i_eat_it.view.TakePhotoView;
import com.example.cfloo.can_i_eat_it.view.TitleView;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * This method initializes the program and starts the main screen where
     * you take a picture or add pictures.
     * There are also listeners for the buttons for taking a photo, look at
     * gallery, and upload image.
     */
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
                beginGalleryUpload();
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

    /**
     * This method goes makes a new view history if the gallery button has been selected.
     * This displays the history view and also has the listener to go back to
     * main view if back is selected
     */
    public void goToHistory() {
        HistoryView historyView = new HistoryView(activity);
        currentView = historyView;
        Ion.getDefault(activity).getCache().clear();
        new HistoryRefresh(activity, historyView).execute();
        historyView.getPreviousBTN().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                initialize();
            }
        });
    }

    /**
     * This is called from the beginTakePhoto method. This method opens up the camera and
     * allows for the taking of a picture. This also lets you upload to the server.
     */
    public void goToTakePhoto() {
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
        takePhotoView.getPrevious().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            initialize();
            }
        });
        currentView = takePhotoView;
    }

    /**
     * This is called when you select take a photo. It starts the method
     * that controls the view and starts the camera. It also gets history able to
     * take a new image in
     */
    public void beginTakePhoto() {
        history.getNewImage().clear();
        goToTakePhoto();
        startCameraIntent();
    }

    /**
     * This method controls the view for the gallery view.
     */
    public void goToUploadImage() {
        GalleryUploadView galleryUploadView = new GalleryUploadView(activity);
        galleryUploadView.getGalleryBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGalleryIntent();
            }
        });
        galleryUploadView.getNextBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAndShowResults();
            }
        });
        galleryUploadView.getPrevious().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        currentView = galleryUploadView;
    }

    /**
     * This method gets history ready for a new image. It also starts the gallery and
     * They also call the method that controls the view
     */
    public void beginGalleryUpload() {
        history.getNewImage().clear();
        goToUploadImage();
        startGalleryIntent();
    }

    /**
     *  This lets you pick images from the gallery and opens up the window
     */
    public void startGalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), SELECT_FILE);
    }

    /**
     * This lets you take images from your phones camera and opens the camera
     */
    public void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     *  This uploads the photo to the server and shows the results.
     *  This includes multiple images
     */
    public void uploadAndShowResults() {
        if (history.getNewImage().size() == 0) {
            Toast.makeText(activity, "Please add an image", Toast.LENGTH_LONG).show();
        }
        final ResultView rv = new ResultView(activity);

        rv.getNextBTN().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        rv.setInitialContents(history.getNewImage());
        rv.update();

        new ShowResultsFromUpload(activity, history.getNewImage(), rv).execute();
    }

    /**
     *  Shows multiple results to the user
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            history.setNewImage(imageBitmap);
            if (!(currentView instanceof TakePhotoView)) {
                goToTakePhoto();
            }
            ((TakePhotoView)currentView).getMultiselector().updateWithList(history.getNewImage());
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {

            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
                history.setNewImage(imageBitmap);
                if (!(currentView instanceof GalleryUploadView)) {
                    goToUploadImage();
                }
                ((GalleryUploadView)currentView).getMultiselector().updateWithList(history.getNewImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Shows results to the user from upload
     */
    static class ShowResultsFromUpload extends AsyncTask<Void, Void, Void> {
        Activity activity;
        List<Bitmap> bmps;
        ResultView rv;

        ShowResultsFromUpload(Activity activity, List<Bitmap> bmps, ResultView rv) {
            this.activity = activity;
            this.bmps = bmps;
            this.rv = rv;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final List<UploadedImage> results = new ArrayList<UploadedImage>();
                for (Bitmap bmp : bmps) {
                    results.add(new SeeFoodApi().detectImage(bmp));
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setResults(results);
                        rv.update();
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

    /**
     * This updates the history when new pictures have been taken
     */
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
