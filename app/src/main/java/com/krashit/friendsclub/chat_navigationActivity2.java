package com.krashit.friendsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.krashit.friendsclub.Matches.MatchesAdapter;
import com.krashit.friendsclub.Matches.MatchesObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class chat_navigationActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;
    ArrayList<MatchesObject> resultMatches=new ArrayList<MatchesObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_navigation2);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_chats);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager=new LinearLayoutManager(chat_navigationActivity2.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter=new MatchesAdapter(getDataSetMatches(),chat_navigationActivity2.this);
        mRecyclerView.setAdapter(mMatchesAdapter);
        Log.d(TAG,"        RECYCLER MADE ");
        Log.d(TAG,"        RECYCLER MADE ");
        Log.d(TAG,"        RECYCLER MADE ");

        MatchesObject obj=new MatchesObject("new one");
        resultMatches.add(obj);
        resultMatches.add(obj);
        resultMatches.add(obj);
        mMatchesAdapter.notifyDataSetChanged();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.like:
                        startActivity(new Intent(getApplicationContext(),likes_navigationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.live:
                        startActivity(new Intent(getApplicationContext(),live_navigationActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(),homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile_navigationActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chat:
                        return true;
                }

                return false;
            }
        });
    }

    private List<MatchesObject> getDataSetMatches() {
        return resultMatches;
    }
}