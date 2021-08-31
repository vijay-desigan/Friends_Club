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
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class Activity7show_me extends AppCompatActivity {
    Button continue_button_showme;
    RadioButton selectedRadioButton;
    RadioGroup radioGroup;
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
        setContentView(R.layout.activity_activity7show_me);

        continue_button_showme=findViewById(R.id.continue_bt_show_me);
        radioGroup=findViewById(R.id.radio_group_show_me);

        continue_button_showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButton_int=radioGroup.getCheckedRadioButtonId();
                selectedRadioButton=findViewById(selectedRadioButton_int);

                String showme=selectedRadioButton.getText().toString();

                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String,Object> user=new HashMap<>();
                user.put("show me",showme);

                documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"shoe me updated");
                            startActivity(new Intent(Activity7show_me.this,Activity9passion.class)
                            );
                        }
                    }
                });


            }
        });



    }
}