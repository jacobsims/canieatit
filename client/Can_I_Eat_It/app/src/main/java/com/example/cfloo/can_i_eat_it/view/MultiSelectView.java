package com.example.cfloo.can_i_eat_it.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cfloo.can_i_eat_it.R;

import java.util.List;

public class MultiSelectView extends ConstraintLayout {
    private ImageView imageView;
    private List<Bitmap> bitmapsList;
    private int curIndex;
    private TextView textView;
    private Button prevImgBtn;
    private Button nextImgBtn;


    public MultiSelectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.multiselector, this);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView2);
        prevImgBtn = findViewById(R.id.previmg);
        nextImgBtn = findViewById(R.id.nextImg);
        prevImgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curIndex > 0) {
                    curIndex--;
                    update();
                }
            }
        });
        nextImgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curIndex < bitmapsList.size()-1) {
                    curIndex++;
                    update();
                }
            }
        });
    }

    public void updateWithList(List<Bitmap> bitmaps) {
        bitmapsList = bitmaps;
        curIndex = bitmapsList.size() - 1;
        update();
    }

    private void update() {
        imageView.setImageBitmap(bitmapsList.get(curIndex));
        textView.setText("(" + (curIndex + 1) + " of " + bitmapsList.size() + ")");
    }
}
