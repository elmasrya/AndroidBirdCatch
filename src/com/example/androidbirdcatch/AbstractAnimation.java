/*Course: CS2302
 *Section: 01
 *Name: Andrew El-Masry
 *Professor: Dr Shaw
 *Assignment #: Lab 13
 */

package com.example.androidbirdcatch;
/**
 * The AbstractAnimation Class - 
 * Supports creating animated objects
 */
public abstract class AbstractAnimation {
   public final int IMAGEAMT;    // The max image index per direction
   
   private boolean isRight;      // Determines if animation faces right
   private int imageIndex = 0;   // The animation's image index
   private int width = 0;        // The animation's width
   private int height = 0;       // The animation's height
   private double x = 0;         // The animation's x coordinate
   private double y = 0;         // The animation's y coordinate
   private double dX = 0;        // The animation's x velocity
   private double dY = 0;        // The animation's y velocity

    /**
     * AbstractAnimation constructor
     * 
     * @param IMAGEAMT is the maximum number of images
     * @param width is the width of the animation images
     * @param height is the height of the animation images
     * @param isRight determines if the animation faces right
     * @param x is the animation's top left x coordinate
     * @param y is the animation's top left y coordinate
     * @param xVelocity is the animation's x velocity
     * @param yVelocity is the animation's y velocity
     */ 
   public AbstractAnimation(int IMAGEAMT, boolean isRight, 
                             int width, int height, int x, int y, 
                              int xVelocity, int yVelocity) {
      this(IMAGEAMT,isRight,x,y,xVelocity,yVelocity);
      setWidth(width);
      setHeight(height);
   }
   
   /**
    * AbstractAnimation constructor
    * 
    * @param IMAGEAMT is the maximum number of images
    * @param isRight determines if the animation faces right
    * @param x is the animation's top left x coordinate
    * @param y is the animation's top left y coordinate
    * @param xVelocity is the animation's x velocity
    * @param yVelocity is the animation's y velocity
    */ 
   public AbstractAnimation(int IMAGEAMT, boolean isRight, int x, int y, 
                             int xVelocity, int yVelocity) {
      this.IMAGEAMT = IMAGEAMT;
      this.imageIndex = 0;
      setIsRight(isRight);
      setX(x);
      setY(y);
      setXVelocity(xVelocity);
      setYVelocity(yVelocity);
   }

    /**
     * Gets the top left corner x coordinate
     * 
     * @return Returns the top left corner x coordinate
     */ 
   public double getX() {
      return this.x;
   }

    /**
     * Gets the top left corner y coordinate
     * 
     * @return Returns the top left corner y coordinate
     */ 
   public double getY() {
      return this.y;
   }

    /**
     * Sets the top left corner x coordinate
     * 
     * @param x is the new top left corner x coordinate
     */
   public void setX(double x) {
      this.x = x;
   }

    /**
     * Sets the top left corner y coordinate
     * 
     * @param y is the new top left corner y coordinate
     */
   public void setY(double y) {
      this.y = y;
   }

    /**
     * Gets the X velocity value
     * 
     * @return Returns the X velocity value
     */ 
   public double getXVelocity() {
      return this.dX;
   }

    /**
     * Gets the Y velocity value
     * 
     * @return Returns the Y velocity value
     */ 
   public double getYVelocity() {
      return this.dY;
   }

    /** 
     * Sets the x velocity value
     * 
     * @param velocity is the new x velocity value
     */
   public void setXVelocity(double velocity) {
      this.dX = velocity;
   }

    /** 
     * Sets the y velocity value
     * 
     * @param velocity is the new y velocity value
     */
   public void setYVelocity(double velocity) {
      this.dY = velocity;
   }

    /**
     * Gets the animation's width
     * 
     * @return Returns the width
     */
   public int getWidth() {
      return width;
   }

    /**
     * Gets the animation's height
     * 
     * @return Returns the height
     */
   public int getHeight() {
      return height;
   }

    /**
     * Sets the animation's width
     * 
     * @param width is the new width value
     */
   public void setWidth(int width) {
      this.width = width;
   }

    /**
     * Sets the animation's height
     * 
     * @param height is the new height value
     */
   public void setHeight(int height) {
      this.height = height;
   }

    /**
     * Gets the isRight field
     * 
     * @return Returns the isRight value
     */
   public boolean isRight() {
      return this.isRight;
   }

    /**
     * Sets the isRight field
     * 
     * @param isRight is the new isRight value
     */
   public void setIsRight(boolean isRight) {
      this.isRight = isRight;
   }

    /**
     * Moves the animation left by the offset field
     */
   public void moveLeft() {
      setX(getX() - Math.abs(getXVelocity()));
      setIsRight(false);
   }

    /**
     * Moves the animation left by the offset field
     *
     * @param boundary is the left boundary of the window
     */
   public void moveLeft(int boundary) {
      moveLeft();
      if (getX() < boundary)   // Test for boundary on left edge
         setX(boundary);       // If so, set to boundary
   }

