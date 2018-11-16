package com.example.cfloo.can_i_eat_it;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;

public class TakePhotoView extends AppCompatActivity implements CanIEatItView {
    private Bitmap photoTaken;
    private Button camera;
    private Button next;
    private ImageView iv;

    /**
     * Constructor for TakePhotoView
     */
    public TakePhotoView(){
        setContentView(R.layout.take_picture);

        camera = (Button) findViewById(R.id.cameraBTN);
        next = (Button) findViewById(R.id.tpNextBtn);
        iv = (ImageView) findViewById(R.id.tpImageView);
    }

    /**
     * Getter for camera button
     * @return camera
     */
    public Button getCameraBTN(){
        return camera;
    }

    /**
     * Getter for next button
     * @return next
     */
    public Button getNextBTN() {
        return next;
    }

    /**
     * Getter for ImageView (Preview Window)
     * @return iv
     */
    public ImageView getImageView(){
        return iv;
    }


    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
