package com.project.bloodgram.ui.activity.mainactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.bloodgram.R;
import com.project.bloodgram.adapters.MainAdapter;
import com.project.bloodgram.adapters.PostsAdapter;
import com.project.bloodgram.model.Posts;

import java.util.ArrayList;

public class YourPosts extends AppCompatActivity {

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //Variables
    String state, city;
    ArrayList<Posts> arrayList;

    //Adapter
    PostsAdapter mainAdapter;

    //UI
    RecyclerView postRecyclerView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_posts);

        state = getIntent().getStringExtra("state");
        city = getIntent().getStringExtra("city");

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        arrayList = new ArrayList<>();

        postRecyclerView = findViewById(R.id.postRecyclerView);
        backButton = findViewById(R.id.backPressButton);

        backButton.setOnClickListener(v->{
            onBackPressed();
        });

        getPosts();
    }

    private void getPosts(){
        databaseReference = firebaseDatabase.getReference().child(state).child(city);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Posts posts = dataSnapshot.getValue(Posts.class);
                    if (posts.getUid().equals(firebaseUser.getUid())){
                        arrayList.add(posts);
                    }
                }

                mainAdapter = new PostsAdapter(YourPosts.this,arrayList, state, city);
                LinearLayoutManager llm = new LinearLayoutManager(YourPosts.this, RecyclerView.VERTICAL,true);
                postRecyclerView.setLayoutManager(llm);
                postRecyclerView.setAdapter(mainAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}