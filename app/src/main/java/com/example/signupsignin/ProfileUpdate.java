package com.example.signupsignin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public
class ProfileUpdate extends AppCompatActivity {
    ImageView photoPic;
    Uri imagePath;
    Button UploadPhoto,LogOut;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        photoPic = findViewById(R.id.photo_User);
        UploadPhoto = findViewById(R.id.upload_pic);
        LogOut = findViewById(R.id.logOut);
        
        UploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                uploadPhoto();
            }
        });
        photoPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
    }

    private
    void uploadPhoto() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("image/"+ UUID.randomUUID().toString())
                .putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
//                            Log.d("UPload", "onComplete: uploaded");
                            task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public
                                void onComplete(@NonNull Task<Uri> task) {
                                    updateTheUserProfile(task.getResult().toString());
                                }
                            });
                        }
                        else {

                        }
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public
                    void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = 100* snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
    }

    private
    void updateTheUserProfile(String uri) {
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("user/"+uid+"/profilePic").setValue(uri);
    }


    @Override
    protected
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!= null && requestCode ==1&& resultCode ==RESULT_OK){
            imagePath = data.getData();

            try {
                Bitmap bi = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                photoPic.setImageBitmap(bi);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}