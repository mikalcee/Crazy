package com.igaaft.mickalc.crazyeights;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TitleView titleView; //opening custom view
    View decorView; // top-level window decor view

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        titleView = new TitleView(this);
        titleView.setKeepScreenOn(true); //toggle to keep title screen from turning off

        /**
         * Retrieve the top-level window decor view (containing the standard
         window frame/decorations and the client's content inside of that),
         which can be added as a window to the window manager.
         */
        decorView = getWindow().getDecorView();// top-level window decor view

        //Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;// set window to full screen
        decorView.setSystemUiVisibility(uiOptions);

        /**
         * Remember that you should never show the action bar if the
         status bar is hidden, so hide that too if necessary.
         */
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.hide();

        setContentView(titleView);

    }// end method onCreate

}// end class MainActivity
