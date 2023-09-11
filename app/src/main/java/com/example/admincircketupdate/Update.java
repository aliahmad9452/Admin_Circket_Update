package com.example.admincircketupdate;import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {

    private EditText editTextStatus;
    private EditText editTextMatchType;
    private EditText editTextCurrentSituation;
    private Button buttonUpdateData;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        editTextStatus = findViewById(R.id.editTextStatus);
        editTextMatchType = findViewById(R.id.editTextMatchType);
        editTextCurrentSituation = findViewById(R.id.editTextCurrentSituation);
        buttonUpdateData = findViewById(R.id.buttonUpdateData);

        buttonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        // Retrieve and display existing data from Firebase
        retrieveData();
    }

    private void updateData() {
        String status = editTextStatus.getText().toString().trim();
        String matchType = editTextMatchType.getText().toString().trim();
        String currentSituation = editTextCurrentSituation.getText().toString().trim();

        // Update the data in Firebase
        databaseReference.child("status").setValue(status);
        databaseReference.child("matchType").setValue(matchType);
        databaseReference.child("currentSituation").setValue(currentSituation);

        // Clear input fields
        editTextStatus.getText().clear();
        editTextMatchType.getText().clear();
        editTextCurrentSituation.getText().clear();

        Toast.makeText(this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
    }

    private void retrieveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String matchType = dataSnapshot.child("matchType").getValue(String.class);
                    String currentSituation = dataSnapshot.child("currentSituation").getValue(String.class);

                    // Update UI with retrieved data
                    editTextStatus.setText(status);
                    editTextMatchType.setText(matchType);
                    editTextCurrentSituation.setText(currentSituation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Toast.makeText(Update.this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
