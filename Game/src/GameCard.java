//class sets location of ball on screen and makes sure it stays within the lines/boundaries
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private QAPanel qaPanel;
    final static int ROWS = 5;
    final static int COLS = 5;
    private int ballX = 500;
    private int ballY = 600;
    private int width = 800;
    private int height = 800;
    private int xOffset = 80;
    private int yOffset = 90;
    private Image image;

    public GameCard()
    {
        image = loadImage();
        setLayout(new BorderLayout());
        setSize(width,height);
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
        final String QA_Panel = "QAPanel";
        qaPanel = new QAPanel();
        add(qaPanel, BorderLayout.SOUTH);
    }
    private Image loadImage()                        //load image
    {
        ImageIcon ii = new ImageIcon("src/maze1.jpg");    //change src to name of folder
        return ii.getImage();
    }
    public void paintComponent(Graphics g)           //draw image on game panel screen
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 750,800,this);
        g.setColor(Color.RED);
        g.fillOval(ballX,ballY, 20,20);
    }

    /*
    public void paintComponent(Graphics g)
    {
        drawMaze(g);
    }

    public void drawMaze(Graphics g) {
        int xOffset = 60;
        int yOffset = 120;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (maze[i][j].getRight()) {
                    g.drawLine(xOffset + yOffset * i, xOffset + yOffset * j, xOffset + yOffset * i, xOffset + yOffset * (j + 1));
                }
                if (maze[i][j].getDown()) {
                    g.drawLine(xOffset + yOffset * i, xOffset + yOffset * j, xOffset + yOffset * (i + 1), xOffset + yOffset * j);
                }
            }
        }
    }
    */


    // Implement a KeyEventDispatcher that listens to KeyEvents
    private class MyKeyDispatcher implements KeyEventDispatcher
    {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            // KeyEvent.KEY_PRESSED, KeyEvent.KEY_RELEASED or KeyEvent.KEY_TYPED?
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if (e.getKeyCode() == 37) // left arrow
                {
                    ballX -= 10;
                    repaint();
                    System.out.println("left arrow key");
                    qaPanel.displayQA();
                }
                if (e.getKeyCode() == 38) // up arrow
                {
                    ballY -= 10;
                    repaint();
                    System.out.println("up arrow key");
                }
                if (e.getKeyCode() == 39)  // right arrow
                {
                    ballX += 10;
                    repaint();
                    System.out.println("right arrow key");
                }
                else if (e.getKeyCode() == 40) // down arrow
                {
                    ballY += 10;
                    repaint();
                    System.out.println("down arrow key");
                }
                if(ballX < 0)
                {
                    ballX = 0;
                }
                if(ballY < 0)
                {
                    ballY = 0;
                }
                if(ballX > width - xOffset)
                {
                    ballX = width - xOffset;
                }
                if(ballY > height - yOffset)
                {
                    ballY = height - yOffset;
                }
            }
            return false;
        }
    }
    public static void readFromFileUsingScanner() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("src/Questions.txt"));
        while (reader.hasNext())
        {
            String str = reader.nextLine();
            System.out.println(str);
        }
    }

}
