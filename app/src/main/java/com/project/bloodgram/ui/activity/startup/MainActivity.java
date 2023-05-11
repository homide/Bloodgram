package com.project.bloodgram.ui.activity.startup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.bloodgram.R;
import com.project.bloodgram.ui.fragment.navbar.AddPostFragment;
import com.project.bloodgram.ui.fragment.navbar.HomeFragment;
import com.project.bloodgram.ui.fragment.navbar.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    //UI Components
    private BottomNavigationView bottomNavBar;

    //Variables
    int id;
    String state, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = getIntent().getStringExtra("state");
        city = getIntent().getStringExtra("city");

        bottomNavBar = findViewById(R.id.bottom_navBar);
        bottomNavBar.setSelectedItemId(R.id.bottom_nav_home);

        bottomNavBar.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFrag;
            if(item.getItemId() == R.id.bottom_nav_home){
                if (!(id == R.id.bottom_nav_home)){
                    selectedFrag = new HomeFragment();
                    id = R.id.bottom_nav_home;
                    loadFragment(selectedFrag);
                }
            }else if(item.getItemId() == R.id.bottom_nav_add_post){
                if (!(id == R.id.bottom_nav_add_post)){
                    selectedFrag = new AddPostFragment();
                    id = R.id.bottom_nav_add_post;
                    loadFragment(selectedFrag);
                }
            }else{
                if (!(id == R.id.bottom_nav_profile)){
                    selectedFrag = new ProfileFragment();
                    id = R.id.bottom_nav_profile;
                    loadFragment(selectedFrag);
                }
            }

            return true;
        });

        loadFragment(new HomeFragment());
        id = R.id.bottom_nav_home;
    }

    private void loadFragment(Fragment selectedFrag) {
        if(selectedFrag!=null){
            Bundle bundle = new Bundle();
            bundle.putString("state", state);
            bundle.putString("city", city);
            selectedFrag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_fragment,selectedFrag).commit();
        }
        else {
            Toast.makeText(this,"Fragment Error",Toast.LENGTH_SHORT).show();
        }
    }
}