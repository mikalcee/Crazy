package com.igaaft.mickalc.crazyeights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/***************************************************
 * Created by Michael Canfield on 9/30/2015.
 * GameView is the main custom view for the game
 ***************************************************/

public class GameView  extends View {

    private Context context; //local copy of context

    private List<Card> deck = new ArrayList<>(); //list of card objects in a deck
    private List<Card> myHand = new ArrayList<>(); //list of card objects held by user
    private List<Card> oppHand = new ArrayList<>(); //list of card objects held by user
    private List<Card> discardPile = new ArrayList<>(); //list of card objects discarded

    private Card tempCard; //a card in the deck
    private Paint whitePaint; //defines the properties for drawing to screen
    private Bitmap cardBack; //back of card graphic

    private float scale; //lets you scale elements on the screen (i.e. text)

    private int screenW; //width of screen
    private int screenH; //height of screen
    private int scaledCardW; //scaled width for card to fit game screen
    private int scaledCardH; //scaled height for card to fit game screen

    private int oppScore; //opponent's score
    private int myScore; //user's score
    private int movingCardIdx = -1; //keeps track of the index of the card that being moved
    private int movingX; //keeps track of the x value of the user finger on the screen
    private int movingY; //keeps track of the y value of the user finger on the screen

    private boolean myTurn; //keeps track of player's turn

    /**
     * Custom view GameView is the main game playing screen. This screen
     * contains the scores, opponent's playing cards, user playing cards,
     * and discard card pile.
     * @param context GameActivity's context (interface to global
     *                information about an application environment
     */

    public GameView(Context context) {
        super(context);
        this.context = context;

        /*******************************************************************
         * Sets the scaling factor to the density settings for the device
         * the game appears on
         *******************************************************************/

        scale = this.getResources().getDisplayMetrics().density;

        /************************************************************************
         * create new Paint object and define the properties of the Paint object
         ************************************************************************/
        whitePaint = new Paint();

        /***********************************************************************************
         * sets or clears the ANTI_ALIAS_FLAG bit AntiAliasing smooths out the
         * edges of what is being drawn, but is has no impact on the interior of the shape.
         ************************************************************************************/

        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.RED); //sets the paint's color

        /*********************************************************************************
         * Set the paint's style, used for controlling how primitives'
         * geometries are interpreted (except for drawBitmap, which always assumes Fill).
         *********************************************************************************/

        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setTextAlign(Paint.Align.LEFT); //sets the paint's text alignment.
        whitePaint.setTextSize(scale * 15); //sets the paint's text size.

       //myTurn = new Random().nextBoolean(); //returns a random boolean true or false

        /************************* FOR TEST PURPOSES ONLY *********************************/

        myTurn = true;

