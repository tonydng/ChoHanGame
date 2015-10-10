//package group26;

/**
 * SER 215 ASU
 * Final Project
 * File: ChoHanDice.java
 * 
 * Cho Han Die: contains an array of 6 dies that is used to 
 * create sume of values of 6 dies
 * and roll 6 dies for the main game
 * 
 * @authors Robert McKinney, Heath Rackham, Tomas Vartija, and Tony Nguyen
 * @version 1
 */
public class ChoHanDice
{
  int TOTAL_DICE = 6;
  Die[] dice;

  public ChoHanDice() {
    dice = new Die[TOTAL_DICE];
    for(int i=0; i<TOTAL_DICE; i++) {
      dice[i] = new Die();
    }
  }
  
  public Die[] getDice() {
    return dice;
  }

  // Roll all the dice
  public void rollDice() {
    for(Die die : dice) {
      die.roll();
      die.repaint();
    }
  }

  public int sumOfDice() {
    int sum = 0;
    for(Die die : dice) {
      sum += die.getFaceValue();
    }
    return sum;
  }
}
