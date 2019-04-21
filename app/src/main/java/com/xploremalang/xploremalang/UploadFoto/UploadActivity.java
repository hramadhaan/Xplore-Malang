package com.xploremalang.xploremalang.UploadFoto;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xploremalang.xploremalang.Activity_Utama;
import com.xploremalang.xploremalang.Content.TambahKonten;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.Weather.data.Sys;

import java.util.HashMap;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView imageUpload;
    EditText description;
    Button btnGambar, btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageUpload = findViewById(R.id.imageUpload);
        description = findViewById(R.id.description);
        btnGambar = findViewById(R.id.btn_file);
        btnUpload = findViewById(R.id.btn_upload);

        storageReference = FirebaseStorage.getInstance().getReference("Feeds");

        btnUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        CropImage.activity()
                .setAspectRatio(1,1)
                .start(UploadActivity.this);

    }


    private void uploadFile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (imageUri!=null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask =filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Feeds");
                        String postId = reference.push().getKey();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("postId",postId);
                        hashMap.put("imageFeed",myUrl);
                        hashMap.put("deskripsi",description.getText().toString());
                        hashMap.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        hashMap.put("gambarPublisher",FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

                        reference.child(postId).setValue(hashMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(UploadActivity.this,Activity_Utama.class));
                        finish();
                    } else {
                        Toast.makeText(UploadActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(UploadActivity.this,"Image Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageUpload.setImageURI(imageUri);
        } else {
            Toast.makeText(this,"Something Wrong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UploadActivity.this,Activity_Utama.class));
            finish();
        }

    }
}



