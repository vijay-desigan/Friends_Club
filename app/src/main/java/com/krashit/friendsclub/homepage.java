package com.krashit.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class homepage extends AppCompatActivity {
    ArrayAdaptorUserCards arrayAdapter;
    private int i;
    ListView listView;
   public static List<UserCards> rowItemsUser;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    String current_uid=firebaseUser.getUid();
    String card_user_id;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    CollectionReference users_cr=firebaseFirestore.collection("users");
    StorageReference storageReference;
    String current_gender,gender_search;

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
//       rowItemsUser.add(new UserCards("og5VkwAalHQoLstgovgC7UshEip2","sample card ".concat(String.valueOf(i)),"https://media.idownloadblog.com/wp-content/uploads/2020/10/iPhone-12-red-dark-wallpaper.png"));
//        Log.d(TAG," SAMPLE ADDED SAMPLE CARD");
        //getting user's gender
        arrayAdapter = new ArrayAdaptorUserCards(this, R.layout.item, rowItemsUser );

        DocumentReference documentReference=FirebaseFirestore.getInstance().collection("users").document(current_uid);
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        document.getString("gender");
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    }else {
//                        Log.d(TAG, "No such document");
//                    }
//                }else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//        if(current_gender=="MAN"){
//            gender_search="WOMAN";
//        }else if(current_gender=="WOMAN"){
//            gender_search="MAN";
//        }
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
                                arrayAdapter.notifyDataSetChanged();
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
                //right to left swipe for dislike
//                DocumentReference documentReference=firebaseFirestore.collection("users").document(current_uid).collection("connections").document("dislikes");
//                Map<String,Object>dislikes=new HashMap<>();
//                UserCards temp=rowItemsUser.get(0);
//                dislikes.put(temp.getUserId(),true);
//                documentReference.set(dislikes, SetOptions.merge());
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //   makeToast(testing_card_swipe.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //left to right swipe for like
                DocumentReference documentReference=firebaseFirestore.collection("users").document(current_uid).collection("connections").document("likes");
                Map<String,Object>likes=new HashMap<>();
                UserCards temp=rowItemsUser.get(0);
                likes.put(temp.getUserId(),true);
                documentReference.set(likes, SetOptions.merge());
                card_user_id=rowItemsUser.get(0).getUserId();
                ConnectionMatched(rowItemsUser.get(0).getUserId());
                // makeToast(testing_card_swipe.this, "Right!");
                Log.d(TAG,"  LIKED  LIKED   LIKED");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
               // rowItemsUser.add("XML ".concat(String.valueOf(i)));
               // rowItemsUser.add(new UserCards("og5VkwAalHQoLstgovgC7UshEip2"," end of list ".concat(String.valueOf(i)),"https://ih1.redbubble.net/image.888951088.4035/flat,750x1000,075,f.jpg"));
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

    private void ConnectionMatched(String card_userID) {
        final String[] card_id = new String[1];
        final String[] current = new String[1];
        final boolean[] card = new boolean[1];
        DocumentReference documentReference1=firebaseFirestore.collection("users").document(current_uid).collection("connections").document("likes");
        documentReference1.addSnapshotListener(homepage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getBoolean(current_uid)!=null){

                    Log.d(TAG,"    CURRENT USER HAS LIKED");
                    DocumentReference documentReference2=firebaseFirestore.collection("users").document(card_userID).collection("connections").document("likes");
                    documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc=task.getResult();
                                if(doc.getBoolean(card_userID)!=null){
                                    Log.d(TAG,"  CARD USER HAS also LIKED ");
                                    //matching in current user document
                                    DocumentReference documentReference3=firebaseFirestore.collection("users").document(current_uid).collection("connections").document("matches");
                                    Map<String,Object>match=new HashMap<>();
                                    match.put(card_userID,true);
                                    documentReference3.set(match, SetOptions.merge());
                                    //matching in opposite user
                                    DocumentReference documentReference4=firebaseFirestore.collection("users").document(card_userID).collection("connections").document("matches");
                                    Map<String,Object>likes=new HashMap<>();
                                    likes.put(current_uid,true);
                                    documentReference4.set(likes, SetOptions.merge());
                                    Log.d(TAG,"         MATCHED");

                                }
                            }
                        }
                    });
                }
            }
        });
        //card

        //
//        Log.d(TAG,card_id[0]+current[0]);
//        if(card_id[0]==current[0]){
//            DocumentReference documentReference3=firebaseFirestore.collection("users").document(current_uid).collection("connections").document("matches");
//            Map<String,Object>dislikes=new HashMap<>();
//            UserCards temp=rowItemsUser.get(0);
//            dislikes.put(temp.getUserId(),true);
//            documentReference3.set(dislikes, SetOptions.merge());
//            //
//                DocumentReference documentReference4=firebaseFirestore.collection("users").document(card_userID).collection("connections").document("matches");
//                Map<String,Object>likes=new HashMap<>();
//                likes.put(card_userID,true);
//                documentReference4.set(likes, SetOptions.merge());
//
//        }


    }
}