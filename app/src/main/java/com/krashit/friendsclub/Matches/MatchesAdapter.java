package com.krashit.friendsclub.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krashit.friendsclub.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

//    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rov = new MatchesViewHolder((layoutView));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        holder.mMatchId.setText(matchesList.get(position).getUserID());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
