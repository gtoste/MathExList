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


        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);
        }


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
                    Intent intent = new Intent(MainActivity.this, Statistics.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void singUp(View view)
    {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
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