package com.xploremalang.xploremalang;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xploremalang.xploremalang.AccountActivity.LoginActivity;
import com.xploremalang.xploremalang.Fragment.FeedFragment;
import com.xploremalang.xploremalang.Fragment.HomeFragment;
import com.xploremalang.xploremalang.Fragment.ProfileFragment;
import com.xploremalang.xploremalang.Weather.data.MainActivity_weather;

public class Activity_Utama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ImageView mimageView;
    TextView mtextView;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView mimageView = (ImageView) findViewById(R.id.imageView);
        TextView mtextView = (TextView) findViewById(R.id.textView);

        mimageView = findViewById(R.id.imageView);
        mtextView = findViewById(R.id.textView);

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
                }
            }
        };

//        BOTTOM NAVBAR
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity,
                new HomeFragment()).commit();

    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user !=null){
            if(user.getPhotoUrl()!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(mimageView);
            }
            if(user.getDisplayName()!=null){
                mtextView.setText(user.getDisplayName());
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
            return true;
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
               Toast.makeText(Activity_Utama.this,"Ini Tombol Edit Profle",Toast.LENGTH_SHORT).show();

               break;
           case R.id.nav_feedback:
               Toast.makeText(Activity_Utama.this,"Ini Tombol Lihat Feedback",Toast.LENGTH_SHORT).show();
               break;
           case R.id.nav_diskusi:
               Toast.makeText(Activity_Utama.this,"Ini Tombol Lihat Diskusi",Toast.LENGTH_SHORT).show();
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
}
