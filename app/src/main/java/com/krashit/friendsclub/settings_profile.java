package com.krashit.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class settings_profile extends AppCompatActivity {
    boolean image_choosed=false;
    ImageButton change_dp;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String userID=firebaseUser.getUid();
    String image_name;
    EditText about_et,phone_et,job_et,company_et,university_et,city_et;
    CircularImageView new_image;
    Button save_changes;
    Uri image_uri;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        ActionBar actionBar = getActionBar();
    //    actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings_profile);



        about_et=findViewById(R.id.about_et);
        phone_et=findViewById(R.id.phone_no_et);
        job_et=findViewById(R.id.job_et);
        company_et=findViewById(R.id.company_et);
        university_et=findViewById(R.id.university_et);
        city_et=findViewById(R.id.city_et);
        save_changes=findViewById(R.id.save_changes_bt);

        new_image=findViewById(R.id.image_update);

        DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    if(doc.exists()){
                        if(doc.getString("about")!=null && doc.getString("about")!=""){
                            about_et.setText(doc.getString("about"));
                        }
                        if(doc.getString("city")!=null && doc.getString("city")!=""){
                            city_et.setText(doc.getString("city"));
                        }
                        if(doc.getString("company")!=null && doc.getString("company")!=""){
                            company_et.setText(doc.getString("company"));
                        }
                        if(doc.getString("contact")!=null && doc.getString("contact")!=""){
                            phone_et.setText(doc.getString("contact"));
                        }
                        if(doc.getString("job")!=null && doc.getString("job")!=""){
                            job_et.setText(doc.getString("job"));
                        }
                        if(doc.getString("university")!=null && doc.getString("university")!=""){
                            university_et.setText(doc.getString("university  "));
                        }else{
                            university_et.setText("NA");
                        }
                    }else{
                        Log.d(TAG, "                    No such document");
                    }
                }
            }
        });
        
        Glide.with(settings_profile.this)
                .load(firebaseUser.getPhotoUrl())
                .into(new_image);

        new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagechooser();
            }
        });
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "                               BUTTON PRESSED  ");
                mStorageRef= FirebaseStorage.getInstance().getReference("Images/"+userID);

                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String,Object> user=new HashMap<>();
                user.put("about",about_et.getText().toString());
                user.put("contact",phone_et.getText().toString());
                user.put("job",job_et.getText().toString());
                user.put("company",company_et.getText().toString());
                user.put("university",university_et.getText().toString());
                user.put("city",city_et.getText().toString());
                documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"updated",Toast.LENGTH_SHORT);
                            Intent intent = new Intent(settings_profile.this, profile_navigationActivity2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT);
                        }
                    }
                });
                if(new_image.getDrawable()!=null && image_choosed==true){
                    upload(image_uri);
                }
            }
        });
    }
    private void upload(Uri uri){
        StorageReference ref=mStorageRef.child(image_name+"."+getExtension(uri));
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading..");
        progressDialog.show();
        Log.d(TAG, "                                INSIDE UPLOAD IMAGE");
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //get a url of the uploaded content
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d(TAG, "                                DOWNLOAD URL "+uri.toString());
                    }
                });
                progressDialog.dismiss();
                Toast.makeText(settings_profile.this,"image updated",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(settings_profile.this,"Failed "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int)progress+"%");
            }
        });

    }
    private void imagechooser() {
        Log.d(TAG, "                                CHOOSING IMAGE ");
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Log.d(TAG, "                              GOT IMAGE ");
            image_uri=data.getData();
            image_choosed=true;
            new_image.setImageURI(image_uri);
        }
    }
}