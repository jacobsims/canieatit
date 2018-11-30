package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cfloo.can_i_eat_it.R;

import java.util.ArrayList;
import java.util.List;

public class TakePhotoView extends AppCompatActivity implements CanIEatItView {
    private Activity activity;
    private Bitmap photoTaken;
    private Button camera;
    private Button next;
    private ImageView iv;
    private Button previous;

    /**
     * Constructor for TakePhotoView
     */
    public TakePhotoView(Activity activity){
        this.activity = activity;
        activity.setContentView(R.layout.take_picture);

        camera = (Button) activity.findViewById(R.id.cameraBTN);
        next = (Button) activity.findViewById(R.id.tpNextBtn);

        iv = (ImageView) activity.findViewById(R.id.tpImageView);
        previous = (Button) activity.findViewById(R.id.Previous);
    }
    public Button getPrevious() {
        return previous;
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
