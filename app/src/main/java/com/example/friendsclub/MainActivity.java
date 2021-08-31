package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    SignInButton btsignin;
    Button bttrouble;
    TextView tv_app_name;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btsignin=findViewById(R.id.sign_in_button);
        bttrouble=findViewById(R.id.bt_trouble);

        bttrouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Activity2whats_your_email.class));
            }
        });

        //Initialize Sign in options
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("439921796325-soja9h8o2lt0f3g0bmuq1d0274hloee0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        //Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        btsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign in intent
                Intent intent  = googleSignInClient.getSignInIntent();
                //start activity for result
               startActivityForResult(intent,100);
            }
        });
        //initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //redirect to profile activity
            firebaseUser=firebaseAuth.getCurrentUser();
            String uid=firebaseUser.getUid();
            tv_app_name=findViewById(R.id.tv_app_name);
            DocumentReference documentReference=firebaseFirestore.collection("users").document(uid);
            documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.getString("image-uploaded")==null){
                        startActivity(new Intent(MainActivity.this,Activity3name.class));
                    }else {
                        startActivity(new Intent(MainActivity.this,homepage.class));
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check requestcode
        if(requestCode==100){
            Task<GoogleSignInAccount>SignInAccount =GoogleSignIn.getSignedInAccountFromIntent(data);
            if(SignInAccount.isSuccessful()){
                String s="sucessfull";
                DisplayToast(s);
                try {
                    GoogleSignInAccount googleSignInAccount =SignInAccount
                            .getResult(ApiException.class);
                    //   Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.getId());
                    if(googleSignInAccount!=null){
                        //initialize auth credentials
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        , null);
                        //check credentials
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //check condition
                                        if(task.isSuccessful()){
                                            //redirect to profile activity
                                            firebaseUser=firebaseAuth.getCurrentUser();
                                            String uid=firebaseUser.getUid();
                                            tv_app_name=findViewById(R.id.tv_app_name);
                                            DocumentReference documentReference=firebaseFirestore.collection("users").document(uid);
                                            documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if(value.getString("image-uploaded")==null){
                                                        startActivity(new Intent(MainActivity.this,Activity3name.class));
                                                    }else {
                                                        startActivity(new Intent(MainActivity.this,homepage.class));
                                                    }
                                                }
                                            });
                                            DisplayToast("Firebase auth success");
                                        }else{
                                            DisplayToast("auth failed "+task.getException().getMessage());
                                        }
                                    }
                                });
                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                    Log.w("MainActivity", "Google sign in failed", e);
                }
            }
        }
    }
    public void DisplayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
    }

}