        /**********************************************************************************/
    }// end constructor

    /**************************************************************
     * OnSizeChanged is called by a view to grab the values of the
     * width and height of the screen
     * @param w Screen width
     * @param h Screen height
     * @param oldw old width
     * @param oldh old height
     **************************************************************/

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        //get bitmap graphic and hold in a temporary location
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.card_back);
        scaledCardW = (screenW/8); //scale factor for width
        scaledCardH = (int)(scaledCardW * 1.28); //scale factor for height
        //create scaled version of original bitmap graphic
        cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

        initCards();
        dealCards();
        drawCard(discardPile);
    }// end method onSizeChanged

    /****************************************************
     * InitCards create card id, card objects and bitmaps
     ****************************************************/

    private void initCards(){
        //cycles through the 4 suits
        for (int i = 0; i < 4; i++){
            //cycles through each card rank
            for (int j = 102; j < 115; j++){
                int tempID = j + (i * 100);// get id for card
                tempCard = new Card(tempID);// create new card object and pass the id number

                /***************************************************************
                 * Return a Resources instance for your application's package.
                 * getIdentifier(String name, String defType, String defPackage)
                 * Return a resource identifier for the given resource name.
                 * String name="card"+tempID; String defType="drawable";
                 * String defPackage=context.getPackageName().
                 ****************************************************************/

                int resourceID = this.getResources().getIdentifier("card" + tempID, "drawable",
                        context.getPackageName());

                /****************************************************************
                 * Parameters
                 * res	The resources object containing the image data
                 * id	The resource id of the image data
                 * Returns
                 * The decoded bitmap, or null if the image could not be decoded.
                 ******************************************************************/

                Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(),
                        resourceID);

                /**
                 * scaled width i 1/8 the screen width. Allow seven cards to display
                 */

                scaledCardW = (screenW/8);
                scaledCardH = (int)(scaledCardW * 1.28);

                //create a scaled bitmap of original bitmap
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW,
                        scaledCardH, false);
                tempCard.setBitmap(scaledBitmap);//set the scaled bitmap to a card
                deck.add(tempCard);// add new card to deck
            }// end for loop

        }// end for loop

    }// end method initCards

    /***************************************************************************************
     * The most important step in drawing a custom view is to override the onDraw() method.
     * The parameter to onDraw() is a Canvas object that the view can use to draw itself.
     * The Canvas class defines methods for drawing text, lines, bitmaps, and many other
     * graphics primitives. You can use these methods in onDraw() to create your custom
     * user interface (UI).
     * @param canvas object for drawing on for the ondraw() method
     ****************************************************************************************/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**********************************************************************
         *draw text 10 pixels from the left of the screen and to from the top,
         *which you get by adding the size of the text to 10
         ***********************************************************************/

        canvas.drawText("Computer Score: " + Integer.toString(oppScore),
                10, whitePaint.getTextSize() + 10, whitePaint);

        /**********************************************************************
         * draw text on the bottom of the screen and subtracting the text size
         * and 10 pixels from the height of the screen
         ***********************************************************************/

        canvas.drawText("My Score: " + Integer.toString(myScore),
                10, screenH - whitePaint.getTextSize() - 10, whitePaint);

        /*********************************************************************************
         * Loop through the first seven cards, and lay them out horizontally,
         * 5 pixes apart. The y position of each card subtracts the height of the
         * card, the height of your score text, and 50 scaled pixels from bottom of screen
         ***********************************************************************************/

        for (int i = 0; i < myHand.size(); i++){
            if (i < 7){
                canvas.drawBitmap(myHand.get(i).getBitmap(), i * (scaledCardW + 5),
                        screenH - scaledCardH - whitePaint.getTextSize() - (50 *scale),
                        null);
            }// end if
        }// end for loop

        /*********************************************************************************
         * drawn the opponent card graphic face down. Space the cards 5 pixels apart so
         * they overlap. Draw the cards at the height of the text plus 50 pixels from the
         * top of the screen.
         **********************************************************************************/

        for (int i = 0; i < oppHand.size(); i++){
            canvas.drawBitmap(cardBack, i * (scale * 5), whitePaint.getTextSize() +
                    (50 * scale), null);
        }// end for loop

        /*****************************************************************************
         * draw pile is represented by a single card back image. The image is drawn
         * centered on the screen, so the x position starts with half the screen width
         * minus the width of the card and a slight offset of 10 pixels. The y position
         * is half the screen height minus half the height of the card image
         ******************************************************************************/
        canvas.drawBitmap(cardBack, (screenW / 2) - cardBack.getWidth() - 10,
                (screenH / 2) - (cardBack.getHeight() / 2), null);

        /***********************************************************************************
         * check to see if discard pile has cards, if it does display a card bitmap graphic
         * top card (index 0) slightly to right of the draw pile and at the same height
         **********************************************************************************/

        if (!discardPile.isEmpty()){
            canvas.drawBitmap(discardPile.get(0).getBitmap(), (screenW / 2) + 10,
                    (screenH / 2) - (cardBack.getHeight() / 2), null);
        }// end if

        for (int i = 0; i < myHand.size(); i++){
            /*************************************************************************
             * Check to see whether the index of a given card in your hand matches
             * the movingCardIdx. If so, draw the card at the current x, y positions
             * of the player's finger.
             **************************************************************************/
            if (i == movingCardIdx){
                canvas.drawBitmap(myHand.get(i).getBitmap(), movingX, movingY, null);
            }// end if
            /***************************************************************************
             * If the movingCardIdx doesn't match any index values for cards in your
             * hand (for example when it's -1), you draw the rest of your hand as
             * you did before
             ****************************************************************************/
            else{
                canvas.drawBitmap(myHand.get(i).getBitmap(), i * (scaledCardH + 5),
                                   screenH - scaledCardH - whitePaint.getTextSize() -
                                           (50 * scale), null);
            }// end else
        }// end for

    }// end method onDraw

    /***********************************************************
     * OnTouchEvent is fired when the user touches the screen
     * @param event MotionEvent variable
     * @return true
     ************************************************************/

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x_pos = (int) event.getX();
        int y_pos = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                /**********************************************************
                 * If users turn, loop through the first seven cards
                 * in the player's hand, and check to see whether the
                 * player has touched the screen on a card that's being
                 * displayed. if so, you assign the index of that card to
                 * the method movingCardIdx as well as to the current x
                 * and y positions to the movingX and movingY variables.
                 * A offset of 30 pixel to the left and a 70 pixel offset
                 * up. This centers the image at the point of touch. The
                 * vertical offset is used so that the user can see the
                 * rank and suit of the card as it moves.
                 ***********************************************************/
                if (myTurn){
                    for (int i = 0; i < 7; i++){
                        if (x_pos > i * (scaledCardH + 5) &&
                            x_pos < i * (scaledCardH + 5) + scaledCardH &&
                            y_pos > screenH - scaledCardH - whitePaint.getTextSize() -
                            (50 * scaledCardH)){
                            movingCardIdx = i;
                            movingX = x_pos;
                            movingY = y_pos;

                            //movingX = x_pos - (int)(30 * scale);
                            //movingY = y_pos -(int)(70 * scale);
                        }// end if
                    }// end for
                }// end if
                break;

            case MotionEvent.ACTION_MOVE:
                /****************************************************************
                 * As the player moves their finger across the screen, you keep
                 * track of the x and y. You use this information when drawing
                 * the bitmap for the card being moved. A offset of 30 pixel to
                 * the left and a 70 pixel offset up. This centers the image at
                 * the point of touch. The vertical offset is used so that the
                 * user can see the rank and suit of the card as it moves.
                 *****************************************************************/
                movingX = x_pos;
                movingY = y_pos;

                //movingX = x_pos - (int)(30 * scale);
                //movingY = y_pos -(int)(70 * scale);
                break;

            case MotionEvent.ACTION_UP:
                /******************************************************************
                 * When a player lifts the finger from the screen, you reset the
                 * movingCardIdx to indicate that no cards are moved
                 *******************************************************************/
                movingCardIdx = -1;
                break;
        }// end switch

        /********************************************************
         * tell the view that a change has occurred and that the
         * canvas needs to be redrawn
         ********************************************************/

        invalidate();
        return true;
    }// end method onTouchEvent

    /*******************************************************************
     * DrawCard is use for drawing a single card from the deck
     * and adding it to a particular list of cards. The method passes
     * in the hand to which the card will be added. the card at index 0
     * of the deck is then added to the hand and removed from the deck.
     * @param handToDraw variable for drawing hand
     ********************************************************************/

    private void drawCard(List<Card> handToDraw){

        /*******************************************************************
         * gets the element (card) at the specified location in list (deck)
         * add that card (element)to the list (hand draw) at the specified
         * location (1st position in hand)
         ********************************************************************/

        handToDraw.add(0, deck.get(0));
        deck.remove(0); //remove that element (card) from the list (deck)

        /*********************************************************************
         * if the draw pile is empty, you shuffle back into it all the cards
         * of the discard pile, except for the top one. If the deck is empty
         * after a draw, you loop through all cards except the first one in
         * the discard pile, add the first one to the deck, and then remove
         * it from the discard pile.
         **********************************************************************/

        if (deck.isEmpty()){
            for (int i = discardPile.size() - 1; i > 0; i--){
                deck.add(discardPile.get(i));
                discardPile.remove(i);

                /**************************************************************
                 * Java provided utility function for collections to randomize
                 * (shuffle) the order of the list, the static method shuffle
                 * is used
                 **************************************************************/

                Collections.shuffle(deck, new Random());
            }// end for loop

        }// end if

    }// end method drawCard

    /***********************************************************************************
     * dealCards uses class Collections static method shuffle to move every element of
     * the list (deck)to a random new position in the list using the specified random
     * number generator.
     * Parameters
     ************************************************************************************/

    private void dealCards(){
        Collections.shuffle(deck, new Random());
        //draw seven cards
        for (int i = 0; i < 7; i++){
            drawCard(myHand); //draw a single card from deck
            drawCard(oppHand); //draw a single card from deck
        }// end for loop
    }// end method dealCards

}// end class GameView
