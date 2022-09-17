package com.example.mathexlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListMain extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
//        mAuth = FirebaseAuth.getInstance();
//        userId = mAuth.getCurrentUser().getUid();

        userId = "NgdKff0ui1NzPo9hTAyoQX37SBj1";

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mathexlist-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = database.getReference("");
        userReference = database.getReference("Users/" + userId);


        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button checklistButton = findViewById(R.id.checkListButton);
        Button addSectionButton = findViewById(R.id.addSectionButton);

        checklistButton.setOnClickListener((v -> { startActivity(new Intent(ListMain.this, CheckListActivity.class)); }));
        addSectionButton.setOnClickListener((v -> { startActivity(new Intent(ListMain.this, SectionActivity.class)); }));
    }

}