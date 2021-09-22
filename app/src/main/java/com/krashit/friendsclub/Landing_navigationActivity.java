package com.krashit.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

public class Landing_navigationActivity extends AppCompatActivity {
    Bitmap next_bitmap;
    String next_name;
    ArrayList<String> user_id_array_list=new ArrayList<String>();
    Iterator iterator_uid;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    CollectionReference users_cr=firebaseFirestore.collection("users");
    StorageReference storageReference;
    TextView tv_name;
    TextView tv_about;
    ImageView iv_display_pic;
    String next_uid;
    String[] next_user_id={"bPBZHDYmqLMbqeLaXBaq9G8gvKK2","w9MEyFyE69TFNOP8zF8eSE7hv4A3","12fxDm1FFTT7RfFLxrHl5YXe25U2","xB3fno2ROAbRTpG0z85NP73CqAb2"};
    int user_count=0;

    private UserCards cards_array[];
    private ArrayAdaptorUserCards arrayAdapter;
    private int i;
    ListView listView;
    List<UserCards>rowItems;
    @BindView(R.id.frame)
    SwipeFlingAdapterView flingContainer;

//    @GlideModule
//    public final class MyAppGlideModule extends AppGlideModule {
//        @Override
//        public void registerComponents(Context context, Glide glide, Registry registry) {
//            // Register FirebaseImageLoader to handle StorageReference
//            registry.append(StorageReference.class, InputStream.class,
//                    new FirebaseImageLoader.Factory());
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing_navigation);

//        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home_page);

        tv_name=findViewById(R.id.tv_match_person);
        tv_about=findViewById(R.id.match_person_about);
        iv_display_pic=findViewById(R.id.iv_match_person);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        rowItems = new ArrayList<UserCards>();
        rowItems.add(new UserCards("userId 1","ion op","https://cdn1.dotesports.com/wp-content/uploads/2020/11/10000747/Ion-Operator-Concept3-scaled.jpg"));
        rowItems.add(new UserCards("userId 2","ion sherrif","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXBHAkpjjxnYmCE7tag9UrwsWsGwEJ5EJjzJca7-aMdYzO4VARoeKQs5t76D9weQ9b1ck&usqp=CAU"));
        rowItems.add(new UserCards("userId 3","ion phantom","https://i1.wp.com/invisioncommunity.co.uk/wp-content/uploads/2020/11/Ion-Phantom-Concept1.jpg?w=582&h=298&ssl=1"));
        rowItems.add(new UserCards("userId 4","reaver vandal","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTILSl3bQI6XNyXFSzjIDz3tgbKkwFCUUwKwCWI6VywXznxp-e19v3LuiillMI5W3Tm-VM&usqp=CAU"));
        rowItems.add(new UserCards("userId 5","prime vandal","https://staticg.sportskeeda.com/editor/2020/06/960d1-15910915218367-800.jpg"));
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
                rowItems.add(new UserCards("userId 2","prime".concat(String.valueOf(i)),"https://staticg.sportskeeda.com/editor/2020/06/960d1-15910915218367-800.jpg"));
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

        //query
        Query query=users_cr.whereEqualTo("gender","WOMAN");
        //executing query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String uid_string=document.getId();
                        Log.d(TAG,uid_string);
                        tv_about.setText(uid_string);
                        user_id_array_list.add(uid_string);
                        //add to list of slide model

                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        System.out.println(user_id_array_list);
//        Log.d(TAG,user_id_array_list.get(0));

        iterator_uid=user_id_array_list.iterator();
