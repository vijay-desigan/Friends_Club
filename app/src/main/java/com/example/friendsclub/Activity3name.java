package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class Activity3name extends AppCompatActivity {
    Button continue_button_name;
    EditText et_name;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    String userID=firebaseUser.getUid();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity3name);

        continue_button_name=findViewById(R.id.continue_bt_name);
        et_name=findViewById(R.id.et_name);

        continue_button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the user's name
                String name=et_name.getText().toString();
                //update the users name
                UserProfileChangeRequest profile_updates=new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();
                firebaseUser.updateProfile(profile_updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //if update is successful start next activity
                            DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                            Map<String, Object> user=new HashMap<>();
                            user.put("name",name);
                            user.put("likes","");
                            user.put("dislike","");
                            user.put("UID",userID);
                            documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        DocumentReference doc =firebaseFirestore.collection("users").document("User IDs");
                                        Map<String,Object>user_id=new HashMap<>();
                                        user_id.put(name,userID);
                                        doc.set(user_id,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(Activity3name.this,Activity4date.class));
                                                finish();
                                            }
                                        })/*.addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    startActivity(new Intent(Activity3name.this,Activity4date.class));
                                                    finish();
                                                }
                                            }
                                        })*/;
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });




    }
}