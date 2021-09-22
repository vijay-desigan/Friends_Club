package com.krashit.friendsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class testing_card_swipe extends AppCompatActivity {
   // private ArrayList<String> al;
    private UserCards cards_array[];
    private ArrayAdaptorUserCards arrayAdapter;
    private int i;
    ListView listView;
    List<UserCards>rowItems;


    @BindView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_card_swipe);
        ButterKnife.bind(this);

        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        rowItems = new ArrayList<UserCards>();
//        rowItems.add(new UserCards("userId 1","ion op","https://cdn1.dotesports.com/wp-content/uploads/2020/11/10000747/Ion-Operator-Concept3-scaled.jpg"));
//        rowItems.add(new UserCards("userId 2","ion sherrif","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXBHAkpjjxnYmCE7tag9UrwsWsGwEJ5EJjzJca7-aMdYzO4VARoeKQs5t76D9weQ9b1ck&usqp=CAU"));
//        rowItems.add(new UserCards("userId 3","ion phantom","https://i1.wp.com/invisioncommunity.co.uk/wp-content/uploads/2020/11/Ion-Phantom-Concept1.jpg?w=582&h=298&ssl=1"));
//        rowItems.add(new UserCards("userId 4","reaver vandal","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTILSl3bQI6XNyXFSzjIDz3tgbKkwFCUUwKwCWI6VywXznxp-e19v3LuiillMI5W3Tm-VM&usqp=CAU"));
//        rowItems.add(new UserCards("userId 5","prime vandal","https://staticg.sportskeeda.com/editor/2020/06/960d1-15910915218367-800.jpg"));
        //choose your favorite adapter
       arrayAdapter = new ArrayAdaptorUserCards(this, R.layout.item, rowItems );

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
             //   makeToast(testing_card_swipe.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
               // makeToast(testing_card_swipe.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
               // rowItems.add("XML ".concat(String.valueOf(i)));
//                rowItems.add(new UserCards("userId 2","prime".concat(String.valueOf(i)),"https://staticg.sportskeeda.com/editor/2020/06/960d1-15910915218367-800.jpg"));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
            //    makeToast(testing_card_swipe.this, "Clicked!");
            }
        });
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
}