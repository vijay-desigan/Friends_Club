package com.krashit.friendsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Activity6orientation extends AppCompatActivity {
    Button continue_button_orientation;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    CheckBox cb_straight,cb_gay,cb_lesbian,cb_bisexual,cb_asexual,cb_demisexual,cb_pansexual,cb_aromantic;
    String userID=firebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity6orientation);

        continue_button_orientation=findViewById(R.id.continue_bt_orientation);
        cb_straight=findViewById(R.id.straight);
        cb_gay =findViewById(R.id.gay);
        cb_lesbian=findViewById(R.id.lesbian);
        cb_bisexual=findViewById(R.id.bisexual);
        cb_asexual=findViewById(R.id.asexual);
        cb_demisexual=findViewById(R.id.demisexual);
        cb_pansexual=findViewById(R.id.pansexual);
        cb_aromantic=findViewById(R.id.aromantic);

        continue_button_orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orientation="";
                if(cb_straight.isChecked()){
                    orientation=orientation+"+"+"Straight";
                }
                if(cb_gay.isChecked()){
                    orientation=orientation+"+"+"gay";
                }
                if(cb_lesbian.isChecked()){
                    orientation=orientation+"+"+"lesbian";
                }
                if(cb_bisexual.isChecked()){
                    orientation=orientation+"+"+"bisexual";
                }
                if(cb_demisexual.isChecked()){
                    orientation=orientation+"+"+"demisexual";
                }
                if(cb_pansexual.isChecked()){
                    orientation=orientation+"+"+"pansexual";
                }
                if(cb_aromantic.isChecked()){
                    orientation=orientation+"+"+"aromantic";
                }

                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String, Object> user=new HashMap<>();
                user.put("orientaion",orientation);

                documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"orientation uploaded");
                            startActivity(new Intent(Activity6orientation.this,Activity7show_me.class));
                        }
                    }
                });



            }
        });


    }
}