package com.example.friendsclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class recycler_view_testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_testing);
        View mainLayout=findViewById(R.id.main_layout_recycler);

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication(),LinearLayoutManager.HORIZONTAL,false));
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(null,mainLayout);

        recyclerView.setAdapter(recyclerViewAdapter);
        SnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

    }
    ArrayList<Item> create_item(){
        ArrayList<Item> items=new ArrayList<>();
        items.add(new Item("blue","blue-disc","#3366ff"));
        items.add(new Item("black","black-disc","#3555ff"));
        items.add(new Item("white","white-disc","#3366ff"));
        items.add(new Item("green","green-disc","#3555ff"));
        return items;
    }

}