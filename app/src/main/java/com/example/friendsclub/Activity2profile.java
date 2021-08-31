package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Activity2profile extends AppCompatActivity {
    TextView tvName;
    TextView tvDob;
    TextView tvgender;
    TextView tvorientaion;
    TextView tvshowme;
    ImageView ivImage;
    Button btLogout;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
    String userID=firebaseUser.getUid();
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity2profile);

        ivImage=findViewById(R.id.iv_image);
        tvName=findViewById(R.id.tv_name);
        tvDob=findViewById(R.id.tv_dob);
        tvgender=findViewById(R.id.tv_gender);
        tvorientaion=findViewById(R.id.tv_orientation);
        tvshowme=findViewById(R.id.tv_show_me);
        btLogout=findViewById(R.id.logout_bt);


        if(firebaseUser!=null){
            //set image on image view
            Glide.with(Activity2profile.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);

            tvName.setText(firebaseUser.getDisplayName());
            DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tvDob.setText(value.getString("dob"));
                    tvgender.setText(value.getString("gender"));
                }
            });

        }
        googleSignInClient= GoogleSignIn.getClient( Activity2profile.this, GoogleSignInOptions.DEFAULT_SIGN_IN);


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign out
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //signout
                            firebaseAuth.signOut();
                            //toast
                            Toast.makeText(getApplicationContext(),"Logout success ",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Activity2profile.this,MainActivity.class);
                            finish();

                        }
                    }
                });
            }
        });
    }
}