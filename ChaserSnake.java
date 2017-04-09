import java.awt.*;
import javax.swing.*;

public class ChaserSnake extends Snake
{
//  int n;
//  int[] x = new int[45];
//  int[] y = new int[45];
//  int width, height = 0;
//  boolean start = true;
//  boolean gameOver = false;
  //Snake s;
  
  public ChaserSnake(int x0,int y0,int w, int h)
  {
    super(x0,y0,w,h);
  }
  
  public ChaserSnake(int x0,int y0)
  {
    super(x0,y0);
  }
  
//  @Override
//  public void paint(Graphics g)
//  {
//    g.setColor(Color.RED);
//    
//    for(int i = 0; i < n; i++)
//    {
//      g.fillOval(x[i],y[i],10,10);
//    }
//    
//    start = false;
//  }
  
  public void movement(int direction,Snake s,boolean cpu)
  {
    Dot d = new Dot(s.dot[(int)(Math.random())*s.dot.length]);
    super.movement(direction,d,cpu);
  }
}