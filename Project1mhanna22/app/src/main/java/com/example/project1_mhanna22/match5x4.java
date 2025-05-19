package com.example.project1_mhanna22;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class match5x4 extends AppCompatActivity {
    int[] bitmaps;

    int[] cards;

    int numMatch;

    int numMiss;

    Deck mappedDeck4x3;
    List<Card> cardDeck;

    private Card firstCard = null;

    private ImageButton firstButton = null, processingFirst, processingSecond;

    private boolean processing = false, waiting = false;

    private int cardBack = R.drawable.cs477p1_cardback;

    private TextView matchText, missText, instructionsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match5x4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View clickListener = findViewById(R.id.main);
        clickListener.setClickable(true);
        clickListener.setOnClickListener(v ->{
            if(waiting){
                //flip 2 cards over
                if(processingFirst != null){
                    processingFirst.setImageResource(cardBack);
                    processingFirst.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                if(processingSecond != null){
                    processingSecond.setImageResource(cardBack);
                    processingSecond.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                processing = false;
                waiting = false;
                processingFirst = null;
                processingSecond = null;
            }
        });

        matchText = findViewById(R.id.num_match5x4);
        missText = findViewById(R.id.num_miss5x4);
        instructionsText = findViewById(R.id.instructions5x4);

        assignCards();

        numMatch = 0;
        numMiss = 0;
    }

    protected void assignCards(){
        bitmaps = new int[10];
        bitmaps[0] = R.drawable.academy_brush_color_paint_pallete_icon;
        bitmaps[1] = R.drawable.animal_marine_prawn_seafood_shrimp_icon;
        bitmaps[2] = R.drawable.app_brand_brands_logo_logos_icon;
        bitmaps[3] = R.drawable.cat_icon;
        bitmaps[4] = R.drawable.brain_divide_inkcontober_sains_icon;
        bitmaps[5] = R.drawable.floor_laughing_on_rolling_the_icon;
        bitmaps[6] = R.drawable.christmas_tree_xmas_icon;
        bitmaps[7] = R.drawable.eye_glasses_office_vision_icon;
        bitmaps[8] = R.drawable.food_pizza_icon;
        bitmaps[9] = R.drawable.pencil_icon;

        cards = new int[20];
        cards[0] = R.id.card5x4_00;
        cards[1] = R.id.card5x4_01;
        cards[2] = R.id.card5x4_02;
        cards[3] = R.id.card5x4_03;
        cards[4] = R.id.card5x4_10;
        cards[5] = R.id.card5x4_11;
        cards[6] = R.id.card5x4_12;
        cards[7] = R.id.card5x4_13;
        cards[8] = R.id.card5x4_20;
        cards[9] = R.id.card5x4_21;
        cards[10] = R.id.card5x4_22;
        cards[11] = R.id.card5x4_23;
        cards[12] = R.id.card5x4_30;
        cards[13] = R.id.card5x4_31;
        cards[14] = R.id.card5x4_32;
        cards[15] = R.id.card5x4_33;
        cards[16] = R.id.card5x4_40;
        cards[17] = R.id.card5x4_41;
        cards[18] = R.id.card5x4_42;
        cards[19] = R.id.card5x4_43;

        mappedDeck4x3 = new Deck(cards, bitmaps);
        cardDeck = mappedDeck4x3.getDeck();
    }

    public void onCardClick5x4(View view){
        if(waiting){
            isWaiting();
            return;
        }
        if(processing){
            return;
        }
        ImageButton button = (ImageButton) view;
        int cardId = button.getId();
        Card currCard = mappedDeck4x3.getCardById(cardId);
        if(currCard == null){
            return;
        }
        if(firstCard != null && cardId == firstCard.getCardId()){
            Toast.makeText(getApplicationContext(), "Select a new card!", Toast.LENGTH_SHORT).show();
            return;
        }
        int pic = currCard.getPicture();

        button.setImageResource(pic);
        button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //first card selected
        if(firstCard == null){
            firstCard = currCard;
            firstButton = button;
            instructionsText.setText("Choose a second card.");
            return;
        }
        //matching cards!
        else if (firstCard.getPicture() == currCard.getPicture()) {
            cardMatch(button);
        }
        //no match
        else {
            noMatch(button);
        }
        firstButton = null;
        firstCard = null;
    }
    private void isWaiting(){
        if(processingFirst != null){
            processingFirst.setImageResource(cardBack);
            processingFirst.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if(processingSecond != null){
            processingSecond.setImageResource(cardBack);
            processingSecond.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        processing = false;
        waiting = false;
        processingFirst = null;
        processingSecond = null;
    }

    private void cardMatch(ImageButton button){
        button.setEnabled(false);
        firstButton.setEnabled(false);
        button.setVisibility(View.INVISIBLE);
        firstButton.setVisibility(View.INVISIBLE);
        numMatch++;
        instructionsText.setText("They match! Good job! Choose a card to continue.");
        matchText.setText(String.valueOf(numMatch));
        if(numMatch == 10){
            gameOver();
        }
    }
    private void noMatch(ImageButton button){
        numMiss++;
        instructionsText.setText("Oh no! Keep trying. Click anywhere to continue.");
        missText.setText(String.valueOf(numMiss));
        processing = true;
        waiting = true;
        processingFirst = firstButton;
        processingSecond = button;
    }
    private void gameOver(){
        new AlertDialog.Builder(match5x4.this)
                .setIcon(R.drawable.dessert_donut_food_sweet_icon)
                .setTitle("Game Over")
                .setMessage("Do you want to play again?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    recreate();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    finish();
                })
                .show();
    }
}