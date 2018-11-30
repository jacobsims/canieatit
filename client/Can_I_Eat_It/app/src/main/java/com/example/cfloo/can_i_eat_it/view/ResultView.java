package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cfloo.can_i_eat_it.R;
import com.example.cfloo.can_i_eat_it.model.UploadedImage;

import java.util.List;

public class ResultView extends AppCompatActivity implements CanIEatItView {
    private Activity activity;
    private ImageView iv;
    private TextView result;
    private Button next;
    private Button selectorPrev;
    private Button selectorNext;
    private TextView selectorIndexTextField;
    private List<Bitmap> bitmapList;
    private List<UploadedImage> uploadedImageList;
    private int curIndex;

    /**
     * Default Constructor for ResultView
     */
    public ResultView(Activity activity) {
        activity.setContentView(R.layout.result);

        iv = (ImageView) activity.findViewById(R.id.rImageView);
        result = (TextView) activity.findViewById(R.id.rTextView);
        next = (Button) activity.findViewById(R.id.rNextBTN);

        selectorPrev = (Button) activity.findViewById(R.id.rvPrevImg);
        selectorNext = (Button) activity.findViewById(R.id.rvNextImg);
        selectorIndexTextField = (TextView) activity.findViewById(R.id.rvSelectorIndex);

        selectorPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curIndex > 0) {
                    curIndex--;
                    update();
                }
            }
        });
        selectorNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curIndex < bitmapList.size()-1) {
                    curIndex++;
                    update();
                }
            }
        });
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

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }

    public void update() {
        iv.setImageBitmap(bitmapList.get(curIndex));
        selectorIndexTextField.setText("(" + (curIndex+1) + " of " + bitmapList.size() + ")");
        if (uploadedImageList != null) {
            result.setText(uploadedImageList.get(curIndex).describeResult());
        } else {
            result.setText("Thinking...");
        }
    }

    public void setInitialContents(List<Bitmap> toUpload) {
        bitmapList = toUpload;

    }

    public void setResults(List<UploadedImage> resultList) {
        uploadedImageList = resultList;
    }
}
