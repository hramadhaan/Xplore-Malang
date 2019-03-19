package com.xploremalang.xploremalang.Content;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xploremalang.xploremalang.Activity_Utama;
import com.xploremalang.xploremalang.Fragment.HomeFragment;
import com.xploremalang.xploremalang.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TambahKonten extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadKonten,pilihGambar;
    private EditText et_wisata,et_deskripsi,et_latitude,et_longtitude;
    private Spinner spinner_konten;
    private ImageView view_image_home;
    private ProgressBar progress_home;
    private Uri mImageUri;
    Toolbar toolbar_tambah_konten;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konten);



        pilihGambar = findViewById(R.id.btn_tambah_foto);
        uploadKonten = findViewById(R.id.btn_tambah_konten);
        et_wisata = findViewById(R.id.et_wisata);
        et_deskripsi = findViewById(R.id.et_deskripsi);
        et_latitude = findViewById(R.id.et_latitude);
        et_longtitude = findViewById(R.id.et_longtitude);
        view_image_home = findViewById(R.id.view_gambar_home);
        progress_home = findViewById(R.id.progress_home);
        spinner_konten = findViewById(R.id.spinner_konten);


        mStorageRef = FirebaseStorage.getInstance().getReference("konten");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("konten");

        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadKonten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(TambahKonten.this,"An upload is still in Progress",Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });


    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            progress_home.setVisibility(View.VISIBLE);
            progress_home.setIndeterminate(true);

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progress_home.setVisibility(View.VISIBLE);
                                    progress_home.setIndeterminate(false);
                                    progress_home.setProgress(0);
                                }
                            },2000);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(TambahKonten.this,"Konten Telah di Tambahkan",Toast.LENGTH_SHORT);
                                    IsiKonten konten = new IsiKonten(et_wisata.getText().toString().trim(),
                                            et_deskripsi.getText().toString().trim(),
                                            et_latitude.getText().toString().trim(),
                                            et_longtitude.getText().toString().trim(),
                                            uri.toString(),
                                            spinner_konten.getSelectedItem().toString().trim());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(konten);
                                    progress_home.setVisibility(View.INVISIBLE);
                                    openImagesActivity();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_home.setVisibility(View.INVISIBLE);
                            Toast.makeText(TambahKonten.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progress_home.setProgress((int)progress);
                        }
                    });
        } else {
            Toast.makeText(this,"You haven't Selected Any file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, Activity_Utama.class);
        startActivity(intent);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).into(view_image_home);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
