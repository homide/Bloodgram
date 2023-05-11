package com.project.bloodgram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.bloodgram.R;
import com.project.bloodgram.model.Posts;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    Context context;
    ArrayList<Posts> posts;
    String state, city;

    FirebaseDatabase firebaseDatabase;

    public PostsAdapter(Context context, ArrayList<Posts> posts, String state, String city){
        this.context = context;
        this.posts = posts;
        this.state = state;
        this.city = city;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardlayout, parent, false);
        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        holder.username.setText(posts.get(position).getName());
        holder.patientName.setText("Patient Name: " + posts.get(position).getPatientName());
        holder.patientAge.setText("Patient Age: " + String.valueOf(posts.get(position).getPatientAge()));
        holder.contactInfo.setText("Contact Info: " + String.valueOf(posts.get(position).getPatientphNumber()));
        holder.bloodgrp.setText(posts.get(position).getPatientbloodgrp());

        holder.clickForDetails.setVisibility(View.INVISIBLE);

        holder.cardView.setOnLongClickListener(v->{
            firebaseDatabase = FirebaseDatabase.getInstance() ;
            firebaseDatabase.getReference().child(state).child(city).child(posts.get(position).getPostID()).removeValue();
            notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username, patientName, patientAge, contactInfo, bloodgrp, clickForDetails;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            username = itemView.findViewById(R.id.usernameTV);
            patientName = itemView.findViewById(R.id.patientNameTV);
            patientAge = itemView.findViewById(R.id.patientAgeTV);
            contactInfo = itemView.findViewById(R.id.patientNumTV);
            bloodgrp = itemView.findViewById(R.id.patientbgrpTV);
            clickForDetails = itemView.findViewById(R.id.clickForDetails);
        }
    }

}
