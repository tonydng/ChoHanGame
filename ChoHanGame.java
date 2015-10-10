//package group26;
/**
 * SER 215 ASU
 * Final Project
 * File: ChoHanGame.java
 * 
 * Cho Han Game: In this game, you will place a bet on whether the sum 
 * of the dice rolled will be even or odd. You start with a balance of $500, 
 * and will win the game once your balance reaches $1000. 
 * If your balance reaches $0, you lose with $0.00 bonuses. 
 * For every successful consecutive win, you will earn a bonus amount of $1.
 * 
 * @authors Robert McKinney, Heath Rackham, Tomas Vartija, and Tony Nguyen
 * @version 1
 */
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.text.DecimalFormat;

public class ChoHanGame
{
  public static final double MIN_MONEY = 0;
  public static final double MAX_MONEY = 1000;
  public static final double INITIAL_MONEY = 500;
  private double yourMoney = INITIAL_MONEY;

  private double yourBet = 0;
  private int numOfGuesses = 0;

  private boolean quit = false;

  DecimalFormat d_format = new DecimalFormat("#.##");

  private ArrayList<Boolean> consecutiveWin = new ArrayList<Boolean>();
  
  private JLabel showMoney = new JLabel("", JLabel.CENTER);
  private JLabel betDisplay;  // to display the number entered
  
  private JLabel gameInfo = new JLabel("", JLabel.CENTER);
  private JLabel bonusDisplay = new JLabel("", JLabel.CENTER);
  //private JLabel[] diceDisplay = new JLabel[TOTAL_DICE]; 
  private JLabel diceResults = new JLabel("", JLabel.CENTER);
  
  private JButton betButton = new JButton("Choose Bet");
  private JButton oddButton = new JButton("Odd");
  private JButton evenButton = new JButton("Even");
  private JButton newGameButton = new JButton("New Game");
  private JButton quitButton = new JButton("Quit Game");

  private ChoHanDice choHanDice = new ChoHanDice();

  private int getNumOfGuesses() { return numOfGuesses; }
  
  /**
   * 
   * @return bonus money
   */
  public double getBonuses() {
    double result = 0;
    int count;
    int k;

    for (int i = 0; i < consecutiveWin.size(); i++) {
      count = 0;
      k = i;
      while (k < consecutiveWin.size() && consecutiveWin.get(k).equals(true)) {
        count++;
        k++;
      }
      if (count >= 2) 
        for (int j = 2; j <= count; j++)
          result += 1; // add $1 bonus to consecutive win >=2
    }
    return result;
  }
  
  /**
   * 
   * @return String shows up on gamInfo JLabel
   */
  private String setGameInfo() {
    String result = "";
      if (!quit) {
        if (yourMoney > MIN_MONEY && yourMoney < MAX_MONEY) {
          result = "<html>" +
                "You're about to play a game of Cho Han! In this game, you will place a <br>" +
              "bet on whether the sum of the dice rolled will be even or odd. You start <br>" +
              "with a balance of $500, and will win the game once your balance reaches <br>" + 
              "$1000. If your balance reaches $0, you lose with $0.00 bonuses. For every <br>" +
              "successful consecutive win, you will earn a bonus amount of $1. <br>" + 
              "Please place your bet! </html>";
        
        } 
        else if (yourMoney <= MIN_MONEY) {
          result = "<html>You lost the game in " + getNumOfGuesses() + " bet(s).<br>" +
                  "</html>"; 

        } else {
          result = "<html> Congratuations! You won the game in " + getNumOfGuesses() + 
              " bet(s)!<br>Your balance is $" + d_format.format(yourMoney) + "<br>" +
                  "Your consecutive win bonus is $" + getBonuses() + "</html>";
        }
      } else {
        result = "<html> Congratuations! You left the game after " + getNumOfGuesses() + 
            " bet(s)!<br>Your balance is $" + d_format.format(yourMoney) + "<br>" +
                "Your consecutive win bonus is $" + getBonuses() + "</html>";
      }

      return result;
  }
  