    /**
     * Moves the animation right by the offset field
     */
   public void moveRight() {
      setX(getX() + Math.abs(getXVelocity()));
      setIsRight(true);
   }

    /**
     * Moves the animation right by the offset field
     * 
     * @param width is the width of the window
     */
   public void moveRight(int width) {
      moveRight();
      if (getX() > width - getWidth())  // Test for boundary on right edge
         setX(width - getWidth());      // If so, set to boundary
   }

    /**
     * Moves the animation up by the offset field
     */
   public void moveUp() {
      setY(getY() - Math.abs(getYVelocity()));
   }

    /**
     * Moves the animation up by the offset field
     *
     * @param boundary is the upper boundary of the window
     */
   public void moveUp(int boundary) {
      moveUp();
      if (getY() < boundary)   // Test for boundary above
         setY(boundary);       // If so, set to boundary
   }

    /**
     * Moves the animation down by the offset field
     */
   public void moveDown() {
      setY(getY() + Math.abs(getYVelocity()));
   }

    /**
     * Moves the animation down by the offset field
     * 
     * @param height is the height of the window
     */
   public void moveDown(int height) {
      moveDown();
      if (getY() > height - getHeight())   // Test for boundary below
         setY(height - getHeight());       // If so, set to boundary
   }

    /**
     * Moves the animation by dX and dY
     */
   public void move() {
      move(getXVelocity(),getYVelocity());
   }

    /**
     * Moves the animation by dX and dY within the given boundaries
     * 
     * @param left is the left boundary of the window
     * @param top is the top boundary of the window
     * @param right is the right boundary of the window
     * @param bottom is the bottom boundary of the window
     */
   public void move(int left, int top, int right, int bottom) {
      move(getXVelocity(),getYVelocity());
      if (getX() < left)                  // Test for boundary on left
          setX(left);                     // If so, set to boundary
      if (getX() > right - getWidth())    // Test for boundary on right edge
          setX(right - getWidth());       // If so, set to boundary
      if (getY() < top)                   // Test for boundary above
          setY(top);                      // If so, set to boundary
      if (getY() > bottom - getHeight())  // Test for boundary below
          setY(bottom - getHeight());     // If so, set to boundary
   }

    /**
     * Moves the animation by dX and dY within the given boundaries,
     * rebounding off of the boundaries when connecting
     * 
     * @param left is the left boundary of the window
     * @param top is the top boundary of the window
     * @param right is the right boundary of the window
     * @param bottom is the bottom boundary of the window
     */
   public void moveRebound(int left, int top, int right, int bottom) {
      move(getXVelocity(),getYVelocity());
      if (getX() < left) {                 // Test for boundary on left
         setX(left);                      // If so, set to boundary
         setXVelocity(-getXVelocity());   //   and change x direction
      }
      if (getX() > right - getWidth()) {   // Test for boundary on right edge
         setX(right - getWidth());        // If so, set to boundary
         setXVelocity(-getXVelocity());   //   and change x direction
      }
      if (getY() < top) {                  // Test for boundary above
         setY(top);                       // If so, set to boundary
         setYVelocity(-getYVelocity());   //   and change y direction
      }
      if (getY() > bottom - getHeight()) { // Test for boundary below
         setY(bottom - getHeight());      // If so, set to boundary
         setYVelocity(-getYVelocity());   //   and change y direction
      }
   }

    /**
     * Moves the animation by the given offsets
     * 
     * @param xOff is the x offset
     * @param yOff is the y offset
     */
   public void move(double xOff, double yOff) {
      this.x += xOff;
      this.y += yOff;

      if (xOff > 0)
         setIsRight(true);    // set animation's direction to right
      else if (xOff < 0)
         setIsRight(false);   // set animation's direction to left
      nextImage();
   }

    /**
     * Increments the image index
     */
   public void nextImage() {
      if (++this.imageIndex >= IMAGEAMT)
         this.imageIndex = 0;
   }

    /**
     * Sets the image index to a specific index
     * 
     * @param index is the new image index
     */
   public void setImageIndex(int index) {     
      if (index < IMAGEAMT)
         this.imageIndex = index;
   }

    /**
     * Gets the image index
     * 
     * @return Returns the image index
     */
   public int getImageIndex() {     
      return this.imageIndex;
   }

    /**
     * The toString method for the AbstractAnimation class
     * 
     * @return Returns the direction, position and movement offsets
     */
   public String toString() {
      return "direction=" + (isRight() ? "Right" : "Left") + ", " + 
               "position=(" + (int)getX() + "," + (int)getY() + "), " +
               "velocity vectors=(" + (int)getXVelocity() + "," + 
                             (int)getYVelocity() + ")";
   }

    /**
     * Draws the animation object
     */
   protected abstract void draw(Object gObject);
}