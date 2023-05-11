package com.project.bloodgram.ui.activity.mainactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.bloodgram.R;
import com.project.bloodgram.adapters.MainAdapter;
import com.project.bloodgram.model.Posts;
import com.project.bloodgram.model.User;

public class ShowDetails extends AppCompatActivity {

    //UI
    TextView pnameTV, pageTV, pnumTV, paddressTV, pbloodTV, prelationTV, usernameTV, addressTV, contactTV;
    ImageView backButton;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //Variables
    String state, city, postID;
    User userInfo;
    Posts post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        state = getIntent().getStringExtra("state");
        city = getIntent().getStringExtra("city");
        postID = getIntent().getStringExtra("postID");

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        backButton = findViewById(R.id.backPressButton);
        pnameTV = findViewById(R.id.patientNameTV);
        pnumTV = findViewById(R.id.patientNumTV);
        pageTV = findViewById(R.id.patientAgeTV);
        paddressTV = findViewById(R.id.patientAddressTV);
        pbloodTV = findViewById(R.id.patientbgrpTV);
        prelationTV = findViewById(R.id.patientRelationTV);
        usernameTV = findViewById(R.id.usernameTV);
        addressTV = findViewById(R.id.addressTV);
        contactTV = findViewById(R.id.phNumTV);

        getUserInfo();

        pnumTV.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + post.getPatientphNumber()));
            startActivity(intent);
        });

        contactTV.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + userInfo.getPhNumber()));
            startActivity(intent);
        });

        backButton.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void getPosts(){
        databaseReference = firebaseDatabase.getReference().child(state).child(city).child(postID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                post = snapshot.getValue(Posts.class);
                setFields(post);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserInfo(){
        String uid = firebaseUser.getUid();
        DatabaseReference databaseRef = firebaseDatabase.getReference().child("Users").child(uid);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo = snapshot.getValue(User.class);
                getPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFields(Posts post){
        pnameTV.setText("Patient Name: " + post.getPatientName());
        pageTV.setText("Patient Age: " + post.getPatientAge());
        pnumTV.setText("Contact Info: " + post.getPatientphNumber());
        paddressTV.setText("Patient Address: " + post.getPatientAddress());
        pbloodTV.setText("Required Blood Group: " + post.getPatientbloodgrp());
        prelationTV.setText("Patient Relation: " + post.getPatientRelation());
        usernameTV.setText("Name: " + post.getName());
        addressTV.setText("Address: " + userInfo.getAddress());
        contactTV.setText("Contact Number: " + userInfo.getPhNumber());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}