  /**
   * 
   * @param validBalance, a double parameter
   * @return a valid input from user
   */
  private double getInput(double validBalance) {
    boolean validInput = false; // for input validation
    double result;
    String inputStr = 
            JOptionPane.showInputDialog("Enter a number [0 - " +
              yourMoney + "]");
    do 
    {
      try {
          result = Double.parseDouble(inputStr);
      } catch (NumberFormatException ex) {
          result = -1;
      }
      if (result < MIN_MONEY|| result > validBalance) {
          inputStr = 
                  JOptionPane.showInputDialog("Enter a number [" + 
                          MIN_MONEY + " - " + d_format.format(validBalance) + "]");
      } else {
          JOptionPane.showMessageDialog(null, "You have entered " + 
                  result);
          validInput = true;
      } 
    } while (!validInput);
    return result;
  }
  
  /**
   * 
   * @param yourMoney, a double parameter
   * verifies that the game over or not based on yourMoney
   */
  private void gameValidate(double yourMoney) {
    if (yourMoney <= MIN_MONEY) {
      betDisplay.setText("GAME OVER");
      betDisplay.setForeground(Color.RED);
      betDisplay.setOpaque(true);
      betDisplay.setBackground(Color.WHITE);
      betButton.setEnabled(false);
      oddButton.setEnabled(false);
      evenButton.setEnabled(false);
      newGameButton.setEnabled(true);
      
      // reset bonus money to zero, when lose
      consecutiveWin = new ArrayList<Boolean>();
      bonusDisplay.setText("Your bonus is: $" + getBonuses());

      // update game infomation 
      gameInfo.setText(setGameInfo());
    }
    else if (yourMoney >= MAX_MONEY) {
      betDisplay.setText("YOU ARE THE WINNER");
      betDisplay.setForeground(Color.GREEN);
      betDisplay.setOpaque(true);
      betDisplay.setBackground(Color.BLACK);
      betButton.setEnabled(false);
      oddButton.setEnabled(false);
      evenButton.setEnabled(false);
      newGameButton.setEnabled(true);

      // update game infomation
      gameInfo.setText(setGameInfo());

    } else {
      newGameButton.setEnabled(false);
    }
  }

  /**
   * 
   * verifies that betMoney can't be bigger that current balance, yourMoney
   */
  private void balanceValidate () {
    while (yourBet > yourMoney) {
      JOptionPane.showMessageDialog(null, "Can't bet more than balance " + 
              yourMoney);
      yourBet = yourMoney;
      betDisplay.setText("Your bet is as most as $" + yourBet);

      // reset your bet
      yourBet = 0;
      yourBet = getInput(yourMoney);
    }
  }
  
  /**
   * 
   * @param x
   * @param y
   * @return blankLabel that adds more spaces 
   * between labels or buttons
   */
  private JLabel blankLabel(int x, int y) {
    JLabel blankLabel = new JLabel("", JLabel.CENTER);
    blankLabel.setPreferredSize(new Dimension(x, y));
    return blankLabel;
  }
    
