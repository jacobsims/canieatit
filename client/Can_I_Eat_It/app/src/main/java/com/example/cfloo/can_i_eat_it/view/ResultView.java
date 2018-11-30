package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cfloo.can_i_eat_it.R;

public class ResultView extends AppCompatActivity implements CanIEatItView {
    private Activity activity;
    private ImageView iv;
    private TextView result;
    private Button next;

    /**
     * Default Constructor for ResultView
     */
    public ResultView(Activity activity) {
        activity.setContentView(R.layout.result);

        iv = (ImageView) activity.findViewById(R.id.rImageView);
        result = (TextView) activity.findViewById(R.id.rTextView);
        next = (Button) activity.findViewById(R.id.rNextBTN);

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

    public TextView getResultView() {
        return result;
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
