package com.example.admincircketupdate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button_set_team_image, button_update_scores,button_update_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_set_team_image = findViewById(R.id.button_set_team_image);
        button_update_scores = findViewById(R.id.button_update_scores);
        button_update_Status = findViewById(R.id.button_update_Status);

        button_update_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Update.class);
                startActivity(intent);
            }
        });

        button_set_team_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetTeamImagesActivity.class);
                startActivity(intent);
            }
        });

        button_update_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateScoresActivity.class);
                startActivity(intent);
            }
        });
    }
}
