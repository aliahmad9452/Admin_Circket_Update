package com.example.admincircketupdate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetTeamImagesActivity extends AppCompatActivity {

    private static final int TEAM_A_IMAGE_REQUEST_CODE = 1;
    private static final int TEAM_B_IMAGE_REQUEST_CODE = 2;

    private Button buttonSetTeamAImage;
    private Button buttonSetTeamBImage;
    private EditText editTextCountryNameA;
    private EditText editTextCountryNameB;
    private Button buttonUpdateData;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("teams");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_team_images);
        editTextCountryNameA = findViewById(R.id.editTextCountryName);
        editTextCountryNameB = findViewById(R.id.editTextCountryName2);
        buttonSetTeamAImage = findViewById(R.id.buttonSetTeamAImage);
        buttonSetTeamBImage = findViewById(R.id.buttonSetTeamBImage);
        buttonUpdateData = findViewById(R.id.buttonUpdateData);

        buttonSetTeamAImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForResult(TEAM_A_IMAGE_REQUEST_CODE);
            }
        });

        buttonSetTeamBImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForResult(TEAM_B_IMAGE_REQUEST_CODE);
            }
        });

        buttonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void openGalleryForResult(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            String teamName = "teamA";
            String countryName = "teamB";

            if (requestCode == TEAM_A_IMAGE_REQUEST_CODE) {
                teamName = "teamA";
                countryName = editTextCountryNameA.getText().toString().trim();
            } else if (requestCode == TEAM_B_IMAGE_REQUEST_CODE) {
                teamName = "teamB";
                countryName = editTextCountryNameB.getText().toString().trim();
            }

            if (countryName.isEmpty()) {
                Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show();
                return;
            }

            uploadImage(selectedImageUri, teamName, countryName);
        }
    }

    private void uploadImage(Uri imageUri, final String teamName, final String countryName) {
        StorageReference teamImageRef = storageRef.child(teamName + "_image.jpg");

        teamImageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully

                        // Get the download URL of the uploaded image
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Save the image URI and country name in the Firebase Realtime Database
                                saveImageUriToDatabase(downloadUri.toString(), teamName, countryName);

                                // Finish the activity when the image is uploaded
                                finish();
                            }
                        });
                    }
                });
    }

    private void saveImageUriToDatabase(String imageUri, String teamName, String countryName) {
        DatabaseReference teamReference = databaseRef.child(teamName);

        // Save both image URI and country name
        teamReference.child("imageUri").setValue(imageUri);
        teamReference.child("countryName").setValue(countryName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Image URI and country name saved successfully
                        Toast.makeText(SetTeamImagesActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateData() {
        // You can implement data update logic here if needed
    }
}
