package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class homepage extends AppCompatActivity {
    private ArrayAdaptorUserCards arrayAdapter;
    private int i;
    ListView listView;
   public static List<UserCards> rowItemsUser;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    CollectionReference users_cr=firebaseFirestore.collection("users");
    StorageReference storageReference;
    Bitmap bitmap;
 //   UserCards new_card;

    @BindView(R.id.frame)
    SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        rowItemsUser = new ArrayList<UserCards>();
        rowItemsUser.add(new UserCards("userId 2","sample card ".concat(String.valueOf(i)),"https://media.idownloadblog.com/wp-content/uploads/2020/10/iPhone-12-red-dark-wallpaper.png"));
        Log.d(TAG,"ADDED SAMPLE CARD");
        //add items to the list from firebase
        Query query=users_cr.whereEqualTo("gender","MAN");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG,"             DOCS " + document.getId() + " => " + document.getData());
                        //to send other details
                        //to get image
                        storageReference=FirebaseStorage.getInstance().getReference("Images/"+document.getId()+"/image1.jpg");
                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                UserCards new_card=new UserCards(document.getId(),document.getString("name"),bitmap);
                                rowItemsUser.add(new_card);
                                Log.d(TAG,"         CARD ADDED IN LIST WITH BITMAP ");
                            }
                        });

                    }
                } else {
                    Log.d(TAG, "ERROR GETTING DOCUMENTS  ", task.getException());
                    Toast.makeText(getApplicationContext(),"Error getting documents ",Toast.LENGTH_LONG).show();
                }
            }
        });

        arrayAdapter = new ArrayAdaptorUserCards(this, R.layout.item, rowItemsUser );
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("                 LIST", "REMOVED");
                rowItemsUser.remove(0);
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
               // rowItemsUser.add("XML ".concat(String.valueOf(i)));
                rowItemsUser.add(new UserCards("userId 2"," end of list ".concat(String.valueOf(i)),"https://ih1.redbubble.net/image.888951088.4035/flat,750x1000,075,f.jpg"));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "modified LAST CARD ADDED");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.like:
                        startActivity(new Intent(getApplicationContext(),likes_navigationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_page:
                        return true;
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(),chat_navigationActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile_navigationActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.live:
                        startActivity(new Intent(getApplicationContext(),live_navigationActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }
}