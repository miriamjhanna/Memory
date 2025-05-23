package com.example.project1_mhanna22;

import android.graphics.Bitmap;

/**
 * A memory-game card that can show either a drawable resource or a dynamically
 * generated Bitmap (used for Emoji faces).
 */
public class Card {

    private final int     cardId;
    private final Integer drawableRes;   // nullable
    private final Bitmap  bitmap;        // nullable

    /** Constructor for drawable-resource faces */
    public Card(int cardId, int drawableRes) {
        this.cardId      = cardId;
        this.drawableRes = drawableRes;
        this.bitmap      = null;
    }

    /** Constructor for Bitmap faces (Emoji) */
    public Card(int cardId, Bitmap bitmap) {
        this.cardId      = cardId;
        this.drawableRes = null;
        this.bitmap      = bitmap;
    }

    public int     getCardId()      { return cardId; }
    public boolean hasBitmap()      { return bitmap != null; }
    public Bitmap  getBitmap()      { return bitmap; }
    public int     getDrawableRes() { return drawableRes != null ? drawableRes : 0; }
}
