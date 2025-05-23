package com.example.project1_mhanna22;

import android.content.Intent;
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

        RadioGroup grp       = findViewById(R.id.radio_card_theme);
        RadioButton rbSimple = findViewById(R.id.radio_simple_cartoon);
        RadioButton rbEmoji  = findViewById(R.id.radio_emojis);
        RadioButton rbCards  = findViewById(R.id.radio_playing_cards);

        TextView descSimple  = findViewById(R.id.desc_simple);
        TextView descEmoji   = findViewById(R.id.desc_emojis);
        TextView descCards   = findViewById(R.id.desc_playing);

        grp.setOnCheckedChangeListener((group, checkedId) -> {
            descSimple.setVisibility(View.GONE);
            descEmoji.setVisibility(View.GONE);
            descCards.setVisibility(View.GONE);

            if (checkedId == R.id.radio_simple_cartoon) {
                selectedTheme = "simple";  descSimple.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_emojis) {
                selectedTheme = "emoji";   descEmoji.setVisibility(View.VISIBLE);
            } else {
                selectedTheme = "playing"; descCards.setVisibility(View.VISIBLE);
            }

            /* magenta text for chosen option */
            for (int i = 0; i < group.getChildCount(); i++) {
                View c = group.getChildAt(i);
                if (c instanceof RadioButton) {
                    ((RadioButton) c).setTextColor(
                            c.getId() == checkedId
                                    ? getColor(R.color.magenta)
                                    : getColor(R.color.black));
                }
            }
        });

        rbSimple.setChecked(true);   // default
    }

    public void onButtonClick(View v) {
        Intent intent = (v.getId() == R.id.board4x3Button)
                ? new Intent(this, match4x3.class)
                : new Intent(this, match5x4.class);
        intent.putExtra("theme", selectedTheme);
        startActivity(intent);
    }
}
