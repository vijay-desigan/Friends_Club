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
import android.widget.CheckBox;
import android.widget.RadioButton;

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

public class Activity9passion extends AppCompatActivity {
    Button continue_button_passion;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String userID=firebaseUser.getUid();
    CheckBox cb_museum,cb_div,cb_climbing,cb_reading,cb_90skids,cb_disney,cb_musician;
    CheckBox cb_new_in_town,cb_picnicking,cb_trivia,cb_fishing,cb_festival,cb_craft_beer;
    CheckBox cb_board_games,cb_outdoors,cb_yoga,cb_politics,cb_vegetarian,cb_karaoke,cb_plant_based;
    CheckBox cb_walking_out,cb_soccer,cb_music,cb_gardening,cb_travel,cb_sneakers,cb_swimming;
    CheckBox cb_grab_a_drink,cb_potterhead,cb_vegan,cb_bhangra,cb_activism,cb_comedy,cb_brunch,cb_movies;
    CheckBox cb_dog_lover,cb_blogging,cb_slam_poetry,cb_tea,cb_cricket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity9passion);

        continue_button_passion=findViewById(R.id.continue_bt_passion);
        cb_museum=findViewById(R.id.museum_button);
        cb_div=findViewById(R.id.div_button);
        cb_climbing =findViewById(R.id.climbing_button);
        cb_reading=findViewById(R.id.reading_button);
        cb_90skids=findViewById(R.id.kid90_button);
        cb_disney=findViewById(R.id.disney_button);
        cb_musician=findViewById(R.id.musician_button);
        cb_new_in_town=findViewById(R.id.new_in_town_button);
        cb_trivia=findViewById(R.id.trivia_button);
        cb_fishing=findViewById(R.id.fishing_button);
        cb_festival=findViewById(R.id.festival_button);
        cb_board_games=findViewById(R.id.board_games_button);
        cb_outdoors=findViewById(R.id.outdoor_button);
        cb_yoga=findViewById(R.id.yoga_button);
        cb_politics=findViewById(R.id.politics_button);
        cb_vegetarian=findViewById(R.id.vegetarian_button);
        cb_karaoke=findViewById(R.id.karaoke_button);
        cb_plant_based=findViewById(R.id.plant_base_button);
        cb_walking_out=findViewById(R.id.walking_out_button);
        cb_soccer=findViewById(R.id.soccer_button);
        cb_music=findViewById(R.id.music_button);
        cb_gardening=findViewById(R.id.gardening_button);
        cb_travel=findViewById(R.id.travel_button);
        cb_sneakers=findViewById(R.id.sneakers_button);
        cb_swimming=findViewById(R.id.swimming_button);
        cb_grab_a_drink=findViewById(R.id.grab_a_drink_button);
        cb_potterhead=findViewById(R.id.potterhead_button);
        cb_vegan=findViewById(R.id.vegan_button);
        cb_bhangra=findViewById(R.id.bhangra_button);
        cb_activism=findViewById(R.id.activism_button);
        cb_comedy=findViewById(R.id.comedy_button);
        cb_brunch=findViewById(R.id.brunch_button);
        cb_movies=findViewById(R.id.movies_button);
        cb_dog_lover=findViewById(R.id.dog_lover_button);
        cb_blogging=findViewById(R.id.blogging_button);
        cb_slam_poetry=findViewById(R.id.slam_poetry_button);
        cb_tea=findViewById(R.id.tea_button);
        cb_cricket=findViewById(R.id.cricket_button);
        cb_picnicking=findViewById(R.id.picnicking_button);
        cb_craft_beer=findViewById(R.id.craft_beer_button);

        continue_button_passion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passion="";
                if(cb_museum.isChecked()){
                    passion=passion+"+ "+"museum";
                }
                if(cb_div.isChecked()){
                    passion=passion+"+ "+"div";
                }
                if(cb_climbing.isChecked()){
                    passion=passion+"+ "+cb_climbing.getText().toString();
                }
                if(cb_reading.isChecked()){
                    passion=passion+"+ "+cb_reading.getText().toString();
                }
                if(cb_90skids.isChecked()){
                    passion=passion+"+ "+cb_90skids.getText().toString();
                }
                if(cb_disney.isChecked()){
                    passion=passion+"+ "+cb_disney.getText().toString();
                }
                if(cb_musician.isChecked()){
                    passion=passion+"+ "+cb_musician.getText().toString();
                }
                if(cb_new_in_town.isChecked()){
                    passion=passion+"+ "+cb_new_in_town.getText().toString();
                }
                if(cb_picnicking.isChecked()){
                    passion=passion+"+ "+cb_picnicking.getText().toString();
                }
                if(cb_trivia.isChecked()){
                    passion=passion+"+ "+cb_trivia.getText().toString();
                }
                if(cb_fishing.isChecked()){
                    passion=passion+"+ "+cb_fishing.getText().toString();
                }
                if(cb_festival.isChecked()){
                    passion=passion+"+ "+cb_festival.getText().toString();
                }
                if(cb_craft_beer.isChecked()){
                    passion=passion+"+ "+cb_craft_beer.getText().toString();
                }
                if(cb_board_games.isChecked()){
                    passion=passion+"+ "+cb_board_games.getText().toString();
                }
                if(cb_outdoors.isChecked()){
                    passion=passion+"+ "+cb_outdoors.getText().toString();
                }
                if(cb_yoga.isChecked()){
                    passion=passion+"+ "+cb_yoga.getText().toString();
                }
                if(cb_politics.isChecked()){
                    passion=passion+"+ "+cb_politics.getText().toString();
                }
                if(cb_vegetarian.isChecked()){
                    passion=passion+"+ "+cb_vegetarian.getText().toString();
                }
                if(cb_karaoke.isChecked()){
                    passion=passion+"+ "+cb_karaoke.getText().toString();
                }
                if(cb_plant_based.isChecked()){
                    passion=passion+"+ "+cb_plant_based.getText().toString();
                }
                if(cb_walking_out.isChecked()){
                    passion=passion+"+ "+cb_walking_out.getText().toString();
                }
                if(cb_soccer.isChecked()){
                    passion=passion+"+ "+cb_soccer.getText().toString();
                }
                if(cb_music.isChecked()){
                    passion=passion+"+ "+cb_music.getText().toString();
                }
                if(cb_gardening.isChecked()){
                    passion=passion+"+ "+cb_gardening.getText().toString();
                }
                if(cb_travel.isChecked()){
                    passion=passion+"+ "+cb_travel.getText().toString();
                }
                if(cb_sneakers.isChecked()){
                    passion=passion+"+ "+cb_sneakers.getText().toString();
                }
                if(cb_swimming.isChecked()){
                    passion=passion+"+ "+cb_swimming.getText().toString();
                }
                if(cb_grab_a_drink.isChecked()){
                    passion=passion+"+ "+cb_grab_a_drink.getText().toString();
                }
                if(cb_potterhead.isChecked()){
                    passion=passion+"+ "+cb_potterhead.getText().toString();
                }
                if(cb_vegan.isChecked()){
                    passion=passion+"+ "+cb_vegan.getText().toString();
                }
                if(cb_bhangra.isChecked()){
                    passion=passion+"+ "+cb_bhangra.getText().toString();
                }
                if(cb_activism.isChecked()){
                    passion=passion+"+ "+cb_activism.getText().toString();
                }
                if(cb_comedy.isChecked()){
                    passion=passion+"+ "+cb_comedy.getText().toString();
                }
                if(cb_brunch.isChecked()){
                    passion=passion+"+ "+cb_brunch.getText().toString();
                }
                if(cb_movies.isChecked()){
                    passion=passion+"+ "+cb_movies.getText().toString();
                }
                if(cb_dog_lover.isChecked()){
                    passion=passion+"+ "+cb_dog_lover.getText().toString();
                }
                if(cb_blogging.isChecked()){
                    passion=passion+"+ "+cb_blogging.getText().toString();
                }
                if(cb_slam_poetry.isChecked()){
                    passion=passion+"+ "+cb_slam_poetry.getText().toString();
                }
                if(cb_tea.isChecked()){
                    passion=passion+"+ "+cb_tea.getText().toString();
                }
                if(cb_cricket.isChecked()){
                    passion=passion+"+ "+cb_cricket.getText().toString();
                }
                DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
                Map<String, Object> user=new HashMap<>();
                user.put("passion",passion);

                documentReference.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"passion uploaded");
                            startActivity(new Intent(Activity9passion.this,Activity10addPhotos.class));
                        }
                    }
                });


            }
        });


    }
}