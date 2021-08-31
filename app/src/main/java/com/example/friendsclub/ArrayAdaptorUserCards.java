package com.example.friendsclub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static android.content.ContentValues.TAG;

class ArrayAdaptorUserCards extends ArrayAdapter<UserCards> {
    Context contex;
    StorageReference storageReference;

    public ArrayAdaptorUserCards(@NonNull Context context, int resourceId, List<UserCards>items) {
        super(context, resourceId,items);
    }
    public View getView(int position, View convertView, ViewGroup parent ){
        UserCards card_item=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView name=(TextView)convertView.findViewById(R.id.helloText);
        ImageView image=(ImageView)convertView.findViewById(R.id.image_on_card);
        Bitmap bitmap=card_item.getImageBitmap();
        name.setText(card_item.getName());

        if(bitmap==null){
            Log.d(TAG, "         BIT MAP NULL "+card_item.getName());
        }
        if(bitmap!=null){
            image.setImageBitmap(card_item.getImageBitmap());
            Log.d(TAG, "       BIT MAP UPLOADED "+card_item.getName());
        }
        if(card_item.imageUrl!=null){
            Glide.with(getContext()).load(card_item.getImageUrl()).into(image);
            Log.d(TAG, "        GLIDE IMAGE LOADED "+card_item.getName());
        }
//        if(card_item.getStorageRef()!=null){
//            storageReference=card_item.getStorageRef();
//            storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                    image.setImageBitmap(bitmap);
//                }
//            });
//        }else{
//            Glide.with(getContext()).load(card_item.getImageUrl()).into(image);
//        }
//        image.setImageResource(R.mipmap.ic_launcher);
        return convertView;

    }


}
