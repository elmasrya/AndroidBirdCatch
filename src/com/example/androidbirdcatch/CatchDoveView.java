package com.example.androidbirdcatch;
/**
 * The CatchDoveView Class - 
 * An Android View for catching AndroidDoves in a flock
 */

/*Course: CS2302
 *Section: 01
 *Name: Andrew El-Masry
 *Professor: Dr Shaw
 *Assignment #: Lab 13
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class CatchDoveView extends View {
   private final int DOVEAMT = 15;                // Number of doves
   private final int DELAY = 30;                  // Delay between dove moves
   private final int STARTRADIUS = 200;           // Radius of doves from center
   private final double SIZERATIO = 0.5;          // Size ratio of doves
   private final boolean STARTSAMEDIR = false;    // Flag for birds facing same dir

   private boolean started = false;               // Flag for starting simulation
   private int scrWidth = 480, scrHeight = 690;
   private int doveWidth, doveHeight, offHeight;
   private ArrayList <AndroidDoveFlockModel> doves;   // Dove list
   private ArrayList <AndroidDoveFlockModel> rmvdoves; 
   private AndroidDoveFlockModel selectedDove;
   
   private Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.oracleskyline);
   private Rect bkgRect = new Rect(0,0,0,0);
   private Paint paint = new Paint();
   private boolean down = false;
   private int downX = -1, downY = -1;
   private int rmvAmount = 0;
   
   private static Timer myTimer;                  // Timer for simulation
   
   // Constructor that instantiates the doves and the timer
   public CatchDoveView(Context context) {
      super(context);
      
      doves = new ArrayList<AndroidDoveFlockModel>();
      rmvdoves = new ArrayList<AndroidDoveFlockModel>();
      for (int i = 0; i < DOVEAMT; ++i)
         addDove(context);
      if (DOVEAMT > 0) {
         doveWidth = doves.get(0).getWidth();
         doveHeight = doves.get(0).getHeight();
         offHeight = 2 * doveHeight + 10;
      }
      
      myTimer = new Timer();
      myTimer.schedule(new TimerTask() {          
         @Override
         public void run() {
            TimerMethod();
         }
      }, 0, DELAY);
   }
   
   private void TimerMethod() {
      // We call the method that will work with the UI thread
      // through the post method.
      this.post(Timer_Tick);
   }

   // Timer thread
   private Runnable Timer_Tick = new Runnable() {
      @Override
      public void run() {
         if (started)  {
            for (AndroidDoveFlockModel dove : doves) {
            	if (selectedDove == null && down == true &&
            			downX >= dove.getX() && downY >= dove.getY() && 
            			downX <= dove.getX() + dove.getWidth() &&
            			downY <= dove.getY() + dove.getHeight())
            		selectedDove = dove;
            	else if (dove != selectedDove)
            	dove.move(doves,scrWidth,scrHeight-offHeight);
            	
            	
            	
            }
            for (AndroidDoveFlockModel dove : rmvdoves) {
            	dove.move();
            	
            }
            invalidate();
         }
      }
   };

   // Handles drawing the GUI screen
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      
      bkgRect.set(0,0,scrWidth,scrHeight-offHeight);
      canvas.drawBitmap(background,null,bkgRect,paint);
      
      paint.setColor(Color.GREEN);
      canvas.drawLine(0, scrHeight-offHeight, scrWidth, scrHeight-offHeight, paint);

      for (AndroidDoveFlockModel dove : doves)
         dove.draw(canvas,selectedDove == dove);
      for (AndroidDoveFlockModel dove : rmvdoves)
          dove.draw(canvas,false);
      
      if(doves.size() == 0)
    	  canvas.drawText("You win!!",  scrWidth/2 -75, scrHeight/2, paint);
      
      if (doves.size() == 0) {
    	  paint.setColor(Color.RED);
    	  paint.setTextSize(36);
        canvas.drawText("You win!!",  scrWidth/2 -75, scrHeight/2, paint);
    	  
    	 // canvas
    	  
      }
   }

   // Handles touch events
   @Override
   public boolean onTouchEvent(MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_UP) {
         down = false;
         downX = downY = -1;
         selectedDove = null;
      } else {
         down = true;
         if (selectedDove != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            int offX = 0, offY = 0;
            if (downX != -1)
               offX = ((int) event.getX()) - downX;
            
            if (downY != -1)
               offY = ((int) event.getY()) - downY;
            // modify dove here
            selectedDove.setX(selectedDove.getX() + offX);
            selectedDove.setY(selectedDove.getY() + offY);
         }
         downX = (int) event.getX();
         downY = (int) event.getY();
        
         if (selectedDove != null && downY > scrHeight-offHeight) {
        	 

        	 
        	 doves.remove(selectedDove);
        	 selectedDove = null;
            
             
             int yOff = (rmvAmount < 10) ? 0 : 1;
             rmvdoves.add(new AndroidDoveFlockModel(getContext(), 
             true,((rmvAmount % 10) * doveWidth), scrHeight-offHeight + yOff * doveHeight, 0,0,SIZERATIO));
              ++rmvAmount; 
             
        	 // modify doves here
         }
         invalidate();
      }
      return true;
   }

   // Handles screen size change events
   @Override
   protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
      super.onSizeChanged(xNew, yNew, xOld, yOld);
      
      scrWidth = xNew;
      scrHeight = yNew;
   }

   // Handles adding a new dove to the simulation
   protected void addDove(Context context) {
      AndroidDoveFlockModel dove =
              new AndroidDoveFlockModel(context,true,0,0,0,0,SIZERATIO);

      int centerX = scrWidth / 2;
      int centerY = scrHeight / 2;
      
      double radius = Math.random() * STARTRADIUS;
      double angle = Math.random() * 2 * Math.PI;
        
      int x = (int)(radius * Math.cos(angle)) + centerX;
      if (x > scrWidth-dove.getWidth())
         x = scrWidth-dove.getWidth();
      if (x < 0)
         x = 0;
      int y = (int)(radius * Math.sin(angle)) + centerY;
      if (y > scrHeight-3*dove.getHeight()-10)
         y = scrHeight-3*dove.getHeight()-10;
      if (y < 0)
         y = 0;
      dove.setX(x);
      dove.setY(y);
    
      int speedX = 5 + (int) (Math.random() * 6);
      int speedY = 5 + (int) (Math.random() * 6);
      if (!STARTSAMEDIR)  {
         if (Math.random() < 0.5)
            speedX *= -1;
         if (Math.random() < 0.5)
            speedY *= -1;
      }
      dove.setXVelocity(speedX);
      dove.setYVelocity(speedY);
      
      dove.setIsRight(speedX>0);
      dove.setImageIndex((int)(Math.random() * 8));
      
      doves.add(dove);
   }

   // Handles removing a dove from the simulation
   public void removeDove() {
      if (doves.size() > 0)
         doves.remove(doves.size()-1);
   }

   // Handles starting the simulation
   public void start() {
      started = true;
      invalidate();
   }
}