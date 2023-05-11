package com.project.bloodgram.ui.fragment.navbar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.bloodgram.R;
import com.project.bloodgram.model.DatabaseHelper;
import com.project.bloodgram.model.User;
import com.project.bloodgram.ui.activity.login.LoginWindow;
import com.project.bloodgram.ui.activity.mainactivities.YourPosts;
import com.project.bloodgram.ui.activity.startup.SelectStateCity;

public class ProfileFragment extends Fragment {

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //Variables
    User userInfo;
    String state, city;

    //UI
    CardView locationCard, postCard, logoutCard;
    TextView username, locationName;

    public ProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        state = bundle.getString("state");
        city = bundle.getString("city");

        locationCard = view.findViewById(R.id.yourLocation);
        postCard = view.findViewById(R.id.yourPosts);
        logoutCard = view.findViewById(R.id.logOut);
        username = view.findViewById(R.id.usernameTV);
        locationName = view.findViewById(R.id.locationName);

        locationName.setText(state + ", " + city);

        locationCard.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), SelectStateCity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });

        postCard.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), YourPosts.class);
            intent.putExtra("state", state);
            intent.putExtra("city", city);
            startActivity(intent);
        });

        logoutCard.setOnClickListener(v->{
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            databaseHelper.deleteLoginDetails();
            Intent intent = new Intent(getContext(), LoginWindow.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        getUserInfo();

        return view;
    }

    private void getUserInfo(){
        String uid = firebaseUser.getUid();
        DatabaseReference databaseRef = firebaseDatabase.getReference().child("Users").child(uid);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo = snapshot.getValue(User.class);
                username.setText(userInfo.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}