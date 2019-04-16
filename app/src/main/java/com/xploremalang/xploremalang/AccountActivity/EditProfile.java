package com.xploremalang.xploremalang.AccountActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.xploremalang.xploremalang.Fragment.ProfileFragment;
import com.xploremalang.xploremalang.R;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    Button Save;
    EditText Id_EdProf, Nama_EdProf, Email_EdProf;
    ImageView profil_EdProf;
    FirebaseUser firebaseUser;
    StorageReference storageRef;
    private Uri mImageUri;
    private String myUri;
    private StorageTask uploadTask;
    String nama, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        Save = Save.findViewById(R.id.bSave_editprofil);
        Id_EdProf = Id_EdProf.findViewById(R.id.Id_editprofil);
        Nama_EdProf = Nama_EdProf.findViewById(R.id.nama_editprofil);
        Email_EdProf = Email_EdProf.findViewById(R.id.email_editprofil);
        profil_EdProf = profil_EdProf.findViewById(R.id.editprofile_photo);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("upload");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                          Nama_EdProf.setText(user.getNama());
                          Email_EdProf.setText(user.getEmail());
                          Glide.with(getApplicationContext()).load(user.getImageurl()).into(profil_EdProf);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });


    }

    private void UpdateProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Nama", nama);
        hashMap.put("Email", email);

        reference.updateChildren(hashMap);

    }

    public ContentResolver getContentResolver() {
        return null;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
          final ProgressDialog progressDialog = new ProgressDialog(this);
          progressDialog.setMessage("Uploading...");
          progressDialog.show();

        if (mImageUri != null) {
            final StorageReference filereference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("ImageUrl", myUri);

                        reference.updateChildren(hashMap);
                          progressDialog.dismiss();

                    } else {
                            Toast.makeText(EditProfile.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
              Toast.makeText(this,"Image selected",Toast.LENGTH_SHORT).show();
        }
    }
}