package com.project.bloodgram.ui.fragment.navbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.bloodgram.R;
import com.project.bloodgram.adapters.MainAdapter;
import com.project.bloodgram.model.Posts;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //UI
    RecyclerView mainRecyclerView;

    //Variables
    String state, city;
    ArrayList<Posts> arrayList;

    //Adapter
    MainAdapter mainAdapter;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);

        state = getArguments().getString("state");
        city = getArguments().getString("city");

        arrayList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        getPosts();

        return view;
    }

    private void getPosts(){
        databaseReference = firebaseDatabase.getReference().child(state).child(city);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Posts posts = dataSnapshot.getValue(Posts.class);
                    arrayList.add(posts);
                }

                mainAdapter = new MainAdapter(getContext(),arrayList, state,city);
                LinearLayoutManager llm = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,true);
                mainRecyclerView.setLayoutManager(llm);
                mainRecyclerView.setAdapter(mainAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}