package com.example.cfloo.can_i_eat_it.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cfloo.can_i_eat_it.R;
import com.example.cfloo.can_i_eat_it.model.UploadedImage;
import com.koushikdutta.ion.Ion;

public class HistoryRowView extends ConstraintLayout {
    private TextView resultView;
    private ImageView imageView;


    public HistoryRowView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.history_row, this);
        resultView = (TextView) findViewById(R.id.hrResultText);
        imageView = (ImageView) findViewById(R.id.hrImageView);
    }

    public void setDataFrom(UploadedImage img) {
        resultView.setText(img.describeResult());
        Ion.with(imageView)
                .placeholder(R.drawable.ic_more_horiz_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .load(img.getUrl());
    }
}
