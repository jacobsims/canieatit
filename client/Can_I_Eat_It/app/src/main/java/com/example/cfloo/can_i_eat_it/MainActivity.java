package com.example.cfloo.can_i_eat_it;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cfloo.can_i_eat_it.controller.ImagesController;

public class MainActivity extends AppCompatActivity {
    ImagesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ImagesController(this);
        controller.initialize();
        //setContentView(R.layout.title_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        controller.onActivityResult(requestCode, resultCode, data);
    }
}
