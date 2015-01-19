package com.example.androidbirdcatch;
/*Course: CS2302
 *Section: 01
 *Name: Andrew El-Masry
 *Professor: Dr Shaw
 *Assignment #: Lab 13
 */

/**
 * The DoveFlockModel Class - 
 * Extends the AndroidDove class so that Android doves can move
 * according to the Flocking Algorithm rules.  Also implements a
 * simple rule for rebounding off of the boundaries of the
 * borders of the screen.  Also allows doves to be resized to
 * any dimension by applying a given ratio to the normal Dove
 * class dimensions (a ratio of 2.0 doubles size, 0.5 halves
 * it).
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class AndroidDoveFlockModel extends AndroidDove {
   public final double DEFRADIUS = 150;     // The default radius a flock extends to
   public final double BOUNDARY = 5;        // The margin to turn doves from
   public final double MAXSPEED = 15;       // The maximum dove speed
   public final double REBOUNDSPEED = 5;    // The speed doves turn from BOUNDARY
   // Final fields to be set in constructor
   public final double FLOCKRADIUS;         // The computed radius a flock extends to
   public final double TOOCLOSE;            // The distance of too close doves

   Paint redPaint;                          // Red paint for drawing flock radius
   RectF oval;                              // Oval for drawing flock radius
   
    /**
     * AndroidDoveFlockModel one-arg constructor
     *
     * @param context is the activity context
     */ 
   public AndroidDoveFlockModel(Context context) {
       this(context,true,0,0,0,0,1);
   }

    /**
     * AndroidDoveFlockModel constructor
     * 
     * @param context is the activity context
     * @param initIsLeft determines if the dove faces left
     * @param initX is the dove's top left x coordinate
     * @param initY is the dove's top left y coordinate
     * @param initXOff is the dove's x offset speed
     * @param initYOff is the dove's y offset speed
     * @param ratio is the ratio of the dove's size compared to the default
     */ 
   public AndroidDoveFlockModel(Context context, 
                          boolean initIsLeft, int initX, int initY,
                                int initXOff, int initYOff, double ratio) {
      super(context,initIsLeft, initX, initY, initXOff, initYOff, ratio);

      // Setting the flocking constants
      this.FLOCKRADIUS = (int)(this.DEFRADIUS * ratio);
      this.TOOCLOSE = Math.min(getWidth(),getHeight()) * (3.0 / 4.0);

      // Setting radius drawing objects
      this.redPaint = new Paint();
      this.redPaint.setARGB(255, 255, 0, 0);
      this.redPaint.setAntiAlias(true);
      this.redPaint.setStyle(Style.STROKE);
      this.redPaint.setStrokeWidth(1);
      this.oval = new RectF(0,0,0,0);
   }

    /** 
     * Moves the dove using the flocking algorithm
     * 
     * @param index is the current dove index in the doves array 
     * @param doves is the doves array
     * @param scrWidth is the screen width
     * @param scrHeight is the screen height
     */
   public void move(ArrayList<AndroidDoveFlockModel> doves, int scrWidth, int scrHeight) {
      double xMov1 = 0, yMov1 = 0;   // stores rule 1 movement factors
      double xMov2 = 0, yMov2 = 0;   // stores rule 2 movementfactors
      double xMov3 = 0, yMov3 = 0;   // stores rule 3 movementfactors
      int count = 0;                 // count of other doves in local flock

      // Rule #1: Separation - move away from any doves that are too close
      for (AndroidDoveFlockModel dove :  doves)
         if (dove != this && distance(dove) < TOOCLOSE) {
            xMov1 -= (dove.getX() - getX()) / 5;
            yMov1 -= (dove.getY() - getY()) / 5;
         }

      // Rule #2: Alignment try to match average velocity vectors of flock
      for (AndroidDoveFlockModel dove :  doves)
         if (dove != this && distance(dove) < FLOCKRADIUS) {
            xMov2 += dove.getXVelocity();
            yMov2 += dove.getYVelocity();
            ++count;
         }
      if (count > 0) {  // get 1/10th diff between current and avg velocity
         xMov2 = (xMov2/count - getXVelocity()) / 10;
         yMov2 = (yMov2/count - getYVelocity()) / 10;
      }

      // Rule #3: Cohesion - move toward the center of the flock
      for (AndroidDoveFlockModel dove : doves)
         if (dove != this && distance(dove) < FLOCKRADIUS) {
            xMov3 += dove.getX();
            yMov3 += dove.getY();
         }
      if (count > 0) {  // Get 1/100th diff between current and avg position
         xMov3 = ((xMov3+getX())/(count+1) - getX()) / 100;
         yMov3 = ((yMov3+getY())/(count+1) - getY()) / 100;
      }

      // Keep the dove from going too fast
      if (Math.abs(getXVelocity()) > MAXSPEED)
         setXVelocity((getXVelocity() / Math.abs(getXVelocity())) * MAXSPEED);
      if (Math.abs(getYVelocity()) > MAXSPEED)
         setYVelocity((getYVelocity() / Math.abs(getYVelocity())) * MAXSPEED);

      // Re-adjust dove velocity factoring in Rules 1 and 2
      setXVelocity(getXVelocity() + xMov1 + xMov2);
      setYVelocity(getYVelocity() + yMov1 + yMov2);

      // Move the dove a fraction closer to center factoring in Rule 3
      move(getXVelocity() + xMov3, getYVelocity() + yMov3);

      // Rules for rebounding when the Dove hits a BOUNDARY
      if (getX() < BOUNDARY)
         setXVelocity(REBOUNDSPEED);
      if (getX() > scrWidth - BOUNDARY - getWidth())
         setXVelocity(-REBOUNDSPEED);
      if (getY() < BOUNDARY)
         setYVelocity(REBOUNDSPEED);
      if (getY() > scrHeight - BOUNDARY - getHeight())
         setYVelocity(-REBOUNDSPEED);
   }

    /** 
     * Draws the dove on the given page
     * 
     * @param page is the graphics page
     * @param showFlockRadius determines if the flock radius is shown
     */
   public void draw(Canvas canvas, boolean showFlockRadius) {
      if (showFlockRadius) {
          int xMid = (int)(getX() + getWidth()/2);
          int yMid = (int)(getY() + getHeight()/2);
          oval.set((int)(xMid - FLOCKRADIUS),(int)(yMid - FLOCKRADIUS),
                   (int)(xMid + FLOCKRADIUS),(int)(yMid + FLOCKRADIUS));
          canvas.drawOval(oval,redPaint);
      }
      super.draw(canvas);
   }

    /** 
     * Gets the distance to another Dove's coordinates
     * 
     * @param otherDove is the other dove
     * @return Returns the distance to the other dove
     */
   public double distance(AndroidDoveFlockModel otherDove) {
      return (Math.sqrt(Math.pow(otherDove.getX()-getX(),2) +
                           Math.pow(otherDove.getY()-getY(),2)));
   }
}