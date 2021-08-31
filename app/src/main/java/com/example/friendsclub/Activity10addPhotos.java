package com.example.friendsclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Activity10addPhotos extends AppCompatActivity {
    Button continue_bt_add_photos;
    ImageButton image_bt_1,image_bt_2,image_bt_3,image_bt_4;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String userID=firebaseUser.getUid();
    String image_name;
    String download_url,download_url_1,download_url_2,download_url_3,download_url_4;
    ProgressBar progressBar;
    public Uri imguri_1,imguri_2,imguri_3,imguri_4;
    StorageReference mStorageRef;
    int img_bt_1_pressed=0,img_bt_2_pressed=0,img_bt_3_pressed=0,img_bt_4_pressed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity10add_photos);

        continue_bt_add_photos=findViewById(R.id.continue_bt_add_photos);

        image_bt_1=findViewById(R.id.image_upload_1);
        image_bt_2=findViewById(R.id.image_upload_2);
        image_bt_3=findViewById(R.id.image_upload_3);
        image_bt_4=findViewById(R.id.iamge_upload_4);
        progressBar=findViewById(R.id.progress_bar);


        mStorageRef= FirebaseStorage.getInstance().getReference("Images/"+userID);
        

        image_bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_bt_1_pressed=1;
                imagechooser();
            }
        });
        image_bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_bt_2_pressed=1;
                imagechooser();
            }
        });
        image_bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_bt_3_pressed=1;
                imagechooser();
            }
        });
        image_bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_bt_4_pressed=1;
                imagechooser();
            }
        });

        continue_bt_add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(image_bt_1.getDrawable()!=null && image_bt_2.getDrawable()!=null){
                   image_name="image"+"1";
                    upload(imguri_1);
                    download_url_1=download_url;
                    image_name=image_name+"2";
                    upload(imguri_2);
                    download_url_2=download_url;
                    image_name=image_name+"3";
                    if(image_bt_3.getDrawable()!=null){
                        upload(imguri_3);
                        image_name=image_name+"4";
                        download_url=download_url_3;
                    }
                    if(image_bt_4.getDrawable()!=null){
                        upload(imguri_4);
                        download_url=download_url_4;
                    }
                    DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                    Map<String,Object>user=new HashMap<>();
                    user.put("image-uploaded","true");
                    user.put("image 1",download_url_1);
                    user.put("image 2",download_url_2);
                    if(download_url_3!=null){
                        user.put("image 3",download_url_3);
                    }
                    if(download_url_4!=null){
                        user.put("image 4",download_url_4);
                    }
                    documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Activity10addPhotos.this, homepage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                   Toast.makeText(Activity10addPhotos.this,"please select at least 2 photos",Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    private void upload(Uri uri){
        StorageReference ref=mStorageRef.child(image_name+"."+getExtension(uri));
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading..");
        progressDialog.show();

        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //get a urk of the uploaded content
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, uri.toString());
                        download_url=uri.toString();
                    }
                });
               //Uri downloadedUri=taskSnapshot.getDownloadUri();
                progressDialog.dismiss();
                //download_url=taskSnapshot.getDownloadUri();

                Toast.makeText(Activity10addPhotos.this,"image uploaded",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Activity10addPhotos.this,"Failed "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            progressDialog.setMessage("Uploaded "+(int)progress+"%");
            }
        });

    }
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void imagechooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            if(img_bt_1_pressed==1){
                imguri_1=data.getData();
                image_bt_1.setImageURI(imguri_1);
                img_bt_1_pressed=0;
            }
            if(img_bt_2_pressed==1){
                imguri_2=data.getData();
                image_bt_2.setImageURI(imguri_2);
                img_bt_2_pressed=0;
            }
            if(img_bt_3_pressed==1){
                imguri_3=data.getData();
                image_bt_3.setImageURI(imguri_3);
                img_bt_3_pressed=0;
            }
            if(img_bt_4_pressed==1){
                imguri_4=data.getData();
                image_bt_4.setImageURI(imguri_4);
                img_bt_4_pressed=0;
            }

        }
    }
}