package com.xploremalang.xploremalang.AccountActivity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.xploremalang.xploremalang.R;

public class EditProfile extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button Save;
    EditText Id_EdProf, Nama_EdProf, Email_EdProf ;
    ImageView profil_EdProf ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        Save = findViewById(R.id.bSave_editprofil);
        Id_EdProf = findViewById(R.id.Id_editprofil);
        Nama_EdProf = findViewById(R.id.nama_editprofil);
        Email_EdProf = findViewById(R.id.email_editprofil);
        profil_EdProf = findViewById(R.id.editprofile_photo);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(EditProfile.this);
        if (acct !=null) {
            String personId = acct.getId();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            Id_EdProf.setText("ID   :" +personId);
            Nama_EdProf.setText("Nama   :" +personName);
            Email_EdProf.setText("Email :" +personEmail);
            Glide.with(this).load(personPhoto).into(profil_EdProf);

        }
    }
}

