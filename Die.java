//package group26;

import java.awt.*;
import javax.swing.*;

/**
 * SER 215 ASU
 * Final Project
 * File: Die.java
 * 
 * @authors Robert McKinney, Heath Rackham, Tomas Vartija, and Tony Nguyen
 * @version 1
 */

public class Die extends JComponent
{
  private static final int SPOT_DIAMETER = 10;
  private static final int DIE_SIZE = 60;
  private final int MAX = 6; // maximum face value
  private int faceValue; // current value showing on the die

  //------------------------------------------------------------------
  // Constructor: Sets the initial face value of this die.
  //------------------------------------------------------------------
  public Die()
  {
    faceValue = roll();
    setPreferredSize(new Dimension(DIE_SIZE, DIE_SIZE));
  }
  //------------------------------------------------------------------
  // Computes a new face value for this die and returns the result.
  //------------------------------------------------------------------
  public int roll()
  {
    faceValue = (int)(Math.random() * MAX) + 1;
    return faceValue;
  }
  /**
  * Sets the value of the Die that causes repaint(). 
  * The face value is not modified if the
  * specified value is not valid.
  * @param spots Number from 1 to 6
  */
  public void setFaceValue(int spots)
  {
    if (spots < 0 && spots <= MAX) {
      faceValue = spots;
      repaint(); // value has changed, must repaint.
    }
  }
  //-----------------------------------------------------------------
  // Face value accessor.
  //-----------------------------------------------------------------
  public int getFaceValue()
  {
    return faceValue;
  }

  /** Draws spots of die face. */
  @Override
  public void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    // Change to Graphic2D for smoother spots
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);

    // paint the background of die
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, width, height);

    // change background color to black for painting spots
    g2.setColor(Color.BLACK);

    // Draw border of die
    g2.drawRect(0, 0, width - 1, height - 1);

    switch(faceValue) {
      case 1:
        drawSpot(g2, width / 2, height / 2);
        break;
      case 3:
        drawSpot(g2, width / 2, height / 2);
        // Drop down to the next case
      case 2:
        drawSpot(g2, width / 4, height / 4);
        drawSpot(g2, 3 * width / 4, 3 * height / 4);
        break;
      case 5:
        drawSpot(g2, width / 2, height / 2);
        // Drop down to the next case
      case 4:
        drawSpot(g2, width / 4, height / 4);
        drawSpot(g2, 3 * width / 4, 3 * height / 4);
        drawSpot(g2, 3 * width / 4, height / 4);
        drawSpot(g2, width / 4, 3 * height / 4);
        break;
      case 6:
        drawSpot(g2, width / 4, height / 4);
        drawSpot(g2, 3 * width / 4, 3 * height / 4);
        drawSpot(g2, 3 * width / 4, height / 4);
        drawSpot(g2, width / 4, 3 * height / 4);
        drawSpot(g2, width / 4, height / 2);
        drawSpot(g2, 3 * width / 4, height / 2);
        break;
    }
  }
  // paint single spot
  private void drawSpot(Graphics2D g2, int x, int y) {
    g2.fillOval(x - SPOT_DIAMETER / 2, y - SPOT_DIAMETER / 2, 
        SPOT_DIAMETER, SPOT_DIAMETER);
  }
}