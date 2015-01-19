package com.example.androidbirdcatch;
/*Course: CS2302
 *Section: 01
 *Name: Andrew El-Masry
 *Professor: Dr Shaw
 *Assignment #: Lab 13
 */
/**
 * The AndroidDove Class - 
 * An animated Android Dove object
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class AndroidDove extends AbstractAnimation {
   public static final int IMAGEAMT = 8;      // The number of dove images
   private Bitmap [] doveLeft = new Bitmap[IMAGEAMT];   // The left images
   private Bitmap [] doveRight = new Bitmap[IMAGEAMT];  // The right images

   // Fields for drawing bitmaps
   private Paint paint = new Paint();
   private Rect scaleRect = new Rect(0,0,0,0);

    /**
     * AndroidDove one-arg constructor. Dove is facing right at
     * the top left position of the window with no velocity.
     *
     * @param context is the activity context
     */ 
   public AndroidDove(Context context) {
      this(context,true,0,0,0,0);
   }

    /**
     * AndroidDove constructor. Dove is facing a given direction
     * at position (x,y) with a given velocity.
     * 
     * @param context is the activity context
     * @param isRight determines if the dove faces right
     * @param x is the dove's top left x coordinate
     * @param y is the dove's top left y coordinate
     * @param xVelocity is the dove's x velocity
     * @param yVelocity is the dove's y velocity
     */
   public AndroidDove(Context context, boolean isRight,
                       int x, int y, int xVelocity, int yVelocity) {
      this(context,isRight,x,y,xVelocity,yVelocity,1.0);
   }

    /**
     * AndroidDove constructor. Dove is facing a given direction
     * at position (x,y) with a given velocity with a given
     * image size scale.
     * 
     * @param context is the activity context
     * @param isRight determines if the dove faces right
     * @param x is the dove's top left x coordinate
     * @param y is the dove's top left y coordinate
     * @param xVelocity is the dove's x velocity
     * @param yVelocity is the dove's y velocity
     * @param scale is the image size scale
     */
   public AndroidDove(Context context, boolean isRight,
              int x, int y, int xVelocity, int yVelocity, double scale) {
      super(IMAGEAMT,isRight,x,y,xVelocity,yVelocity);
      
      loadImages(context);
      if (scale > 0.9 && scale < 1.1) {
         setWidth(doveRight[0].getWidth());
         setHeight(doveRight[0].getHeight());
      } else {
         setWidth((int) (doveRight[0].getWidth() * scale));
         setHeight((int) (doveRight[0].getHeight() * scale));
      }
   }

    /**
     * AndroidDove constructor. Dove is facing a given direction
     * with a given width and height, at position (x,y) with
     * a given velocity.
     * 
     * @param context is the activity context
     * @param isRight determines if the dove faces right
     * @param width is the dove's width
     * @param height is the dove's height
     * @param x is the dove's top left x coordinate
     * @param y is the dove's top left y coordinate
     * @param xVelocity is the dove's x velocity
     * @param yVelocity is the dove's y velocity
     */
   public AndroidDove(Context context, boolean isRight,
                       int width, int height, int x, int y,
                        int xVelocity, int yVelocity) {
      super(IMAGEAMT,isRight,width,height,x,y,xVelocity,yVelocity);
      loadImages(context);
   }

    /**
     * Gets the image width
     * 
     * @return Returns the image width
     */
   public int imageWidth() {
      return getWidth();
   }

    /**
     * Gets the image height
     * 
     * @return Returns the image height
     */
   public int imageHeight() {
      return getHeight();
   }

    /**
     * Gets the current image
     * 
     * @return Returns the current image
     */
   public Bitmap getCurrentImage() {
      return isRight() ? doveRight[getImageIndex()] : 
                           doveLeft[getImageIndex()];
   }

    /**
     * Draws the dove on the given page
     * 
     * @param page is the graphics page
     */
   public void draw(Object gObject) {
      Canvas canvas = (Canvas) gObject;
      scaleRect.set((int)getX(), (int)getY(),
                     (int)getX()+getWidth(),(int)getY()+getHeight());
      canvas.drawBitmap(getCurrentImage(),null,scaleRect,paint);
   }

    /**
     * The toString method for the AndroidDove class
     * 
     * @return Returns the dove's direction, position and movement offsets
     */
   public String toString() {
      return "Android Dove " + super.toString();
   }

   // loads the dove images
   private void loadImages(Context context) {
      for (int i = 0; i < IMAGEAMT; ++i) {
         if (doveLeft[i] != null)
            doveLeft[i].recycle();
         if (doveRight[i] != null)
            doveRight[i].recycle();
      }

      int imgID;
      Resources res = context.getResources();
      String pkgName = context.getPackageName();
      for (int i = 0; i < IMAGEAMT; ++i) {
         imgID = res.getIdentifier("ldove"+(i+1),"drawable",pkgName);
         doveLeft[i] = BitmapFactory.decodeResource(res,imgID);
         imgID = res.getIdentifier("rdove"+(i+1),"drawable",pkgName);
         doveRight[i] = BitmapFactory.decodeResource(res,imgID);
      }
   }
}