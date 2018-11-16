package com.example.cfloo.can_i_eat_it;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;

import java.util.List;

public class ImageSelectionView extends AppCompatActivity implements CanIEatItView {
    private List<Bitmap> images;
    private CardView cv;
    private Button next;
    private Button previous;

    /**
     * Constructor for ImageSelectionView
     */
    public ImageSelectionView(){
        setContentView(R.layout.image_selection);

        cv = (CardView) findViewById(R.id.isCardView);
        next = (Button) findViewById(R.id.isNextBTN);
        previous = (Button) findViewById(R.id.isPreviousBTN);
    }

    /**
     * Getter for CardView
     * @return cv
     */
    public CardView getCardView(){
        return cv;
    }

    /**
     * Getter for next button
     * @return next
     */
    public Button getNextBTN(){
        return next;
    }

    /**
     * Getter for previous button
     * @return previous
     */
    public Button getPreviousBTN(){
        return previous;
    }

    /**
     * Getter for Images
     * @return images
     */
    public List<Bitmap> getImages(){
        return images;
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
