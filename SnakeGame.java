import javax.swing.*;
import java.awt.*;
//import java.util.*;
import java.awt.event.*;

public class SnakeGame extends JFrame
{
  SnakeGame()
  {
    super("Snake");
    setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    setSize(1000,1000);
    setUndecorated(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocation(0,0);
    setResizable(true);
    Game g = new Game((int)getToolkit().getScreenSize().getWidth() - 15, (int)getToolkit().getScreenSize().getHeight() - 10);
    setContentPane(g);
    setVisible(true);
  }
  
  public static void main(String[]args)
  {
    SnakeGame s = new SnakeGame();
  }
}