package com.project.bloodgram.ui.activity.startup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.project.bloodgram.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

public class SelectStateCity extends AppCompatActivity {

    //UI
    PowerSpinnerView stateSpinner, citySpinner;
    Button proceedButton;

    //Variables
    String state, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_state_city);

        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        proceedButton = findViewById(R.id.proceedButton);

        stateSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                System.out.println(i1);
                changeCitySpinner(i1);
                state = t1.toString();
                citySpinner.clearSelectedItem();
            }
        });

        citySpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                city = t1.toString();
            }
        });

        proceedButton.setOnClickListener(v->{
            if (state != null && city != null){
                Intent intent = new Intent(SelectStateCity.this, MainActivity.class);
                intent.putExtra("state",state);
                intent.putExtra("city",city);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Please select state and city", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeCitySpinner(int id){
        List<String> items = new ArrayList<>();
        if (id == 0){
            items.add("Noida");
            citySpinner.setItems(items);
        }else if(id == 1){
            items.add("Delhi");
            citySpinner.setItems(items);
        }else if (id == 2){
            items.add("Mumbai");
            citySpinner.setItems(items);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}