package com.xploremalang.xploremalang.Content;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.xploremalang.xploremalang.Fragment.HomeFragment;
import com.xploremalang.xploremalang.MainActivity;
import com.xploremalang.xploremalang.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TambahKonten extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button uploadKonten;
    EditText et_wisata,et_deskripsi,et_latitude,et_longtitude;
    Spinner spinner_konten;
    ImageView image_added;

    String jenis_konten;

    Uri mImageUri;
    String myUri = "";
    StorageReference storageReference;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konten);

        uploadKonten = findViewById(R.id.btn_tambah_konten);
        et_wisata = findViewById(R.id.et_wisata);
        et_deskripsi = findViewById(R.id.et_deskripsi);
        et_latitude = findViewById(R.id.et_latitude);
        et_longtitude = findViewById(R.id.et_longtitude);
        image_added = findViewById(R.id.view_gambar_home);
        spinner_konten = findViewById(R.id.spinner_konten);

        jenis_konten = spinner_konten.getSelectedItem().toString().trim();

        storageReference = FirebaseStorage.getInstance().getReference("Konten");

        uploadKonten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        CropImage.activity()
                .setAspectRatio(1,1)
                .start(TambahKonten.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            mImageUri = result.getUri();

            image_added.setImageURI(mImageUri);
        } else {
            Toast.makeText(this,"Something wrong",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TambahKonten.this, Activity_Utama.class));
            finish();
        }
    }

    private String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (mImageUri!=null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
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
                        myUri = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Konten");

                        String postId = reference.push().getKey();

                        HashMap<String,Object> hashMap = new HashMap<>();

                        hashMap.put("postId",postId);
                        hashMap.put("postImage",myUri);
                        hashMap.put("description",et_deskripsi.getText().toString());
                        hashMap.put("wisata",et_wisata.getText().toString());
                        hashMap.put("latitude",et_latitude.getText().toString());
                        hashMap.put("longtitude",et_longtitude.getText().toString());
                        hashMap.put("jenis_konten",spinner_konten.getSelectedItem().toString());
                        hashMap.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postId).setValue(hashMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(TambahKonten.this,Activity_Utama.class));
                        finish();
                    } else {
                        Toast.makeText(TambahKonten.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TambahKonten.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this,"Image selected",Toast.LENGTH_SHORT).show();
        }
    }

}