package com.example.project1_mhanna22;


public class Card {
    private int cardId;
    private int picture;

    public Card(int id, int pic){
        cardId = id;
        picture = pic;
    }

    public int getCardId(){
        return cardId;
    }
    public int getPicture(){
        return picture;
    }
}
