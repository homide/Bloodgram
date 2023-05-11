package com.project.bloodgram.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.bloodgram.R;
import com.project.bloodgram.model.DatabaseHelper;
import com.project.bloodgram.ui.activity.startup.MainActivity;
import com.project.bloodgram.ui.activity.startup.SelectStateCity;

public class LoginWindow extends AppCompatActivity {

    //Firebase
    FirebaseAuth firebaseAuth;

    //UI
    TextView signUpText, errorText;
    Button loginButton;
    EditText emailET, passwordET;

    //Database Helper
    DatabaseHelper databaseHelper;

    //Variables
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_window);

        signUpText = findViewById(R.id.signUpTextView);
        loginButton = findViewById(R.id.loginButton);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        errorText = findViewById(R.id.errorText);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        if (databaseHelper.checkLoginDetails()){
            checkIDPass(databaseHelper.getEmail(), databaseHelper.getPass());
        }

        loginButton.setOnClickListener(v->{
            email = emailET.getText().toString().trim();
            password = passwordET.getText().toString().trim();
            checkForDetails(email,password);
            checkIDPass(email, password);
        });

        signUpText.setOnClickListener(v->{
            Intent intent = new Intent(LoginWindow.this, SignUpWindow.class);
            startActivity(intent);
        });
    }

    private void checkIDPass(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->{
            if (task.isSuccessful()){
                databaseHelper.deleteLoginDetails();
                addToDatabase(email,password,"true");
                Toast.makeText(LoginWindow.this, "Welcome back!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginWindow.this, SelectStateCity.class);
                startActivity(intent);
            }else{
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Incorrect email or password!");
            }
        });
    }

    private void addToDatabase(String email, String password, String loggedIn){
        databaseHelper.deleteLoginDetails();
        databaseHelper.insertLoginDetails(email, password,loggedIn);
    }

    private boolean checkForDetails(String email, String password){
        if (password.length() < 7){
            Toast.makeText(this,"Password should be greater than 7 characters", Toast.LENGTH_LONG).show();
            return false;
        }else if (!email.contains("@")){
            Toast.makeText(this,"Incorrect Email ID", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}