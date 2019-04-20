package com.xploremalang.xploremalang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xploremalang.xploremalang.AccountActivity.EditProfile;
import com.xploremalang.xploremalang.AccountActivity.LoginActivity;
import com.xploremalang.xploremalang.Fragment.FeedFragment;
import com.xploremalang.xploremalang.Fragment.HomeFragment;
import com.xploremalang.xploremalang.Fragment.ProfileFragment;
import com.xploremalang.xploremalang.Weather.data.MainActivity_weather;

public class Activity_Utama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;

    ImageView mimageView;
    TextView mtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mimageView = findViewById(R.id.user_image);
        mtextView = findViewById(R.id.user_email);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.navbar_head);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        LOG OUT
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(Activity_Utama.this, LoginActivity.class));
                    finish();
                }
            }
        };

        loadUserInformation(navigationView);

//        BOTTOM NAVBAR
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity,
                new HomeFragment()).commit();

    }

    private void loadUserInformation(NavigationView navigationView) {
        View nav_header = navigationView.getHeaderView(0);
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        if(user !=null){
            if(user.getPhotoUrl()!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(((ImageView)nav_header.findViewById(R.id.user_image)));
            }
            if(user.getDisplayName()!=null){
                ((TextView)nav_header.findViewById(R.id.user_email)).setText(user.getEmail());
            }
        }



    }

//    BOTTOM NAV
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
        BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.home_button:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.feed_button:
                        selectedFragment = new FeedFragment();
                        break;
                    case R.id.profile_button:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity,
                        selectedFragment).commit();
                return true;
            }
        };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Activity_Utama.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       switch (id){
           case R.id.nav_edit_profl:
               Intent profil = new Intent(Activity_Utama.this, EditProfile.class);
               startActivity(profil);
               break;
           case R.id.nav_feedback:
               Toast.makeText(Activity_Utama.this,"Ini Tombol Lihat Feedback",Toast.LENGTH_SHORT).show();
               break;
           case R.id.nav_diskusi:
               Toast.makeText(Activity_Utama.this,"Ini Tombol Lihat Diskusi",Toast.LENGTH_SHORT).show();
               break;
           case R.id.nav_transportasi:
               Intent transportasi = new Intent(Activity_Utama.this, TransportasiActivity.class);
               startActivity(transportasi);
               break;
           case R.id.nav_about:
               Toast.makeText(Activity_Utama.this,"Ini Tombol About Us",Toast.LENGTH_SHORT).show();
               Intent about = new Intent(Activity_Utama.this,about.class);
               startActivity(about);
               break;

           case R.id.nav_send:
               startActivity(new Intent(Activity_Utama.this, MainActivity_weather.class));
               break;
           case R.id.nav_logout:
               Toast.makeText(Activity_Utama.this,"Log Out",Toast.LENGTH_SHORT).show();
               mAuth.signOut();
               break;
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
