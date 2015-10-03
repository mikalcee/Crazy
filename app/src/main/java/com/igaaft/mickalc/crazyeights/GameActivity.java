package com.igaaft.mickalc.crazyeights;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by Michael Canfield on 9/30/2015.
 * Game Activity is the main game activity screen
 */

//Main Game activity
public class GameActivity extends AppCompatActivity {

    private GameView gameView; //custom game view
    View decorView; //top-level window decor view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        gameView.setKeepScreenOn(true); //set to keep display from timing out

        /**
         * Retrieve the top-level window decor view (containing the standard
         window frame/decorations and the client's content inside of that),
         which can be added as a window to the window manager.
         */
        decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;// set window to full screen

        /* Request that the visibility of the status bar or
         other screen/window decorations be changed.
        */
        decorView.setSystemUiVisibility(uiOptions);

        /* Remember that you should never show the action bar if the
           status bar is hidden, so hide that too if necessary.
        */
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.hide();

        setContentView(gameView);

    }// end method onCreate

}// end class GameActivity
