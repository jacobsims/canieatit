package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cfloo.can_i_eat_it.R;

public class GalleryUploadView extends AppCompatActivity implements CanIEatItView {
    private Activity activity;
    private Bitmap photoTaken;
    private MultiSelectView multiselector;
    private Button galleryButton;
    private Button next;
    private ImageView iv;
    private Button previous;

    /**
     * Constructor for GalleryUploadView
     */
    public GalleryUploadView(Activity activity){
        this.activity = activity;
        activity.setContentView(R.layout.gallery_upload);

        galleryButton = (Button) activity.findViewById(R.id.galleryBTN);
        next = (Button) activity.findViewById(R.id.tpNextBtn);

        multiselector = (MultiSelectView) activity.findViewById(R.id.galleryMultiselector);
        previous = (Button) activity.findViewById(R.id.Previous);
    }
    public Button getPrevious() {
        return previous;
    }
    /**
     * Getter for camera button
     * @return camera
     */
    public Button getGalleryBTN(){
        return galleryButton;
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

    /**
     * Getter for MultiSelectView
     * @return multiselector
     */
    public MultiSelectView getMultiselector() {
        return multiselector;
    }



    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
