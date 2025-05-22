package com.example.project1_mhanna22;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // keeps the current theme choice; default = simple cartoon
    private String selectedTheme = "simple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        /* ---------- set up RADIO-GROUP logic ---------- */
        RadioGroup grp = findViewById(R.id.radio_card_theme);
        RadioButton rbSimple  = findViewById(R.id.radio_simple_cartoon);
        RadioButton rbEmojis  = findViewById(R.id.radio_emojis);
        RadioButton rbCards   = findViewById(R.id.radio_playing_cards);

        TextView descSimple   = findViewById(R.id.desc_simple);
        TextView descEmojis   = findViewById(R.id.desc_emojis);
        TextView descPlaying  = findViewById(R.id.desc_playing);

        // tint that matches the spec colour
        ColorStateList magentaTint = ColorStateList.valueOf(getColor(R.color.magenta));

        grp.setOnCheckedChangeListener((group, checkedId) -> {
            // reset all descriptions
            descSimple.setVisibility(View.GONE);
            descEmojis.setVisibility(View.GONE);
            descPlaying.setVisibility(View.GONE);

            if (checkedId == R.id.radio_simple_cartoon) {
                selectedTheme = "simple";
                descSimple.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_emojis) {
                selectedTheme = "emoji";
                descEmojis.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_playing_cards) {
                selectedTheme = "playing";
                descPlaying.setVisibility(View.VISIBLE);
            }

            // colour the chosen radio button & label
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if (child instanceof RadioButton) {
                    RadioButton rb = (RadioButton) child;
                    rb.setTextColor(rb.getId() == checkedId ? getColor(R.color.magenta)
                            : getColor(R.color.black));
                    rb.setButtonTintList(rb.getId() == checkedId ? magentaTint : null);
                }
            }
        });

        // pre-select the first option so UI is consistent
        rbSimple.setChecked(true);
    }

    /* ---------- BOARD BUTTON callback ---------- */
    public void onButtonClick(View view) {
        Intent intent;
        if (view.getId() == R.id.board4x3Button) {
            intent = new Intent(this, match4x3.class);
        } else { // R.id.board5x4Button
            intent = new Intent(this, match5x4.class);
        }
        intent.putExtra("theme", selectedTheme);
        startActivity(intent);
    }
}
