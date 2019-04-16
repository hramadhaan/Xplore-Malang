package com.xploremalang.xploremalang;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      //  getSupportActionBar().setSubtitle("");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings){

            Intent mIntent = new Intent(this, Setting.class);

            startActivity(mIntent);

            Toast.makeText(this, "Buka Halaman Settings", Toast.LENGTH_SHORT).show();


            return  true;
        }


        return super.onOptionsItemSelected(item);
    }
}
