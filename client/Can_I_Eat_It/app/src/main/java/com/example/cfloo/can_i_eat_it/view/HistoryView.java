package com.example.cfloo.can_i_eat_it.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;

import com.example.cfloo.can_i_eat_it.R;

public class HistoryView extends AppCompatActivity implements CanIEatItView {
    private Button next;
    private Button previous;
    private CardView cv;
    /**
     * Constructor for HistoryView
     */
    public HistoryView(){
        setContentView(R.layout.history);

        next = (Button) findViewById(R.id.hNextBTN);
        previous = (Button) findViewById(R.id.hPreviousBTN);
        cv = (CardView) findViewById(R.id.hCardView);
    }

    public Button getNextBTN(){
        return next;
    }

    public Button getPreviousBTN(){
        return previous;
    }

    public CardView getCardView(){
        return cv;
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
