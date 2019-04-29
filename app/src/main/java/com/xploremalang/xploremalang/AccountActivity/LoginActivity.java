package com.xploremalang.xploremalang.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.xploremalang.xploremalang.Activity_Utama;
import com.xploremalang.xploremalang.MainActivity;
import com.xploremalang.xploremalang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    EditText login_email;
    EditText login_password;
    Button submit_login;
    private SignInButton mbutton_signin_google;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG="MAIN_ACTIVITY";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView sudah;

    CallbackManager callbackManager;
    LoginButton loginButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sudah = findViewById(R.id.sudah_punya_akun);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        // If using in a fragment
        //loginButton.setActivity(MainActivity.this);

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Retrieving Data...");
                progressDialog.show();
                Profile profile = Profile.getCurrentProfile();

                String accesstoken = loginResult.getAccessToken().getToken();


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();

                        Log.d("response", response.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Hi, " + object.getString("name"), Toast.LENGTH_LONG).show();
                        } catch(JSONException ex) {
                            ex.printStackTrace();
                        }
                        getData(object);


                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("field", "id,name,email,birthday,friend");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {
                // App code

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }


        });
        if (AccessToken.getCurrentAccessToken() != null) {
            //Set User ID
            Toast.makeText(this,AccessToken.getCurrentAccessToken().getUserId(), Toast.LENGTH_SHORT).show();



//            Intent intent = new Intent(this, Activity_Utama.class);
//            startActivity(intent);
//            finish();
        }
        printKeyHash();

        sudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterAccount.class);
                startActivity(i);
            }
        });

        submit_login = findViewById(R.id.button_login);
        submit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginActivity.this, Activity_Utama.class));
                    finish();
                }
            }
        };

        mbutton_signin_google = (SignInButton)findViewById(R.id.button_signin_google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(LoginActivity.this,"You got an Error !",Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mbutton_signin_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        login_email = findViewById(R.id.et_email);
        login_password = findViewById(R.id.et_password);

        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

    }

    public void btnSubmit(){

        String str_email = login_email.getText().toString();
        String str_password = login_password.getText().toString();

        if (TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)){
            Toast.makeText(LoginActivity.this,"All fields are required",Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(str_email, str_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                                progressDialog.setMessage("Please wait...");
                                progressDialog.show();
                                Toast.makeText(LoginActivity.this,"Login Succes..",Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("ERROR",task.getException().toString());
                                Toast.makeText(LoginActivity.this,"Erornya disini"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                mGoogleApiClient.clearDefaultAccountAndReconnect();
                firebaseAuthWithGoogle(account);


            }else {

            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                Log.d(TAG,"signInWithCredentialComplete:"+task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.v(TAG,"SignInWithCredential",task.getException());
                    Toast.makeText(LoginActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData (JSONObject object){
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");

            //Glide.with(this).load(profile_picture.toString()).into(img_avatar);
            Toast.makeText(this, "" + object.getString("email"), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "" +object.getString(""), Toast.LENGTH_SHORT).show();
            //txt_email.setText(object.getString("email"));
            //txt_birthdate.setText(object.getString("birthday"));
            //txt_friend.setText("Friends: " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.xploremalang.xploremalang", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}