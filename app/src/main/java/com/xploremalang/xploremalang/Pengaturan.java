package com.xploremalang.xploremalang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class Pengaturan extends AppCompatActivity {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String PREF_FONT_LARGE = "font_large" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean theme = preferences.getBoolean(PREF_DARK_THEME, false);
        boolean font = preferences.getBoolean(PREF_FONT_LARGE, false);
        if (theme && font) {
            setTheme(R.style.AppTheme_Dark_FontLarge);
        } else if (theme) {
            setTheme(R.style.AppTheme_Dark_FontNormal);
        } else if (font) {
            setTheme(R.style.AppTheme_FontLarge);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        if (theme) {
            RadioButton radioButton = findViewById(R.id.settings_radio_dark);
            radioButton.setChecked(true);
        } else {
            RadioButton radioButton = findViewById(R.id.settings_radio_light);
            radioButton.setChecked(true);
        }

        final Switch switchFont = findViewById(R.id.switch_font);
        switchFont.setChecked(font);
        switchFont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREF_FONT_LARGE, isChecked);
                editor.apply();
                recreate();

            }
        });
        Button btnApply = findViewById(R.id.button_accept);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup radioGroup1 = findViewById(R.id.settings_radiogroup_theme);
                switch (radioGroup1.getCheckedRadioButtonId()) {
                    case R.id.settings_radio_light:
                        toggleTheme(0);
                        break;
                    case R.id.settings_radio_dark:
                        toggleTheme(1);
                }
            }
        });
    }

    private void toggleTheme(int mode) {
        switch (mode) {
            case 1:
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREF_DARK_THEME, true);
                editor.apply();
                recreate();
                startActivity(new Intent(Pengaturan.this, Activity_Utama.class));
                finish();
                break;
            case 0:
                editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREF_DARK_THEME, false);
                editor.apply();
                startActivity(new Intent (Pengaturan.this, Activity_Utama.class));
                finish();
                break;
        }

    }
}

