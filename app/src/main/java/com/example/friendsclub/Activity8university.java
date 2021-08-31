package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Activity8university extends AppCompatActivity {
    Button continue_button_uni;
    EditText et_university;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String userID=firebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity8university);

        continue_button_uni=findViewById(R.id.continue_bt_uni);
        et_university=findViewById(R.id.et_uni);

        continue_button_uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get users university name
                String university_string=et_university.getText().toString();
                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String,Object>user_university=new HashMap<>();
                user_university.put("university",university_string);
                //upload to firestore cloud
                documentReference.set(user_university, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"UPDATED");
                            startActivity(new Intent(Activity8university.this,Activity7show_me.class));
                        }
                    }
                });


            }
        });

    }
}