  private void displayGUI()
  {
    // Initialize layout manager
    JFrame frame = new JFrame("Cho-Han Game");
    JPanel contentPane = new JPanel();
    contentPane.setOpaque(true);
    contentPane.setBackground(Color.CYAN);
    frame.setContentPane(contentPane);
    frame.setSize(745, 815);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationByPlatform(true);
    frame.setVisible(true);

    // initialize starting balance on showMoney JLabel
    showMoney.setForeground(Color.WHITE);
    showMoney.setPreferredSize(new Dimension(200, 30));
    showMoney.setOpaque(true);
    showMoney.setBackground(Color.BLACK);
    showMoney.setText("Your balance: $" + d_format.format(yourMoney));
    contentPane.add(showMoney, BorderLayout.PAGE_START);
    
    // add a blank label to move showMoney JLabel to the left
    contentPane.add(blankLabel(525, 30), BorderLayout.PAGE_START);
    
    // initialize bonusDisplay JLabel
    bonusDisplay.setForeground(Color.RED);
    bonusDisplay.setPreferredSize(new Dimension(200, 30));
    bonusDisplay.setOpaque(true);
    bonusDisplay.setBackground(Color.BLACK);
    bonusDisplay.setText("Your bonus is: $" + 0);
    contentPane.add(bonusDisplay, BorderLayout.PAGE_START);
    
    // add a blank label to move bonusDisplay JLabel to the left
    contentPane.add(blankLabel(525, 30), BorderLayout.PAGE_START);

    // add blank label to separate bonusDislay Jlabel with below components
    contentPane.add(blankLabel(700, 50), BorderLayout.CENTER);
    
    // Initialize diceResults JLabel
    diceResults.setText("Make a bet, Then choose \"Odd\" or \"Even\"");
    diceResults.setPreferredSize(new Dimension(300, 80));
    diceResults.setForeground(Color.WHITE);
    diceResults.setOpaque(true);
    diceResults.setBackground(Color.BLACK);
    contentPane.add(diceResults, BorderLayout.CENTER);
    
    // add blank label to separate diceResults Jlabel with below components
    contentPane.add(blankLabel(700, 30), BorderLayout.CENTER);
    
    // add dice GUI to contentPane
    Die[] dice = choHanDice.getDice();
    
    for (Die die : dice) {
      contentPane.add(die, BorderLayout.LINE_START);
      contentPane.add(blankLabel(5, 60));
    }
    
    contentPane.add(blankLabel(700, 30), BorderLayout.CENTER);
    
    // Initialize gameInfo JLabel
    gameInfo.setText(setGameInfo());
    gameInfo.setForeground(Color.WHITE);
    gameInfo.setPreferredSize(new Dimension(545, 120));
    gameInfo.setOpaque(true);
    gameInfo.setBackground(Color.BLACK);
    contentPane.add(gameInfo, BorderLayout.CENTER);
    
     // add blank label to separate gameInfo Jlabel with below components
    contentPane.add(blankLabel(700, 30), BorderLayout.CENTER);
    
    // Initialize betDispaly JLabel
    betDisplay = new JLabel("", JLabel.CENTER);
    betDisplay.setText("Your bet is $" + yourBet);
    betDisplay.setPreferredSize(new Dimension(280, 40));
    betDisplay.setForeground(Color.WHITE);
    betDisplay.setOpaque(true);
    betDisplay.setBackground(Color.BLACK);
    contentPane.add(betDisplay, BorderLayout.CENTER);
    
     // add blank label to separate betDisplay Jlabel with below components
    contentPane.add(blankLabel(700, 30), BorderLayout.CENTER);
    // add a blank label to move oddButton to the left
    contentPane.add(blankLabel(140, 40), BorderLayout.CENTER);

    oddButton.setPreferredSize(new Dimension(80, 40));
    //oddButton.setBackground(null);
    oddButton.setEnabled(false);
    contentPane.add(oddButton, BorderLayout.LINE_START);
    // Register listener for oddButton
    oddButton.addActionListener(new OddButtonListener());

    // add a blank label to separate oddButton and evenButton
    contentPane.add(blankLabel(150, 40), BorderLayout.CENTER);   
    evenButton.setPreferredSize(new Dimension(80, 40));
    evenButton.setEnabled(false);
    contentPane.add(evenButton);
    // Register listener for evenButton
    evenButton.addActionListener(new EvenButtonListener());
    
     // add a blank label to separate evenButton and betButton
    contentPane.add(blankLabel(150, 40), BorderLayout.CENTER);
    betButton.setPreferredSize(new Dimension(140, 40));
    contentPane.add(betButton); 
    // Register listener for betButton
    betButton.addActionListener(new BetButtonListener());
    
    // add blank label to separate odd/even/bet buttons with below components
    contentPane.add(blankLabel(700, 5), BorderLayout.CENTER);
    
    // add a blank label to move newGameButton to the left
    newGameButton.setPreferredSize(new Dimension(140, 40));
    contentPane.add(newGameButton);
    // Register listener for newGameButton
    newGameButton.addActionListener(new NewGameButtonListener()); 

    quitButton.setPreferredSize(new Dimension(140, 40));
    contentPane.add(quitButton);
    // Register listener for quitButton
    quitButton.addActionListener(new QuitButtonListener());
  }

