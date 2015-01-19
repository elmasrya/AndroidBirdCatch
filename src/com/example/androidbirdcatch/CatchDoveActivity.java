/*Course: CS2302
 *Section: 01
 *Name: Andrew El-Masry
 *Professor: Dr Shaw
 *Assignment #: Lab 13
 */

package com.example.androidbirdcatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class CatchDoveActivity extends Activity {
	private CatchDoveView flockView;     // the View
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_dove);
        
        flockView = new CatchDoveView(this);
        setContentView(flockView);
        flockView.start();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catch_dove, menu);
        return true;
    }
}
