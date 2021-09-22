package com.krashit.friendsclub;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.storage.StorageReference;

import static android.content.ContentValues.TAG;

class UserCards {
    String userId;
    String name;
    String imageUrl;
    StorageReference storageReference;
    Bitmap imageBitmap;

    public UserCards(String userId, String name, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public UserCards(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.imageUrl="https://www.alchinlong.com/wp-content/uploads/2015/09/sample-profile.png";
    }

    public UserCards(String userId, String name, Bitmap imageBitmap) {
        this.userId = userId;
        this.name = name;
        this.imageBitmap = imageBitmap;
    }

    public UserCards(String userId, String name, String imageUrl, StorageReference storageReference) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.storageReference=storageReference;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public StorageReference getStorageRef() {
        return storageReference;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void print(){
        Log.d(TAG,this.userId+" "+this.name+" "+this.imageUrl);
    }
}
