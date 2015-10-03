package com.igaaft.mickalc.crazyeights;

import android.graphics.Bitmap;

/**class Card contains set and get methods for bitmap images and card ids
 * Created by MickalC on 9/30/2015.
 */
public class Card {
    private int id; //card id ex. deuce of diamonds = 102
    private Bitmap bitmap;

    /**
     * Constructor takes in the unique if for the card. Diamonds(100),
     * Clubs(200), Hearts(300), and Spades(400)
     * @param newID card id
     */
    public Card(int newID){
        id = newID;// contructor takes in the unique id for the card
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

}// end class Card
