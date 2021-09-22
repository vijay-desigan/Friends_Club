package com.krashit.friendsclub.Matches;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krashit.friendsclub.R;

class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId;

    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId=(TextView) itemView.findViewById(R.id.match_id);
    }

    @Override
    public void onClick(View v) {

    }
}
