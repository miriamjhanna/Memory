package com.example.project1_mhanna22;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class match5x4 extends AppCompatActivity {

    private int[] cards;      // 20 ImageButton view-IDs
    private int   cardBack;
    private String theme;

    private Deck deck;
    private Card firstCard = null;
    private ImageButton firstButton = null, processingFirst, processingSecond;
    private boolean processing = false, waiting = false;

    private int numMatch, numMiss;
    private TextView matchText, missText, instructionsText;

    /* ===================================================== */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match5x4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        theme = getIntent().getStringExtra("theme");
        if (theme == null) theme = "simple";

        assignCards();
        paintSlotsThemeBackground();
        setInitialCardBacks();

        matchText        = findViewById(R.id.num_match5x4);
        missText         = findViewById(R.id.num_miss5x4);
        instructionsText = findViewById(R.id.instructions5x4);

        findViewById(R.id.main).setOnClickListener(v -> { if (waiting) flipBackTwo(); });
    }

    /* ---------------------- BOARD BUILD ---------------------- */
    private void assignCards() {
        cards = new int[] {
                R.id.card5x4_00,R.id.card5x4_01,R.id.card5x4_02,R.id.card5x4_03,
                R.id.card5x4_10,R.id.card5x4_11,R.id.card5x4_12,R.id.card5x4_13,
                R.id.card5x4_20,R.id.card5x4_21,R.id.card5x4_22,R.id.card5x4_23,
                R.id.card5x4_30,R.id.card5x4_31,R.id.card5x4_32,R.id.card5x4_33,
                R.id.card5x4_40,R.id.card5x4_41,R.id.card5x4_42,R.id.card5x4_43
        };

        switch (theme) {
            case "playing":
                cardBack = R.drawable.cardback_playing;
                deck     = new Deck(cards, getRandomPlayingCards(10));
                break;

            case "emoji":
                cardBack = R.drawable.cs477p1_cardback;
                deck     = new Deck(cards, getRandomEmojiBitmaps(10));
                break;

            default:
                cardBack = R.drawable.cs477p1_cardback;
                deck     = new Deck(cards, getSimpleDrawables());
        }
    }

    private void paintSlotsThemeBackground() {
        int bgColor = resolveThemeColor(android.R.attr.colorBackground);
        for (int id : cards) ((ImageButton) findViewById(id)).setBackgroundColor(bgColor);
    }

    private int resolveThemeColor(int attr) {
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(attr, tv, true);
        return tv.data;
    }

    private void setInitialCardBacks() {
        for (int id : cards) {
            ImageButton b = findViewById(id);
            b.setImageResource(cardBack);
            b.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    /* -------------------- FACE PROVIDERS -------------------- */
    private int[] getSimpleDrawables() {
        return new int[] {
                R.drawable.academy_brush_color_paint_pallete_icon,
                R.drawable.animal_marine_prawn_seafood_shrimp_icon,
                R.drawable.app_brand_brands_logo_logos_icon,
                R.drawable.cat_icon,
                R.drawable.brain_divide_inkcontober_sains_icon,
                R.drawable.floor_laughing_on_rolling_the_icon,
                R.drawable.christmas_tree_xmas_icon,
                R.drawable.eye_glasses_office_vision_icon,
                R.drawable.food_pizza_icon,
                R.drawable.pencil_icon
        };
    }

    private int[] getRandomPlayingCards(int n) {
        int[] all = {
                /* spades */
                R.drawable.ace_spades,   R.drawable.two_spades,  R.drawable.three_spades,
                R.drawable.four_spades,  R.drawable.five_spades, R.drawable.six_spades,
                R.drawable.seven_spades, R.drawable.eight_spades,R.drawable.nine_spades,
                R.drawable.ten_spades,   R.drawable.jack_spades, R.drawable.queen_spades,
                R.drawable.king_spades,
                /* hearts */
                R.drawable.ace_hearts,   R.drawable.two_hearts,  R.drawable.three_hearts,
                R.drawable.four_hearts,  R.drawable.five_hearts, R.drawable.six_hearts,
                R.drawable.seven_hearts, R.drawable.eight_hearts,R.drawable.nine_hearts,
                R.drawable.ten_hearts,   R.drawable.jack_hearts, R.drawable.queen_hearts,
                R.drawable.king_hearts,
                /* diamonds */
                R.drawable.ace_diamonds, R.drawable.two_diamonds,R.drawable.three_diamonds,
                R.drawable.four_diamonds,R.drawable.five_diamonds,R.drawable.six_diamonds,
                R.drawable.seven_diamonds,R.drawable.eight_diamonds,R.drawable.nine_diamonds,
                R.drawable.ten_diamonds, R.drawable.jack_diamonds,R.drawable.queen_diamonds,
                R.drawable.king_diamonds,
                /* clubs */
                R.drawable.ace_clubs,    R.drawable.two_clubs,   R.drawable.three_clubs,
                R.drawable.four_clubs,   R.drawable.five_clubs,  R.drawable.six_clubs,
                R.drawable.seven_clubs,  R.drawable.eight_clubs, R.drawable.nine_clubs,
                R.drawable.ten_clubs,    R.drawable.jack_clubs,  R.drawable.queen_clubs,
                R.drawable.king_clubs
        };
        List<Integer> bag = new ArrayList<>();
        for (int id : all) bag.add(id);
        Collections.shuffle(bag, new Random());

        int[] out = new int[n];
        for (int i = 0; i < n; i++) out[i] = bag.get(i);
        return out;
    }

    private Bitmap[] getRandomEmojiBitmaps(int n) {
        List<String> pool = new ArrayList<>();
        Collections.addAll(pool, match4x3.EMOJI_POOL);
        Collections.shuffle(pool, new Random());

        Bitmap[] out = new Bitmap[n];
        for (int i = 0; i < n; i++) out[i] = makeEmojiBitmap(pool.get(i));
        return out;
    }

    private Bitmap makeEmojiBitmap(String e) {
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 72, getResources().getDisplayMetrics());

        Bitmap bmp = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
        Canvas c   = new Canvas(bmp);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(px * 0.8f);
        p.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = p.getFontMetrics();
        float x = px / 2f;
        float y = px / 2f - (fm.ascent + fm.descent) / 2f;
        c.drawText(e, x, y, p);

        return bmp;
    }

    /* ---------------------- GAMEPLAY ---------------------- */
    public void onCardClick5x4(View v) {
        if (waiting) { flipBackTwo(); return; }
        if (processing) return;

        ImageButton btn = (ImageButton) v;
        Card curr = deck.getCardById(btn.getId());
        if (curr == null) return;
        if (firstCard != null && curr.getCardId() == firstCard.getCardId()) {
            Toast.makeText(this, "Select a different card!", Toast.LENGTH_SHORT).show();
            return;
        }

        revealFace(btn, curr);

        if (firstCard == null) { firstCard = curr; firstButton = btn;
            instructionsText.setText("Choose a second card."); return; }

        if (isMatch(firstCard, curr)) cardMatch(btn);
        else                           noMatch(btn);

        firstCard = null; firstButton = null;
    }

    private void revealFace(ImageButton b, Card c) {
        if (c.hasBitmap()) b.setImageBitmap(c.getBitmap());
        else               b.setImageResource(c.getDrawableRes());

        b.setScaleType("playing".equals(theme)
                ? ImageView.ScaleType.CENTER_CROP
                : ImageView.ScaleType.CENTER_INSIDE);
    }

    /* helpers */
    private boolean isMatch(Card a, Card b) {
        if (a.hasBitmap() && b.hasBitmap())   return a.getBitmap() == b.getBitmap();
        if (!a.hasBitmap() && !b.hasBitmap()) return a.getDrawableRes() == b.getDrawableRes();
        return false;
    }

    private void flipBackTwo() {
        if (processingFirst != null) {
            processingFirst.setImageResource(cardBack);
            processingFirst.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if (processingSecond != null) {
            processingSecond.setImageResource(cardBack);
            processingSecond.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        processing = waiting = false;
        processingFirst = processingSecond = null;
    }

    private void cardMatch(ImageButton b) {
        b.setEnabled(false); firstButton.setEnabled(false);
        b.setVisibility(View.INVISIBLE); firstButton.setVisibility(View.INVISIBLE);
        matchText.setText(String.valueOf(++numMatch));
        instructionsText.setText("They match! Choose another card.");
        if (numMatch == 10) gameOver();
    }

    private void noMatch(ImageButton b) {
        missText.setText(String.valueOf(++numMiss));
        instructionsText.setText("No match – tap anywhere to continue.");
        processing = waiting = true;
        processingFirst = firstButton; processingSecond = b;
    }

    /* ---------- end-of-game dialog ---------- */
    private void gameOver() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.dessert_donut_food_sweet_icon)
                .setTitle("Game Over")
                .setMessage("Play again?")
                .setPositiveButton("Yes", (d, w) -> recreate())
                .setNegativeButton("No",  (d, w) -> finish())
                .create();

        dialog.show();
        int magenta = getColor(R.color.magenta);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(magenta);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(magenta);
    }
}
