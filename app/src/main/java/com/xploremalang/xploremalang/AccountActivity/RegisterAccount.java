package com.xploremalang.xploremalang.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xploremalang.xploremalang.Activity_Utama;
import com.xploremalang.xploremalang.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterAccount extends AppCompatActivity {

    EditText registration_nama, registration_email, registration_password;
    CircleImageView registration_foto;
    Button bregistrasi;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ProgressDialog pd;
    TextView have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        registration_nama = findViewById(R.id.registrasi_nama);
        registration_email = findViewById(R.id.registrasi_email);
        registration_password = findViewById(R.id.registrasi_password);
        registration_foto = findViewById(R.id.registrasi_foto);
        bregistrasi = findViewById(R.id.bregistrasi);

        firebaseAuth = FirebaseAuth.getInstance();

        have_account = findViewById(R.id.have_account);
        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAccount.this, LoginActivity.class));
            }
        });

        bregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterAccount.this);
                pd.setMessage("Please wait..");
                pd.show();

                String str_nama = registration_nama.getText().toString();
                String str_email = registration_email.getText().toString();
                String str_password = registration_password.getText().toString();

                if (TextUtils.isEmpty(str_nama) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(RegisterAccount.this, "All fields are require!", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(RegisterAccount.this, "Password must have 6 character", Toast.LENGTH_SHORT).show();
                } else {
                    bregistrasi(str_nama, str_email, str_password);
                }
            }
        });
    }

    public void bregistrasi(String str_nama, String str_email, String str_password) {
                final ProgressDialog progressDialog = ProgressDialog.show(RegisterAccount.this, "Please wait...", "Processing...", true);
                (firebaseAuth.createUserWithEmailAndPassword(registration_email.getText().toString(), registration_password.getText().toString()))
                        .addOnCompleteListener(RegisterAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    String userid = firebaseUser.getUid();

                                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("id", userid);
                                    hashMap.put("nama", registration_nama.getText().toString());
                                    hashMap.put("email", registration_email.getText().toString());
                                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/xploremalangv2.appspot.com/o/icons8-male-user-48.png?alt=media&token=a0f4ed8b-f2bc-4eff-b592-5510fd9df630");

                                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                pd.dismiss();
                                                Intent intent = new Intent(RegisterAccount.this, Activity_Utama.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                                } else {
                                    pd.dismiss();
                                    Toast.makeText(RegisterAccount.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }

        }

