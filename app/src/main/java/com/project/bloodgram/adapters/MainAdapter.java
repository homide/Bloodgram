package com.project.bloodgram.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.bloodgram.R;
import com.project.bloodgram.model.Posts;
import com.project.bloodgram.ui.activity.mainactivities.ShowDetails;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context context;
    ArrayList<Posts> posts;
    String state, city;

    public MainAdapter(Context context, ArrayList<Posts> posts, String state, String city){
        this.context = context;
        this.posts = posts;
        this.state = state;
        this.city = city;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(posts.get(position).getName());
        holder.patientName.setText("Patient Name: " + posts.get(position).getPatientName());
        holder.patientAge.setText("Patient Age: " + String.valueOf(posts.get(position).getPatientAge()));
        holder.contactInfo.setText("Contact Info: " + String.valueOf(posts.get(position).getPatientphNumber()));
        holder.bloodgrp.setText(posts.get(position).getPatientbloodgrp());

        holder.contactInfo.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + posts.get(position).getPatientphNumber()));
            context.startActivity(intent);
        });

        holder.clickForDetails.setOnClickListener(v->{
            Intent intent = new Intent(context, ShowDetails.class);
            intent.putExtra("postID", posts.get(position).getPostID());
            intent.putExtra("state",state);
            intent.putExtra("city",city);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username, patientName, patientAge, contactInfo, bloodgrp, clickForDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTV);
            patientName = itemView.findViewById(R.id.patientNameTV);
            patientAge = itemView.findViewById(R.id.patientAgeTV);
            contactInfo = itemView.findViewById(R.id.patientNumTV);
            bloodgrp = itemView.findViewById(R.id.patientbgrpTV);
            clickForDetails = itemView.findViewById(R.id.clickForDetails);
        }
    }

}
