package com.example.project1_mhanna22;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Builds a shuffled deck in which every face appears exactly twice.  Callers may
 * supply either drawable resource IDs or Bitmaps (for Emoji).
 */
public class Deck {

    private final ArrayList<Card> cardDeck;

    /* ----------------  drawable constructor  ---------------- */
    public Deck(int[] viewIds, int[] drawableResIds) {
        Integer[] boxed = new Integer[drawableResIds.length];
        for (int i = 0; i < drawableResIds.length; i++) boxed[i] = drawableResIds[i];
        cardDeck = buildDeck(viewIds, boxed);
    }

    /* ----------------  bitmap constructor  ---------------- */
    public Deck(int[] viewIds, Bitmap[] bitmaps) {
        cardDeck = buildDeck(viewIds, bitmaps);
    }

    /* ----------------  core builder  ---------------- */
    private ArrayList<Card> buildDeck(int[] viewIds, Object[] pictures) {
        int uniqueNeeded = viewIds.length / 2;                 // 6 for 4×3, 10 for 5×4
        if (pictures.length < uniqueNeeded) {
            throw new IllegalArgumentException("Not enough pictures supplied to build deck");
        }

        List<Object> pool = new ArrayList<>();
        Collections.addAll(pool, pictures);
        Collections.shuffle(pool, new Random());

        /* pick the faces we’ll actually use, duplicate each once, then shuffle */
        List<Object> faces = new ArrayList<>(uniqueNeeded * 2);
        for (int i = 0; i < uniqueNeeded; i++) {
            Object face = pool.get(i);
            faces.add(face);
            faces.add(face);
        }
        Collections.shuffle(faces, new Random());

        /* marry every face with a view-ID */
        ArrayList<Card> deck = new ArrayList<>(viewIds.length);
        for (int i = 0; i < viewIds.length; i++) {
            Object face = faces.get(i);
            if (face instanceof Integer) {
                deck.add(new Card(viewIds[i], (Integer) face));
            } else {
                deck.add(new Card(viewIds[i], (Bitmap) face));
            }
        }
        return deck;
    }

    /* lookup helper */
    public Card getCardById(int id) {
        for (Card c : cardDeck) if (c.getCardId() == id) return c;
        return null;
    }
}
