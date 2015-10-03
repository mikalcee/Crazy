package com.igaaft.mickalc.crazyeights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael Canfield on 9/30/2015.
 * GameView is the custom view for the main game
 */
public class GameView  extends View {

    private Context context; //local copy of context

    private List<Card> deck = new ArrayList<>(); //list of card objects in a deck
    private List<Card> myHand = new ArrayList<>(); //list of card objects held by user
    private List<Card> oppHand = new ArrayList<>(); //list of card objects held by user
    private List<Card> discardPile = new ArrayList<>(); //list of card objects discarded

    Card tempCard; //a card in the deck

    private int screenW; //width of screen
    private int screenH; //height of screen
    private int scaledCardW; //scaled width for card to fit game screen
    private int scaledCardH; //scaled height for card to fit game screen

    public GameView(Context context) {
        super(context);
        this.context = context;
    }// end constructor

    /**
     * OnSizeChanged is called by a view to grab the values of the
     * width and height of the screen
     * @param w Screen width
     * @param h Screen height
     * @param oldw old width
     * @param oldh old height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        initCards();
    }// end method onSizeChanged

    /**
     * InitCards create card id, card objects and bitmaps
     */
    private void initCards(){
        //cycles through the 4 suits
        for (int i = 0; i < 4; i++){
            //cycles through each card rank
            for (int j = 102; j < 115; j++){
                int tempID = j + (i * 100);// get id for card
                tempCard = new Card(tempID);// create new card object and pass the id number

                /**
                 * Return a Resources instance for your application's package.
                 * getIdentifier(String name, String defType, String defPackage)
                 * Return a resource identifier for the given resource name.
                 * String name="card"+tempID; String defType="drawable";
                 * String defPackage=context.getPackageName().
                 */
                int resourceID = this.getResources().getIdentifier("card" + tempID, "drawable", context.getPackageName());

                /**
                 * Parameters
                 * res	The resources object containing the image data
                 * id	The resource id of the image data
                 * Returns
                 * The decoded bitmap, or null if the image could not be decoded.
                 */
                Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);

                scaledCardW = (screenW/8);// scaled width i 1/8 the screen width. Allow seven cards to display
                scaledCardH = (int)(scaledCardW * 1.28);

                //create a scaled bitmap of original bitmap
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
                tempCard.setBitmap(scaledBitmap);//set the scaled bitmap to a card
                deck.add(tempCard);// add new card to deck
            }// end for loop

        }// end for loop

    }// end method initCards

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }// end method onDraw

    /**
     * OnTouchEvent is fired when the user touches the screen
     * @param event MotionEvent variable
     * @return true
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x_pos = (int) event.getX();
        int y_pos = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }// end switch

        /**
         * tell the view that a change has occurred and that the
         * canvas needs to be redrawn
         */
        invalidate();
        return true;
    }// end method onTouchEvent

    /**
     * DrawCard is use for drawing a single card from the deck
     * and adding it to a particular list of cards. THe method passes
     * in the hand to which the card will be added. the card at index 0
     * of the deck is then added to the hand and removed from the deck.
     * @param handToDraw variable for drawing hand
     */
    private void drawCard(List<Card> handToDraw){
        handToDraw.add(0, deck.get(0));
        deck.remove(0);

        /**
         * if the draw pile is empty, you shuffle back into it all the cards
         * of the discard pile, except for the top one. If the deck is empty
         * after a draw, you loop through all cards except the first one in
         * the discard pile, add the first one to the deck, and then remove
         * it from the discard pile.
         */
        if (deck.isEmpty()){
            for (int i = discardPile.size() - 1; i > 0; i--){
                deck.add(discardPile.get(i));
                discardPile.remove(i);

                /**
                 * Java provided utility function for collections to randomize
                 * (shuffle) the order of the list.
                 */
                Collections.shuffle(deck, new Random());
            }// end for loop
        }// end if
    }// end method drawCard

    private void dealCards(){
        Collections.shuffle(deck, new Random());
        for (int i = 1; i < 7; i++){
            drawCard(myHand);
            drawCard(oppHand);
        }// end for loop
    }// end method dealCards

}// end class GameView
