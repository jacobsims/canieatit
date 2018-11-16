package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cfloo.can_i_eat_it.R;

public class TitleView implements CanIEatItView {
    private Activity activity;
    private Button uploadImage;
    private Button takePicture;
    /*
     * Constructor for TitleView
     */
    public TitleView(Activity activity) {
        //setContentView(R.layout.title_layout);
        this.activity = activity;

        uploadImage = (Button) activity.findViewById(R.id.uploadImage);
        takePicture = (Button) activity.findViewById(R.id.takePicture);

    }

    /**
     * Getter for uploadImage
     * @return
     */
    public Button getUploadImageBTN(){
        return uploadImage;
    }

    /**
     * Getter for takePicture
     * @return
     */
    public Button getTakePictureBTN(){
        return takePicture;
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
