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
import android.widget.DatePicker;

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

public class Activity4date extends AppCompatActivity {
    Button continue_button_date;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String userID;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity4date);

        continue_button_date=findViewById(R.id.continue_bt_date);
        datePicker=findViewById(R.id.date_picker1);

        userID=firebaseUser.getUid();

        continue_button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the d.o.b.
                String day=String.valueOf(datePicker.getDayOfMonth());
                String month=String.valueOf(datePicker.getMonth());
                String year=String.valueOf(datePicker.getYear());
                //string concatenation into single string
                String dob=day+"-"+month+"-"+year;
                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String,Object> user=new HashMap<>();
                user.put("dob",dob);
                //upload the data in the firestore
                documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //if upload is successful start new activity
                            Log.d(TAG,"UPDATED");
                            startActivity(new Intent(Activity4date.this,Activity5gender.class)
                            );
                        }
                    }
                });


            }
        });



    }

}