package com.example.cfloo.can_i_eat_it.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cfloo.can_i_eat_it.R;

public class LibrarySelectionView extends AppCompatActivity implements CanIEatItView {
    private String library;
    private Button next;
    private Button previous;
    private Spinner spin;

    /**
     * Constructor for LibrarySelectionView
     */
    public LibrarySelectionView(){
        setContentView(R.layout.library_selection);

        next = (Button) findViewById(R.id.lsNextBTN);
        previous = (Button) findViewById(R.id.lsPreviousBTN);
        spin = (Spinner) findViewById(R.id.lsSpinner);
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
     * Getter for RecyclerView
     * @return rv
     */
    public Spinner getSpinner() {
        return spin;
    }
    /**
     * Getter for Selected Library
     * @return selected library
     */
    public String getSelectedLibrary(){
        return library;
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }
}
