package com.example.admincircketupdate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateScoresActivity extends AppCompatActivity {

    private EditText editTextScoreTeamA;
    private EditText editTextOversTeamA;
    private EditText editTextScoreTeamB;
    private EditText editTextOversTeamB;
    private Button buttonUpdateScoreTeamA;
    private Button buttonUpdateOversTeamA;
    private Button buttonUpdateScoreTeamB;
    private Button buttonUpdateOversTeamB;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_scores);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        editTextScoreTeamA = findViewById(R.id.editTextScoreTeamA);
        editTextOversTeamA = findViewById(R.id.editTextOversTeamA);
        editTextScoreTeamB = findViewById(R.id.editTextScoreTeamB);
        editTextOversTeamB = findViewById(R.id.editTextOversTeamB);

        buttonUpdateScoreTeamA = findViewById(R.id.buttonUpdateScoreTeamA);
        buttonUpdateOversTeamA = findViewById(R.id.buttonUpdateOversTeamA);
        buttonUpdateScoreTeamB = findViewById(R.id.buttonUpdateScoreTeamB);
        buttonUpdateOversTeamB = findViewById(R.id.buttonUpdateOversTeamB);

        buttonUpdateScoreTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScore("teamA", editTextScoreTeamA.getText().toString().trim());
            }
        });

        buttonUpdateOversTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOvers("teamA", editTextOversTeamA.getText().toString().trim());
            }
        });

        buttonUpdateScoreTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScore("teamB", editTextScoreTeamB.getText().toString().trim());
            }
        });

        buttonUpdateOversTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOvers("teamB", editTextOversTeamB.getText().toString().trim());
            }
        });
    }

    private void updateScore(String teamName, String score) {
        databaseReference.child(teamName).child("score").setValue(score);
    }

    private void updateOvers(String teamName, String overs) {
        databaseReference.child(teamName).child("overs").setValue(overs);
    }
}
