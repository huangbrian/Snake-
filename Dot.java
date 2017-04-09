import java.awt.*;

public class Dot
{
  private int x = 0;
  private int y = 0;
  
  public Dot(int width, int height, boolean overloaded)
  {
    x = (int)(Math.random()*width)/10 *10;
    y = (int)(Math.random()*height)/10 *10;
  }
  
  public Dot(int xC,int yC)
  {
    x = xC;
    y = yC;
  }
  
  public Dot(Dot copy)
  {
    x = copy.x;
    y = copy.y;
  }
  
  public void paint(Graphics g)
  {
    g.setColor(Color.ORANGE);
    g.fillOval(x,y,10,10);
  }
  
  public int getX()
  {return x;}
  
  public int getY()
  {return y;}
  
  public void setX(int xC)
  {x = xC;}
  
  public void setY(int yC)
  {y = yC;}
  
  public void incrX(int add)
  {x+=add;}
  
  public void incrY(int add)
  {y+=add;}
  
  public boolean equals(Dot other)
  {
    return x==other.x&&y==other.y;
  }
  
  public int getDistance(Dot other)
  {
    return Math.abs(x-other.x)+Math.abs(y-other.y);
  }
}