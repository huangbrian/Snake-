import java.awt.*;
import javax.swing.*;

public class Snake
{
  int n;
  Dot[] dot = new Dot[52];
  int width, height = 0;
  boolean start = true;
  boolean gameOver = false;
  
  private boolean r = false;
  private boolean l = false;
  private boolean u = false;
  private boolean dn = false;
  
  /**
   * initial constructor for Snake at beginning of game
   * takes screen width and height for collision handling
   * 
   * @param startX the start x coordinate
   * @param startY the start y coordinate
   * @param w screen width
   * @param h screen height
   */
  public Snake(int startX,int startY,int w, int h)
  {
    n = 3;
    width = w;
    height = h;
    
    for(int i = 0; i < n; i++)
    {
      dot[i] = new Dot(startX-i*10,startY);
    }
  }
  
  public Snake(int startX,int startY)
  {
    n = 3;
    
    for(int i = 0; i < n; i++)
    {
      dot[i] = new Dot(startX-i*10,startY);
    }
  }
  
  /**
   * paint method for snake
   * 
   * @param g graphics for GUI
   */
  public void paint(Graphics g)
  {
    for(int i = 0; i < n; i++)
    {
      g.fillOval(dot[i].getX(),dot[i].getY(),10,10);
    }
    
    start = false;
  }
  
  /**
   * moves snake in direction inputted
   * also checks for collisions
   * 
   * @param direction inputted direction from key listener
   * @param d dot which snake can eat if coordinates match
   * @param cpu determines if the snake movement is self-controlled
   */
  public void movement(int direction,Dot d,boolean cpu)
  {
    Dot[] temp = new Dot[48];
    System.arraycopy(dot,0,temp,0,48);
    for(int i = 0; i < n; i++)
    {
      if(temp[i]==null)
      {
        temp[i] = new Dot(dot[0]);
      }
      dot[i+1] = new Dot(temp[i]);
    }
    if(!cpu)
    {
      switch (direction)
      {
        case 1:
        {
          dot[0].setX(dot[0].getX()-10);
          break;
        }
        case 2:
        {
          dot[0].setX(dot[0].getX()+10);
          break;
        }
        case 3:
        {
          dot[0].setY(dot[0].getY()-10);
          break;
        }
        case 4:
        {
          dot[0].setY(dot[0].getY()+10);
          break;
        }
        default:
          break;
      }
      
      if(dot[0].getX()<0||dot[0].getX()>width||dot[0].getY()<0||dot[0].getY()>height)
           gameOver = true;
    }
    else
      reactTo(d);
    
    for(int i = 3;i < n;i++)
    {
      if(dot[0].equals(dot[i]))
      {
        gameOver = true;
      }
    }
    if(n==0)
      gameOver = true;
  }
  
  
  public boolean checkWin()
  {
    if(n > 45)
    {
      return true;
    }
    else
      return false;
  }
  
  /**
   * When playing against a Snake computer,
   * AI for determining snake movements
   * 
   * @param d Dot the Snake is to eat
   */
  public void reactTo(Dot d)
  {
    //int xOffset;
    //int yOffset;
    
    //int counter = 0;
    
    //if(counter%2==0)
    //{
    if (d.getX() > dot[0].getX()&&!l)
    {
      dot[0].incrX(10);
      r = true;
      l = false;
      u = false;
      dn = false;
    }
    else if (d.getX() < dot[0].getX()&&!r)
    {
      dot[0].incrX(-10);
      l = true;
      r = false;
      u = false;
      dn = false;
    }
    //if (r.contains(x[0] - 5 + xOffset, y[0] - 5, 10, 10))
    //x[0] = x[0] + xOffset;
    //}
    //else
    //{
    else if (d.getY() > dot[0].getY()&&!u)
    {
      dot[0].incrY(10);
      dn = true;
      l = false;
      u = false;
      r = false;
    }
    else if (!dn)
    {
      dot[0].incrY(-10);
      u = true;
      l = false;
      r = false;
      dn = false;
    }
    
    if(!(dot[0].getX()>0||dot[0].getX()<width||dot[0].getY()>0||dot[0].getY()<height))
    {
      gameOver = true;
    }
    //counter++;
  }

  public boolean eat(int a,int b)
  {
    if(dot[0].equals(new Dot(a,b)))
      return true;
    else
      return false;
  }
  
  /**
   * @param which dot in the snake
   */
  public int getX(/*int which*/)
  {
    return dot[0/*which*/].getX();
  }
  
  public void grow()
  {
    n+=6;
    for(int i=n-6;i<n;i++)
    {
      dot[i] = new Dot(dot[n-6]);
    }
  }
  /**
   * @param which dot in the snake
   */
  public int getY(/*int which*/)
  {
    return dot[0/*which*/].getY();
  }
  
  public Dot getDot(int which)
  {return dot[which];}
}