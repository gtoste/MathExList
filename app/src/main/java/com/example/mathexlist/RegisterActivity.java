package com.example.mathexlist;

import android.app.ProgressDialog;
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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText getEmailInput;
    private EditText getPasswordInput;
    private EditText getPasswordInput2;
    private EditText getUserNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();


        Button signUpButton = findViewById(R.id.singUp);
        getEmailInput = findViewById(R.id.addressEmailInput);
        getPasswordInput = findViewById(R.id.passwordInput);
        getPasswordInput2 = findViewById(R.id.passwordInput_rep);
        getUserNameInput = findViewById(R.id.usernameInput);

        findViewById(R.id.cancel_button).setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        signUpButton.setOnClickListener(this::singUp);
    }

    public void singUp(View view)
    {
        ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, "",
                "Please wait...", true);

        String login = getEmailInput.getText().toString().trim();
        String password = getPasswordInput.getText().toString().trim();
        String password2 = getPasswordInput2.getText().toString().trim();
        String userName = getUserNameInput.getText().toString().trim();

        if (validateInputs(login, password,password2,userName))
        {
            mAuth.createUserWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            //TODO
                            User user = new User(login,userName);

                            FirebaseDatabase.getInstance("https://mathexlist-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful())
                                        {
                                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(RegisterActivity.this, "Failed! Try again...", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }

                    });
        }
    }

    private boolean validateInputs(String email, String password, String password2, String username)
    {
        if (email.isEmpty())
        {
            getUserNameInput.setError("Required");
            getUserNameInput.requestFocus();
            return false;
        }
        if (password.isEmpty())
        {
            getPasswordInput.setError("Required");
            getPasswordInput.requestFocus();
            return false;
        }
        if(password2.isEmpty())
        {
            getPasswordInput2.setError("Required");
            getPasswordInput2.requestFocus();
        }
        if(username.isEmpty())
        {
            getUserNameInput.setError("Required");
            getUserNameInput.requestFocus();
        }

        if(!password.equals(password2))
        {
            getPasswordInput2.setError("Passwords do not match");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            getEmailInput.setError("Incorrect email");
            getEmailInput.requestFocus();
            return false;
        }

        if (password.length() < 6)
        {
            getPasswordInput.setError("To short");
            getPasswordInput.requestFocus();
            return false;
        }

        if(username.length() < 3 || username.length() > 16)
        {
            getUserNameInput.setError("Name must contains between 3 and 16 characters");
            getUserNameInput.requestFocus();
        }
        return true;
    }

}