  private class OddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      balanceValidate();

      // update random number for next guess
      choHanDice.rollDice();

      betDisplay.setText("Your bet is $" + yourBet);
      
      if (choHanDice.sumOfDice() % 2 == 1) {
          consecutiveWin.add(true);
          yourMoney += yourBet;
          diceResults.setText("Dice Total: " + choHanDice.sumOfDice() + "... You Win!");
          diceResults.setForeground(Color.GREEN);
      } else {
          consecutiveWin.add(false);
          yourMoney -= yourBet;
          diceResults.setText("Dice Total: " + choHanDice.sumOfDice() + "... You Lose!");
          diceResults.setForeground(Color.RED);      
      }

      // update bonus money
      bonusDisplay.setText("Your bonus is: $" + getBonuses());

      // update numOfGuesses
      numOfGuesses++;

      // check for game over
      gameValidate(yourMoney);

      // update balance
      showMoney.setText("Your balance: $" + d_format.format(yourMoney));
    }
  }

  private class EvenButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        
      balanceValidate();

      // update random number for next guess
      choHanDice.rollDice();

      betDisplay.setText("Your bet is $" + yourBet);

      if (choHanDice.sumOfDice() % 2 == 0) {
        consecutiveWin.add(true);
        yourMoney += yourBet;
        diceResults.setText("Dice Total: " + choHanDice.sumOfDice() + "... You Win!");
        diceResults.setForeground(Color.GREEN);
      } else {
        consecutiveWin.add(false);
        yourMoney -= yourBet;
        diceResults.setText("Dice Total: " + choHanDice.sumOfDice() + "... You Lose!");
        diceResults.setForeground(Color.RED);
      }

      // update bonus money
      bonusDisplay.setText("Your bonus is: $" + getBonuses());  

      // update numOfGuesses
      numOfGuesses++;

      // check for game over
      gameValidate(yourMoney);

      // update balance
      showMoney.setText("Your balance: $" + d_format.format(yourMoney));
    }
  }

  private class BetButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      oddButton.setEnabled(true);
      evenButton.setEnabled(true);
      yourBet = getInput(yourMoney);
      betDisplay.setText("Your bet is $" + yourBet);
    }
  }

  private class NewGameButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      yourMoney = INITIAL_MONEY;
      yourBet = 0;
      quit = false;

      // enable all buttons
      betButton.setEnabled(true);

      // update balance
      showMoney.setText("Your balance: $" + d_format.format(yourMoney));
      
      // update bet display
      betDisplay.setText("Your bet is $" + yourBet);
      betDisplay.setForeground(Color.WHITE);
      betDisplay.setOpaque(true);
      betDisplay.setBackground(Color.BLACK);

      // reset bonus money to zero, when playing new game
      consecutiveWin = new ArrayList<Boolean>();
      bonusDisplay.setText("Your bonus is: $" + getBonuses());

      // reset numOfGuesses to zero
      numOfGuesses = 0;

      // reset quit 
      quit = false;

      // reset gameInfo JLabel
      gameInfo.setText(setGameInfo());
    }
  }

  private class QuitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      quit = true;
      oddButton.setEnabled(false);
      evenButton.setEnabled(false);
      betButton.setEnabled(false);
      newGameButton.setEnabled(true);

      // reset gameInfo JLabel if quit
      gameInfo.setText(setGameInfo());
    }
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
          new ChoHanGame().displayGUI();
      }
    });
  }
}