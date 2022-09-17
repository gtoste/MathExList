package com.example.mathexlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        Button signInButton = findViewById(R.id.singIn);
        Button signUpButton = findViewById(R.id.singUp);
        emailInput = findViewById(R.id.addressEmailInput);
        passwordInput = findViewById(R.id.passwordInput);

        signInButton.setOnClickListener(this::login);
        signUpButton.setOnClickListener(this::singUp);
    }

    public void login(View view)
    {
        String login = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInputs(login, password))
        {
            mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(MainActivity.this, ListMain.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Intent intent = new Intent(MainActivity.this, ListMain.class);
            startActivity(intent);
        }

    }

    public void singUp(View view)
    {
        String login = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInputs(login, password))
        {
            mAuth.createUserWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            User user = new User(login);
                            FirebaseDatabase.getInstance("https://mathexlist-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful())
                                        {
                                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(MainActivity.this, "Failed! Try again...", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }

                    });
        }
    }

    private boolean validateInputs(String email, String password)
    {
        if (email.isEmpty())
        {
            emailInput.setError("Required");
            emailInput.requestFocus();
            return false;
        }
        if (password.isEmpty())
        {
            passwordInput.setError("Required");
            passwordInput.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailInput.setError("Incorrect email");
            emailInput.requestFocus();
            return false;
        }

        if (password.length() < 6)
        {
            passwordInput.setError("To short");
            passwordInput.requestFocus();
            return false;
        }

        return true;
    }


}