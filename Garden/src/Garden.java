import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Garden extends JPanel implements MouseListener, KeyListener {

    private boolean mouseClicked;
    private int WIDTH = 1000;
    private int HEIGHT = 500;

    public Garden()
    {
        setBackgroundColor(Color.PINK);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        System.out.println("Key released: " + evt.getKeyCode());
    }

    public void keyTyped(KeyEvent evt) {
        System.out.println("Key typed: " + evt.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        System.out.println("Key pressed: " + evt.getKeyCode());
        if (evt.getKeyCode() == 53) // "%" character
        {
            if (mouseClicked) {
                setBackgroundColor(Color.GREEN);
                mouseClicked = false;
            }
        }
        else if (evt.getKeyCode() == 38) // "up arrow" character
        {
            if(mouseClicked)
            {
                paintSun();
            }
        }
        else if (evt.getKeyCode() == 32) // "space bar" character
        {
            setBackgroundColor(Color.PINK);
            repaint();
            mouseClicked = false;
        }
    }


    @Override
    public void mouseClicked(MouseEvent evt)
    {
        mouseClicked = true;
        System.out.println("mouse test");
    }

    @Override
    public void mousePressed(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }


    public void setBackgroundColor(Color color)
    {
        setBackground(color);
    }

    public void paintSun()
    {
        Graphics g = getGraphics();
        g.setColor(Color.YELLOW);
        for(int i = 0;i < WIDTH; i += 200)
        {
            for(int j = 0;j < HEIGHT; j += 200)
            {
                g.fillOval(i, j, 50, 50);
            }
        }
        repaint();
    }

}




