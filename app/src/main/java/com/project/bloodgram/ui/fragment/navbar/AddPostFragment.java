package com.project.bloodgram.ui.fragment.navbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.HashMap;

public class AddPostFragment extends Fragment {

    //UI
    EditText patientName, patientAge, contactNumber, patientAddress, patientRelation;
    PowerSpinnerView bloodgrpSpinner;
    CheckBox checkBox;
    Button postButton;

    //Variables
    String pname, paddress, prelation, pbloodgrp, state, city;
    int page;
    long pnumber;
    boolean checked = false;
    User userInfo;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    public AddPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_post, container, false);

        Bundle bundle = getArguments();
        state = bundle.getString("state");
        city = bundle.getString("city");

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        getUserInfo();

        patientName = view.findViewById(R.id.patientNameET);
        patientAge = view.findViewById(R.id.patientAgeET);
        contactNumber = view.findViewById(R.id.patientContactET);
        patientAddress = view.findViewById(R.id.patientAddressET);
        patientRelation = view.findViewById(R.id.patientRelationET);
        bloodgrpSpinner = view.findViewById(R.id.bloodgroupSpinner);
        checkBox = view.findViewById(R.id.checkBox);
        postButton = view.findViewById(R.id.postButton);

        bloodgrpSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                pbloodgrp = t1.toString();
            }
        });

        postButton.setOnClickListener(v->{
            pname = patientName.getText().toString();
            page = Integer.parseInt(patientAge.getText().toString().trim());
            pnumber = Long.parseLong(contactNumber.getText().toString().trim());
            paddress = patientAddress.getText().toString();
            prelation = patientRelation.getText().toString();
            checked = checkBox.isChecked();
            if (checkForDetails(pname,page, pnumber,paddress,prelation,pbloodgrp,checked) && userInfo != null){
                addPost(userInfo, pname,page,pnumber,paddress,prelation,pbloodgrp);
            }
        });


        return view;
    }

    private void addPost(User currentUser, String pname,int page, long pnumber, String paddress, String prelation, String pbloodgrp){
        databaseReference = firebaseDatabase.getReference().child(state).child(city);
        String postID = databaseReference.push().getKey();
        String uid = firebaseUser.getUid();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("patientName", pname);
        hashMap.put("patientbloodgrp", pbloodgrp);
        hashMap.put("patientAddress", paddress);
        hashMap.put("patientAge", page);
        hashMap.put("patientphNumber", pnumber);
        hashMap.put("patientRelation", prelation);
        hashMap.put("postID", postID);
        hashMap.put("name", currentUser.getName());

        databaseReference.child(postID).setValue(hashMap);

        Toast.makeText(getContext(), "Successfully Posted",Toast.LENGTH_LONG).show();
        clearFields();
    }

    private void getUserInfo(){
        String uid = firebaseUser.getUid();
        DatabaseReference databaseRef = firebaseDatabase.getReference().child("Users").child(uid);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearFields(){
        patientName.setText("");
        patientAge.setText("");
        patientRelation.setText("");
        patientAddress.setText("");
        checkBox.setChecked(false);
        bloodgrpSpinner.clearSelectedItem();
        patientAddress.setText("");
    }

    private boolean checkForDetails(String patientName, int patientAge, long contactNum, String patientAddress, String patientRelation, String bloodGroup, boolean check){
        if (patientName == null){
            Toast.makeText(getContext(),"Patient Name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientName.length() < 3){
            Toast.makeText(getContext(),"Patient Name should be greater than 3 char", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientAge == 0){
            Toast.makeText(getContext(),"Patient age can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(contactNum == 0){
            Toast.makeText(getContext(),"Patient contact can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientAddress == null){
            Toast.makeText(getContext(),"Patient address can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientRelation == null){
            Toast.makeText(getContext(),"Patient relation can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(bloodGroup == null){
            Toast.makeText(getContext(),"Patient blood group can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else if(!check){
            Toast.makeText(getContext(),"Please check the checkbox", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientAddress.length() < 5){
            Toast.makeText(getContext(),"Please provide longer address", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientRelation.length() < 3){
            Toast.makeText(getContext(),"Please provide proper relation", Toast.LENGTH_LONG).show();
            return false;
        }else if(patientAge > 120){
            Toast.makeText(getContext(),"Please provide relevant age", Toast.LENGTH_LONG).show();
            return false;
        }else if(String.valueOf(contactNum).length() != 10){
            Toast.makeText(getContext(),"Please provide relevant contact number", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }


    }
}