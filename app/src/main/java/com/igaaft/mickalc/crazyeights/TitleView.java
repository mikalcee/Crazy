package com.igaaft.mickalc.crazyeights;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/** Title Screen
 *  Created by MickalC on 9/30/2015.
 */

public class TitleView extends View {

    private Bitmap titleGraphic; //declare the bitmap object for the title graphic
    private Bitmap playButtonUp; //declare the bitmap object for upstate button graphic
    private Bitmap playButtonDown; //declare the bitmap object for downstate button graphic

    private int screenW; //width of screen
    private int screenH; //height of screen
    private int placeTitle_xcoords;
    private int buttonUp_xcoords;
    private int buttonUp_ycoords;

    private boolean buttonPressed;
    private Context  context;
    Intent intent;

    public TitleView(Context context) {
        super(context);
        this.context = context;

        /*** Load bitmaps into memory ***/
        titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
        playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
        playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
    }// end constructor

    /*** method called by a view to grab the values of the width and height of the screen ***/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
    }// end method onSizeChanged

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Using relative positioning to place graphics on screen
        placeTitle_xcoords = (screenW - titleGraphic.getWidth())/2; //position graphic in middle of screen
        buttonUp_xcoords = (screenW - playButtonUp.getWidth())/2; //x coordinate
        buttonUp_ycoords = (int)( screenH * 0.7); //y coordinate

        canvas.drawBitmap(titleGraphic, placeTitle_xcoords, 0, null); //draw graphic at coordinates}

        if (buttonPressed){
            canvas.drawBitmap(playButtonDown, buttonUp_xcoords, buttonUp_ycoords, null);// draw button pressed graphic
        }else{
            canvas.drawBitmap(playButtonUp,buttonUp_xcoords, buttonUp_ycoords, null); //draw button not pressed graphic
        }// end if/else

    }// end method onDraw

    /*** Logic for handling cases when the user touches the screen ***/
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x_pos = (int) event.getX();
        int y_pos = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //Check to see whether the user is touching the screen within the bounds of the button graphic
                if (x_pos > buttonUp_xcoords && x_pos < buttonUp_xcoords + playButtonUp.getWidth() &&
                    y_pos > buttonUp_ycoords && y_pos < buttonUp_ycoords + playButtonUp.getHeight()){

                    buttonPressed = true; //button pressed
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                if (buttonPressed){
                    intent = new Intent(context, GameActivity.class);
                    context.startActivity(intent);// launch new activity
                }
                buttonPressed = false; //button not pressed
                break;
        }// end switch

        invalidate(); //tell the view that a change has occurred and that the canvas needs to be redrawn
        return true;
    }// end method onTouchEvent

}// end class TitleView
