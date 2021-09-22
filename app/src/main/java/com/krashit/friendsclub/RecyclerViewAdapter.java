package com.krashit.friendsclub;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<Item>list;
    View mainLayout;

    public RecyclerViewAdapter(ArrayList<Item>list1,View mainlayout1){
        list = new ArrayList<Item>();
        this.list=list1;
        this.mainLayout=mainlayout1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout,parent,false);
        MyViewHolder holder_for_return=new MyViewHolder(view);
        return holder_for_return;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View view=holder.view;
        TextView title=view.findViewById(R.id.text_view_recycler_1);
        TextView desc=view.findViewById(R.id.text_view_recycler_2);
        View image_rec=view.findViewById(R.id.frame_layout_image);
        title.setText(list.get(position).getTitle());
        desc.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return this.list.size() ;
    }

//    @Override
//    public int getItemCount() {
//        return this.list.size();
//    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {

        mainLayout.animate().alpha(0.5f).setDuration(150).withEndAction(()->{
            mainLayout.animate().alpha(1.0f).setDuration(150);
            GradientDrawable gd=new GradientDrawable();
            mainLayout.setBackground(gd);
        });

        super.onViewAttachedToWindow(holder);
    }
}
