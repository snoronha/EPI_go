import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Garden extends JPanel implements MouseListener, KeyListener
{

    private boolean mouseClicked, isWater, isSun;
    private int WIDTH = 1000;
    private int HEIGHT = 500;

    public Garden()
    {
        mouseClicked = false;
        isWater      = false;
        isSun        = false;
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (isWater)
        {
            paintWater(g);
        }
        else if (isSun)
        {
            paintSun(g);
        }
        else
        {
            setBackgroundColor(g, Color.PINK);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt)
    {
        System.out.println("Key released: " + evt.getKeyCode());
    }

    public void keyTyped(KeyEvent evt)
    {
        System.out.println("Key typed: " + evt.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent evt)
    {
        System.out.println("Key pressed: " + evt.getKeyCode());
        if (evt.getKeyCode() == 53) // "%" character
        {
            if (mouseClicked)
            {
                isWater      = true;
                isSun        = false;
                mouseClicked = false;
		        repaint();
            }
        }
        else if (evt.getKeyCode() == 38) // "up arrow" character
        {
            if(mouseClicked)
            {
                isWater      = false;
                isSun        = true;
                mouseClicked = false;
                repaint();
            }
        }
        else if (evt.getKeyCode() == 32) // "space bar" character
        {
            isWater      = false;
            isSun        = false;
            mouseClicked = false;
            repaint();
        }
    }


    @Override
    public void mouseClicked(MouseEvent evt)
    {
        mouseClicked = true;
        System.out.println("mouse clicked");
    }

    @Override
    public void mousePressed(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }


    public void setBackgroundColor(Graphics g, Color color)
    {
        g.setColor(color);
	g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void paintWater(Graphics g)
    {
        setBackgroundColor(g, Color.GREEN);
    }
    
    public void paintSun(Graphics g)
    {
        setBackgroundColor(g, Color.GREEN);
        g.setColor(Color.YELLOW);
        g.fillOval(0, 0, 50, 50);
        for(int i = 0;i < WIDTH; i += 200)
        {
            for(int j = 0;j < HEIGHT; j += 200)
            {
                g.fillOval(i, j, 50, 50);
            }
        }
    }

}
