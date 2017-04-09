import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener, ActionListener
{
  private Timer t;
  private Timer elapsed;
  private int time = 120;
  private int seconds = 0;
  private int minutes = 2;
  private JButton start;
  private JButton quit;
  private JButton rules;
  private JButton endSession;
  private Snake s;
  private ChaserSnake c;
  private int dir1 = 0;
  private int dir2 = 0;
  private int width, height = 0;
  private boolean pPress = false;
  private boolean snakePressedOnce, chaserPressedOnce = false;
  int a = 0;
  Dot d;
  
  JMenuBar cpuOn;
  JMenu cpuOps;
  JMenuItem chaser, snake, original;
  
  JMenuBar timeOptions;
  JMenu options;
  JMenuItem oneMin,twoMin,threeMin,fiveMin,forever;
  
  JButton cpuOff;
  
  boolean cCom = false;
  boolean sCom = false;
  
  public Game(int w, int h)
  {
    width = w; height = h;
    t = new Timer(48,new RefreshListener());
    elapsed = new Timer(1000,new Elapsed());
    start = new JButton("Start (default 2p setting)");
    quit = new JButton("Exit Game");
    cpuOff = new JButton("2p");
    rules = new JButton("Rules and Controls");
    endSession = new JButton("Exit Round (no contest)");

    start.addActionListener(this);    
    quit.addActionListener(this);
    cpuOff.addActionListener(this);
    rules.addActionListener(this);
    endSession.addActionListener(this);
    
    cpuOn = new JMenuBar();
    cpuOps = new JMenu("1p Options");
    chaser = new JMenuItem("Play as Snake(green)");
    snake = new JMenuItem("Play as Chaser(red)");
    
    chaser.addActionListener(new PlayerMenu());
    snake.addActionListener(new PlayerMenu());
    
    cpuOps.add(chaser);
    cpuOps.add(snake);
    cpuOn.add(cpuOps);
    
    timeOptions = new JMenuBar();
    options = new JMenu("Total Time (default 2 minutes)");
    oneMin = new JMenuItem("1 minute");
    twoMin = new JMenuItem("2 minutes");
    threeMin = new JMenuItem("3 minutes");
    fiveMin = new JMenuItem("5 minutes");
    forever = new JMenuItem("forever");
    
    oneMin.addActionListener(new TimeMenu());
    twoMin.addActionListener(new TimeMenu());
    threeMin.addActionListener(new TimeMenu());
    fiveMin.addActionListener(new TimeMenu());
    forever.addActionListener(new TimeMenu());
    
    options.add(oneMin);
    options.add(twoMin);
    options.add(threeMin);
    options.add(fiveMin);
    options.add(forever);
    timeOptions.add(options);
    
    add(start);
    add(cpuOn);
    add(cpuOff);
    add(timeOptions);
    add(quit);
    add(rules);
    add(endSession);
    
    endSession.setVisible(false);
    
    addKeyListener(this);
    
    s = new Snake(500,300,w,h);
    c = new ChaserSnake(800,600,w,h);
    d = new Dot(width,height,true);
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    g.drawString("Snake score:"+(s.n-3),800,50);
    g.drawString("Chaser score:"+(c.n-3),900,50);
    if(time >= 0)
    {
      if(seconds >= 10)
        g.drawString("Time Remaining  "+minutes+":"+seconds,1000,50);
      else
        g.drawString("Time Remaining  "+minutes+":0"+seconds,1000,50);
    }
    g.setColor(Color.GREEN);
    s.paint(g);
    g.setColor(Color.RED);
    c.paint(g);
    d.paint(g);
    g.setColor(Color.CYAN);
    g.fillRect(0,height/10*10+10,width/10*10+10,height+10);
    g.fillRect(width+10,0,width+20,height+20);
  }
  
  public void actionPerformed(ActionEvent evt)
  {
    requestFocus();
    
    if(evt.getSource() == start)
    {
      t.start();
      elapsed.start();
      start.setVisible(false);
      start.setText("Start");
      cpuOff.setVisible(false);
      cpuOn.setVisible(false);
      timeOptions.setVisible(false);
      quit.setVisible(false);
      rules.setVisible(false);
    }
    
    if(evt.getSource() == quit)
    {
      System.exit(2);
    }
    
    if(evt.getSource() == cpuOff)
    {
      sCom = false;
      cCom = false;
    }
    
    if(evt.getSource() == rules)
    {
      JFrame instruct = new JFrame();
      instruct.setVisible(true);
      instruct.setSize(500,500);
      instruct.setLocation(100,50);
      JTextArea instructions = new JTextArea
        ("Controls:\n"+
         "up, left, down, right for snake(green)."
           +"W A S D for chaser(red)\n"
           +"Click 'p' to pause the game.\n\n"
           +"Rules:\n"
           +"Snake(green) eats the orange dot to grow and add to score\n"
           +"while the Chaser(red) eats any dot on the Snake to grow and add to score."
           +"\n\nNeither Snake now Chaser may cross outside of the borders of the screen."
           +"\nBorders are the edges of the screen and the cyan area."
           +"\n\nThe Snake also cannot run into any part to the Chaser.\n\n"
           +"Once the Snake gets over 42 points, the Snake wins.\n"
           +"Other ways to win are forcing the other player to crash into a border.\n\n"
           +"NOTE: Rules are constantly being changed for rebalancing :-)");
      instruct.setContentPane(instructions);
    }
    
    if(evt.getSource() == endSession)
    {
      JOptionPane.showMessageDialog(new JDialog(),"No Contest. Click 'Start' for new game");
      resetAfterGame();
    }
  }
  
  class PlayerMenu implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      if(evt.getSource() == chaser)
      {
        cCom = true;
        sCom = false;
      }
      else
      {
        sCom = true;
        cCom = false;
      }
    }
  }
  
  class TimeMenu implements ActionListener
  {
    TimeMenu(){}
    public void actionPerformed(ActionEvent evt)
    {
      if(evt.getSource() == oneMin)
      {
        time = 60;
        options.setText("1 minute");
      }
      if(evt.getSource() == twoMin)
      {
        time = 120;
        options.setText("2 minutes");
      }
      if(evt.getSource() == threeMin)
      {
        time = 180;
        options.setText("3 minutes");
      }
      if(evt.getSource() == fiveMin)
      {
        time = 300;
        options.setText("5 minutes");
      }
      if(evt.getSource() == forever)
      {
        time = -1;
        options.setText("forever");
      }
      minutes = time/60;
      seconds = time - 60*minutes;
      repaint(/*1000,50,1250,80*/);
      repaint();
    }
  }
  
  class RefreshListener implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      snakePressedOnce = false;
      chaserPressedOnce = false;
      
      if(s.eat(d.getX(),d.getY()))
      {
        d = new Dot(width,height,true);
        s.grow();
        
        if(s.checkWin())
        {
          c.gameOver = true;
        }
      }
      
      for(int i = 0;i < s.n;i++)
      {
        // System.out.println(c.x[0]+""+c.y[0]+""+s.x[i]+""+s.y[i]);
        if(c.getDot(0).equals(s.getDot(i)))
        {
          c.n++;
          s.n--;
          
          if(c.n>45)
          {
            s.gameOver = true;
          }
          
          break;
        }
      }
      for(int i = 0;i < c.n;i++)
      {
        if(s.getDot(0).equals(c.getDot(i)))
          s.gameOver = true;
      }
      
      if(s.gameOver||c.gameOver)
      {
        t.stop();
        elapsed.stop();
        
        if(s.gameOver)
        {
          JOptionPane.showMessageDialog(new JDialog(),"Chaser Wins! Click 'Start' for new game");
          s.gameOver = false;
        }
        else if(c.gameOver)
        {
          JOptionPane.showMessageDialog(new JDialog(),"Snake Wins! Click 'Start' for new game");
          c.gameOver = false;
        }
        resetAfterGame();
      }
      
      s.movement(dir1,d,sCom);
      c.movement(dir2,s,cCom);
      repaint();
    }
  }
  public void resetAfterGame()
  {
    s = new Snake(500,300,width,height);
    c = new ChaserSnake(800,600,width,height);
    start.setVisible(true);
    cpuOff.setVisible(true);
    cpuOn.setVisible(true);
    timeOptions.setVisible(true);
    quit.setVisible(true);
    rules.setVisible(true);
    endSession.setVisible(false);
    time = 120;
    minutes = time/60;
    seconds = time - 60*minutes;
    dir1 = 0;
    dir2 = 0;
    
    options.setText("Total Time (default 2 minutes)");
  }
  class Elapsed implements ActionListener
  {
    public Elapsed(){}
    
    public void actionPerformed(ActionEvent evt)
    {
      time--;
      if(time == 0)
      {
        if(s.n>c.n)
          c.gameOver = true;
        else
          s.gameOver = true;
      }
      minutes = time/60;
      seconds = time - 60*minutes;
      repaint(/*1000,50,1250,80*/);
    }
  }
  
  public void keyPressed(KeyEvent evt)
  {
    if(!snakePressedOnce)
    {
      if(evt.getKeyCode() == KeyEvent.VK_LEFT&&dir1!=2)
      {
        dir1 = 1;
        snakePressedOnce = true;
      }
      if(evt.getKeyCode() == KeyEvent.VK_RIGHT&&dir1!=1)
      {
        dir1 = 2;
        snakePressedOnce = true;
      }
      if(evt.getKeyCode() == KeyEvent.VK_UP&&dir1!=4)
      {
        dir1 = 3;
        snakePressedOnce = true;
      }
      if(evt.getKeyCode() == KeyEvent.VK_DOWN&&dir1!=3)
      {
        dir1 = 4;
        snakePressedOnce = true;
      }
    }
    if(!chaserPressedOnce)
    {
      if(evt.getKeyChar() == 'a'&&dir2!=2)
      {
        dir2 = 1;
        chaserPressedOnce = true;
      }
      if(evt.getKeyChar() == 'd'&&dir2!=1)
      {
        dir2 = 2;
        chaserPressedOnce = true;
      }
      if(evt.getKeyChar() == 'w'&&dir2!=4)
      {
        dir2 = 3;
        chaserPressedOnce = true;
      }
      if(evt.getKeyChar() == 's'&&dir2!=3)
      {
        dir2 = 4;
        chaserPressedOnce = true;
      }
    }
//    if(dir<=4)
//      which = false;
//    else
//      which = true;
  }
  public void keyReleased(KeyEvent evt)
  {}
  public void keyTyped(KeyEvent evt)
  {
    if(evt.getKeyChar()=='p'&&pPress&&!start.isVisible())
    {
      t.start();
      elapsed.start();
      quit.setVisible(false);
      rules.setVisible(false);
      endSession.setVisible(false);
      pPress = false;
    }
    else if(evt.getKeyChar() == 'p'&&!start.isVisible())
    {
      t.stop();
      elapsed.stop();
      quit.setVisible(true);
      rules.setVisible(true);
      endSession.setVisible(true);
      pPress = true;
    }
//    if(evt.getKeyChar() == 'o')
//    {o = true;/*m = false;n = false;o2 = false;*/m2 = false;}
//    if(evt.getKeyChar() == 'm')
//    {m = true;/*n = false;o2 = false;m2 = false;*/}
//    if(evt.getKeyChar() == 'n')
//    {n = true;o = false;m = false;}
//    if(evt.getKeyChar() == 'o')
//    {o2 = true;/*m2 = false;*/}
//    if(evt.getKeyChar() == 'm')
//    {m2 = true;}
  }
}
