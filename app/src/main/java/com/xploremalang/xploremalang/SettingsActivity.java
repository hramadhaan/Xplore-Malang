package com.xploremalang.xploremalang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Button save;
    private Switch mode, size;
    SharedPreferences Font, Night;

    final String PREF_NIGHT_MODE = "NightMode";
    final String PREF_FONT_SIZE = "BigSize";

    SharedPreferences.Editor editTheme, editFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
            Night = getSharedPreferences(PREF_NIGHT_MODE, Context.MODE_PRIVATE);
            if (Night.getBoolean(PREF_NIGHT_MODE, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mode = findViewById(R.id.mode);
        size = findViewById(R.id.size);
        save = findViewById(R.id.save);

        Font = getSharedPreferences(PREF_FONT_SIZE, Context.MODE_PRIVATE);


        if (Font.getBoolean(PREF_FONT_SIZE, false)) {
            size.setChecked(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfig();
            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            mode.setChecked(true);
        }

        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void saveConfig() {
        if (size.isChecked()) {
            editFont = Font.edit();
            editFont.putBoolean(PREF_FONT_SIZE, true);
            editFont.apply();
        } else {
            editFont = Font.edit();
            editFont.putBoolean(PREF_FONT_SIZE, false);
            editFont.apply();
        }

        if (mode.isChecked()) {
            editTheme = Night.edit();
            editTheme.putBoolean(PREF_NIGHT_MODE, true);
            editTheme.apply();
        }

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}

