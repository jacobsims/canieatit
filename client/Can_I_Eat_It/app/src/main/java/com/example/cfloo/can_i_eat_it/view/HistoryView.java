package com.example.cfloo.can_i_eat_it.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.cfloo.can_i_eat_it.R;
import com.example.cfloo.can_i_eat_it.model.UploadedImage;

import java.util.ArrayList;
import java.util.List;

public class HistoryView extends AppCompatActivity implements CanIEatItView {
    private Activity activity;

    private Button previous;
    private RecyclerView rv;
    private HistoryDataAdapter adapter;
    /**
     * Constructor for HistoryView
     */
    public HistoryView(Activity activity){
        this.activity = activity;

        activity.setContentView(R.layout.history);


        previous = (Button) activity.findViewById(R.id.hPreviousBTN);
        rv = (RecyclerView) activity.findViewById(R.id.hRecyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        List<UploadedImage> data = new ArrayList<>();
        adapter = new HistoryDataAdapter(data);
        rv.setAdapter(adapter);
    }

    public void updateDataWith(List<UploadedImage> data) {
        adapter.setData(data);
    }



    public Button getPreviousBTN(){
        return previous;
    }

    public RecyclerView getRecyclerView(){
        return rv;
    }

    @Override
    public void previousView() {

    }

    @Override
    public void nextView() {

    }

    public static class HistoryDataAdapter extends RecyclerView.Adapter<HistoryDataAdapter.HistoryViewHolder> {
        private List<UploadedImage> data;

        public HistoryDataAdapter(List<UploadedImage> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            HistoryRowView rowView = new HistoryRowView(viewGroup.getContext());
            rowView.setDataFrom(data.get(i));
            return new HistoryViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
            // TODO
            historyViewHolder.rowView.setDataFrom(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class HistoryViewHolder extends RecyclerView.ViewHolder {
            public HistoryRowView rowView;
            public HistoryViewHolder(HistoryRowView rowView) {
                super(rowView);
                this.rowView = rowView;
            }
        }

        public void setData(List<UploadedImage> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }
}
