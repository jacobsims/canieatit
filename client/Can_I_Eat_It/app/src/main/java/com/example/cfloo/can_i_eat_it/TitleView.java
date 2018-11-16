package com.example.cfloo.can_i_eat_it;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class TitleView extends AppCompatActivity implements CanIEatItView {
    private Button uploadImage;
    private Button takePicture;
    /*
     * Constructor for TitleView
     */
    public TitleView() {
        setContentView(R.layout.title_layout);

        uploadImage = (Button) findViewById(R.id.uploadImage);
        takePicture = (Button) findViewById(R.id.takePicture);
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
