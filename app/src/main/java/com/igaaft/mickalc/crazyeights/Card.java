package com.igaaft.mickalc.crazyeights;

import android.graphics.Bitmap;

/**class Card contains set and get methods for bitmap images and card ids
 * Created by MickalC on 9/30/2015.
 */
public class Card {
    private int id; //card id ex. deuce of diamonds = 102
    private Bitmap bitmap;
    private int suit;
    private int rank;

    /**
     * Constructor takes in the unique if for the card. Diamonds(100),
     * Clubs(200), Hearts(300), and Spades(400)
     * @param newID card id
     */
    public Card(int newID){
        id = newID;// constructor takes in the unique id for the card
        suit = Math.round((id / 100) * 100); //rounds id to nearest hundred to get suit
        rank = id - suit;
    }// end constructor

    /**
     * setBitmap sets the bitmap image for a particular card
     * @param newBitmap bitmap image
     */
    public void setBitmap(Bitmap newBitmap){
        bitmap = newBitmap;
    }// end method getBitmap

    /**
     * getBitmap returns the bitmap image for a particular card
      * @return bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }// end method getBitmap

    /**
     * getId returns ID for a particular card
     * @return id
     */
    public int getId() {
        return id;
    }// end method getID

    //get suit of card
    public int getSuit() {
        return suit;
    }// end method getSuit

    //get rank of card
    public int getRank() {
        return rank;
    }// end method getRank

}// end class Card
