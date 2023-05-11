package com.project.bloodgram.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.bloodgram.R;
import com.project.bloodgram.model.DatabaseHelper;

import org.w3c.dom.Text;

public class SignUpWindow extends AppCompatActivity {

    //UI
    TextView loginText, errorText;
    EditText emailET, passET, passETRW;
    Button signUpButton;

    //Variables
    String emailText, passwordText, passConfirmText;
    boolean checkMailExist = false;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    //Database Helper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_window);

        loginText = findViewById(R.id.loginTextView);
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passwordET);
        passETRW = findViewById(R.id.passwordETRW);
        signUpButton = findViewById(R.id.signUpButton);
        errorText = findViewById(R.id.errorText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        signUpButton.setOnClickListener(v->{
            emailText = emailET.getText().toString().trim();
            passwordText = passET.getText().toString().trim();
            passConfirmText = passETRW.getText().toString().trim();
            if (checkForDetails(emailText,passwordText,passConfirmText)){
                checkEmailExistsOrNot(emailText);
            }
        });

        loginText.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private boolean checkEmailExistsOrNot(String mail){
        firebaseAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(task -> {
            if (task.getResult().getSignInMethods().size() == 0){
                firebaseAuth.createUserWithEmailAndPassword(emailText,passwordText);
                addToDatabase(emailText, passwordText, "true");
            }else {
                Toast.makeText(SignUpWindow.this, "User with this email already exists!", Toast.LENGTH_LONG).show();
            }
        });

        return checkMailExist;
    }

    private void addToDatabase(String email, String password, String loggedIn){
        databaseHelper.deleteLoginDetails();
        databaseHelper.insertLoginDetails(email, password,loggedIn);
        Intent intent = new Intent(SignUpWindow.this, UserInfoWindow.class);
        startActivity(intent);
    }

    private boolean checkForDetails(String email, String password, String passwordRW){
        if (password.length() < 7){
            Toast.makeText(this,"Password should be greater than 7 characters", Toast.LENGTH_LONG).show();
            return false;
        }else if (!email.contains("@")){
            Toast.makeText(this,"Incorrect Email ID format", Toast.LENGTH_LONG).show();
            return false;
        }else if (!password.equals(passwordRW)){
            Toast.makeText(this,"Password doesn't match. Please check again!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}