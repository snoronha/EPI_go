import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class GameCard extends JPanel
{
    //public JPanel gameCard;
    public MazeNode[][] maze;
    final static int ROWS = 5;
    final static int COLS = 5;
    public GameCard()
    {
        setLayout(new BorderLayout());
        setSize(800,800);
        setBackground(Color.WHITE);

        // Manager to listen to keyboard events
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyDispatcher());

        // create maze
        maze = new MazeNode[ROWS][COLS];
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                maze[i][j] = new MazeNode(i % 2 == 0, i % 2 == 0);
            }
        }
    }
    public void paintComponent(Graphics g)
    {
        drawMaze(g);
    }

    public void drawMaze(Graphics g)
    {
        int xOffset = 60;
        int yOffset = 120;
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(maze[i][j].getRight())
                {
                    g.drawLine(xOffset+yOffset*i,xOffset+yOffset*j,xOffset+yOffset*i,xOffset+yOffset*(j+1));
                }
                if(maze[i][j].getDown())
                {
                    g.drawLine(xOffset+yOffset*i,xOffset+yOffset*j,xOffset+yOffset*(i+1),xOffset+yOffset*j);
                }
            }
        }
    }

    // Implement a KeyEventDispatcher that listens to KeyEvents
    private class MyKeyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            // KeyEvent.KEY_PRESSED, KeyEvent.KEY_RELEASED or KeyEvent.KEY_TYPED?
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if (e.getKeyCode() == 39)
                {
                    System.out.println("right arrow key");
                }
                else if (e.getKeyCode() == 40)
                {
                    System.out.println("down arrow key");
                }
            }
            return false;
        }
    }

}
