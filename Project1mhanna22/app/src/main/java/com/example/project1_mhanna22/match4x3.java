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


public class match4x3 extends AppCompatActivity {
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
        setContentView(R.layout.activity_match4x3);
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
        assignCards();
        matchText = findViewById(R.id.num_match4x3);
        missText = findViewById(R.id.num_miss4x3);
        instructionsText = findViewById(R.id.instructions4x3);

        numMatch = 0;
        numMiss = 0;
    }
    protected void assignCards(){
        bitmaps = new int[6];
        bitmaps[0] = R.drawable.academy_brush_color_paint_pallete_icon;
        bitmaps[1] = R.drawable.animal_marine_prawn_seafood_shrimp_icon;
        bitmaps[2] = R.drawable.app_brand_brands_logo_logos_icon;
        bitmaps[3] = R.drawable.cat_icon;
        bitmaps[4] = R.drawable.brain_divide_inkcontober_sains_icon;
        bitmaps[5] = R.drawable.floor_laughing_on_rolling_the_icon;

        cards = new int[12];
        cards[0] = R.id.card4x3_00;
        cards[1] = R.id.card4x3_01;
        cards[2] = R.id.card4x3_02;
        cards[3] = R.id.card4x3_10;
        cards[4] = R.id.card4x3_11;
        cards[5] = R.id.card4x3_12;
        cards[6] = R.id.card4x3_20;
        cards[7] = R.id.card4x3_21;
        cards[8] = R.id.card4x3_22;
        cards[9] = R.id.card4x3_30;
        cards[10] = R.id.card4x3_31;
        cards[11] = R.id.card4x3_32;

        mappedDeck4x3 = new Deck(cards, bitmaps);
        cardDeck = mappedDeck4x3.getDeck();
    }

    public void onCardClick4x3(View view){
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
        if(numMatch == 6){
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
        new AlertDialog.Builder(match4x3.this)
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