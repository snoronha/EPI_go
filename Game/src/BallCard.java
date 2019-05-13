import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;
public class BallCard extends JPanel implements ActionListener
{
    private int delay = 10;
    private Timer timer;
    private int x = 0;
    private int y = 0;
    private int radius = 20;
    private int dx = 5;
    private int dy = 5;
    public BallCard()
    {
        setSize(50,50);
        setVisible(true);
        //timer = new Timer(delay,this);
        //timer.start();
    }
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
    public void paintComponent(Graphics g)
    {
        g.drawOval(10,10, 30,30);
        /*
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        if(x < radius)
        {
            dx = Math.abs(dx);
        }
        if (x > getWidth() - radius)
        {
            dx = -Math.abs(dx);
        }
        if (y < radius)
        {
            dy = Math.abs(dy);
        }
        if (y > getHeight() - radius)
        {
            dy = -Math.abs(dy);
        }
        x += dx;
        y += dy;
        g.fillOval(x - radius, y - radius, radius*2, radius*2);
        */
    }
}