//       next_uid=user_id_array_list.get(0);



        next_bitmap=get_image();
        next_name=get_name();

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.like:
//                        startActivity(new Intent(getApplicationContext(),likes_navigationActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.live:
//                        startActivity(new Intent(getApplicationContext(),live_navigationActivity2.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.chat:
//                        startActivity(new Intent(getApplicationContext(),chat_navigationActivity2.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(),profile_navigationActivity2.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.home_page:
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    private class SwipeListner implements View.OnTouchListener {
        GestureDetector gestureDetector;

        //constructor
        SwipeListner(View view){
            int threshold=1;
            int velocity_threshold=1;
            //simple gesture listener
            GestureDetector.SimpleOnGestureListener listener=new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff=e2.getX()-e1.getX();
                    float yDiff=e2.getY()-e1.getY();
                    try {
                        if(Math.abs(xDiff)>Math.abs(yDiff)){
                                //x greater than y
                            if(Math.abs(xDiff)>threshold && Math.abs(velocityX)>velocity_threshold){
                                if(xDiff>0){
                                    startActivity(new Intent(Landing_navigationActivity.this,recycler_view_testing.class));
//
                                    change_next_profile_details();
                                      iv_display_pic.setImageBitmap(next_bitmap);
//                                    tv_name.setText(next_name);
                                    //swiped right
                                  //  change_next_profile_details();
//                                    if(user_count<next_user_id.length){
//                                        change_next_profile_details();
//                                        iv_display_pic.setImageBitmap(next_bitmap);
//                                        tv_name.setText(next_name);
//                                        next_bitmap=get_image();
//                                        next_name=get_name();
//                                        user_count+=1;
//                                    }else{
//                                        user_count=0;
//                                        iv_display_pic.setImageBitmap(next_bitmap);
//                                        tv_name.setText(next_name);
//                                        next_bitmap=get_image();
//                                        next_name=get_name();
//                                    }

                                    tv_about.setText("swiped right "+next_uid);
                                    if(iterator_uid.hasNext()==true){
                                        next_uid=iterator_uid.next().toString();
                                    }

                                }else{
                                    change_next_profile_details();
//                                    iv_display_pic.setImageBitmap(next_bitmap);
//                                    tv_name.setText(next_name);
                                    //swiped left
                                 //   change_next_profile_details();
//                                    if(user_count<next_user_id.length){
//                                        change_next_profile_details();
//                                        iv_display_pic.setImageBitmap(next_bitmap);
//                                        tv_name.setText(next_name);
//                                        next_bitmap=get_image();
//                                        next_name=get_name();
//                                        user_count+=1;
//                                    }else{
//                                        user_count=0;
//                                        iv_display_pic.setImageBitmap(next_bitmap);
//                                        tv_name.setText(next_name);
//                                        next_bitmap=get_image();
//                                        next_name=get_name();
//                                    }
                                    tv_about.setText("swiped left "+next_uid);
                                    if(iterator_uid.hasNext()==true){
                                        next_uid=iterator_uid.next().toString();
                                    }

                                }
                            }return true;
                        }else{
                            if(Math.abs(yDiff)>threshold && Math.abs(velocityY)>velocity_threshold){
                                if(yDiff>0){
                                    //swiped down
                                   // tv_name.setText("swiped down");
                                }else{
                                    //swiped up
                                  //  tv_name.setText("swiped up");
                                }
                            }return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                return false;
                }
            };
            //initialize gesture detector
            gestureDetector =new GestureDetector(listener);
            //set listener on view
            view.setOnTouchListener(this);
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //return gesture event
            return gestureDetector.onTouchEvent(event);
        }
    }

    private String get_name() {
        final String[] next_name = new String[1];
        DocumentReference documentReference = firebaseFirestore.collection("users").document(next_user_id[user_count]);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
              next_name[0] =value.getString("name");
            }
        });
        return next_name[0];
    }

    private Bitmap get_image() {
        final Bitmap[] image_bitmap = new Bitmap[1];
        storageReference = FirebaseStorage.getInstance().getReference("Images/" + next_user_id[user_count] + "/image1.jpg");
        //gs://friends-club-7bef4.appspot.com/Images/bPBZHDYmqLMbqeLaXBaq9G8gvKK2/image1.jpg
        //gs://friends-club-7bef4.appspot.com/Images/bPBZHDYmqLMbqeLaXBaq9G8gvKK2/image1.jpg
        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //  iv_display_pic.setImageBitmap(bitmap);
                image_bitmap[0] = bitmap;
            }
        });
        return image_bitmap[0];
    }

    private void change_next_profile_details() {
        if (user_count < next_user_id.length) {
            storageReference = FirebaseStorage.getInstance().getReference("Images/" + next_user_id[user_count] + "/image1.jpg");
//            gs://friends-club-7bef4.appspot.com/Images/bPBZHDYmqLMbqeLaXBaq9G8gvKK2/image1.jpg
//            gs://friends-club-7bef4.appspot.com/Images/bPBZHDYmqLMbqeLaXBaq9G8gvKK2/image1.jpg
//           storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//               @Override
//               public void onSuccess(byte[] bytes) {
//                   Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                   iv_display_pic.setImageBitmap(bitmap);
//               }
//           });
            DocumentReference documentReference = firebaseFirestore.collection("users").document(next_user_id[user_count]);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tv_name.setText(value.getString("name"));
                    String image_url = value.getString("image 1");
                    Glide.with(Landing_navigationActivity.this).load(image_url).into(iv_display_pic);

                }
            });
            user_count += 1;
        }else{
            user_count=0;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //super.onBackPressed();
    }
}