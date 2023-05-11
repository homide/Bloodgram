package com.project.bloodgram.ui.activity.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.bloodgram.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.HashMap;

public class UserInfoWindow extends AppCompatActivity {

    //UI
    EditText usernameET, phNumET, addressET;
    PowerSpinnerView bloodGroupSpinner;
    Button createProfileButton;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    //Variables
    String bloodgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_window);

        usernameET = findViewById(R.id.usernameET);
        phNumET = findViewById(R.id.phNumET);
        addressET = findViewById(R.id.addressET);
        bloodGroupSpinner = findViewById(R.id.bloodgroupSpinner);
        createProfileButton = findViewById(R.id.createProfileButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        bloodGroupSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                bloodgroup = t1.toString();
            }
        });

        createProfileButton.setOnClickListener(v->{
            checkForDetails(usernameET.getText().toString().trim(),Long.valueOf(phNumET.getText().toString()), addressET.getText().toString().trim(), bloodgroup);
            sendInfo(user.getEmail(),usernameET.getText().toString().trim(),Long.valueOf(phNumET.getText().toString()), addressET.getText().toString().trim(), bloodgroup);
        });

    }

    private void sendInfo(String mail, String name, long phNumber, String address, String bloodgrp){
        databaseReference = firebaseDatabase.getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        String uid = user.getUid();
        hashMap.put("uid",uid);
        hashMap.put("mail", mail);
        hashMap.put("name", name);
        hashMap.put("phNumber", phNumber);
        hashMap.put("address", address);
        hashMap.put("bloodgrp", bloodgrp);

        databaseReference.child("Users").child(uid).setValue(hashMap);

        Intent intent = new Intent(UserInfoWindow.this, LoginWindow.class);
        startActivity(intent);
        Toast.makeText(this,"Profile created. Please login again!", Toast.LENGTH_LONG).show();
        finishAffinity();
    }

    private boolean checkForDetails(String username, long phNumber, String address, String bloodGrp){
        if (username.length() < 3){
            Toast.makeText(this,"Username must have atleast 3 characters", Toast.LENGTH_LONG).show();
            return false;
        }else if (String.valueOf(phNumber).length() != 10){
            Toast.makeText(this,"Incorrect phone number provided", Toast.LENGTH_LONG).show();
            return false;
        }else if (address.length() < 5){
            Toast.makeText(this,"Address must be longer", Toast.LENGTH_LONG).show();
            return false;
        }else if (bloodGrp.isEmpty()){
            Toast.makeText(this,"Please select blood group", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}