package com.example.cfloo.can_i_eat_it;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultView extends AppCompatActivity implements CanIEatItView {
    private ImageView iv;
    private TextView result;
    private Button next;

    /**
     * Default Constructor for ResultView
     */
    public ResultView() {
        setContentView(R.layout.result);

        iv = (ImageView) findViewById(R.id.rImageView);
        result = (TextView) findViewById(R.id.rTextView);
        next = (Button) findViewById(R.id.rNextBTN);
    }

    /**
     * Constructor for ResultView that sets the text of the TextView result
     * to the parameter string r
     * @param r
     */
    public ResultView(String r) {
        setContentView(R.layout.result);

        iv = (ImageView) findViewById(R.id.rImageView);
        result = (TextView) findViewById(R.id.rTextView);
        next = (Button) findViewById(R.id.rNextBTN);

        result.setText(r);
    }

    /**
     * Getter for Next
     * @return next
     */
    public Button getNextBTN(){
        return next;
    }

    /**
     * Getter for ImageView
     * @return iv
     */
    public ImageView getImageView(){
        return iv;
    }

    /**
     * Setter for result
     * @param r
     */
    public void setResult(String r){
        result.setText(r);
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
