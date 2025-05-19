package com.example.project1_mhanna22;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Deck{
    private ArrayList<Card> cardDeck;
    int[] ids;
    private int[] pictures;

    public Deck(int[] idArray, int[] picList){
        cardDeck = new ArrayList<>();
        ids = new int[idArray.length];
        pictures = new int[idArray.length];
        System.arraycopy(idArray, 0, ids, 0, idArray.length);
        for(int i=0, j=0; i < ids.length; i+=2, j++){
            pictures[i] = picList[j];
            pictures[i+1] = picList[j];
        }
        mapDeck();
    }
    private void mapDeck(){
        Random random = new Random();
        for (int id : ids) {
            int randPicIdx;
            do {
                randPicIdx = random.nextInt(pictures.length);
            } while (pictures[randPicIdx] == 0);
            int randPicture = pictures[randPicIdx];
            Card card = new Card(id, randPicture);
            cardDeck.add(card);
            pictures[randPicIdx] = 0;
        }
    }

    public Card getCardById(int id){
        Card cardReturn = null;
        for(Card card : cardDeck){
            if(card.getCardId() == id){
                cardReturn = card;
                break;
            }
        }
        return cardReturn;
    }
    public List<Card> getDeck(){
        return cardDeck;
    }
}
