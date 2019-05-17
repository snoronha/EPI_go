//class sets location of ball on screen and makes sure it stays within the lines/boundaries
import javax.swing.*;
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
    public MazeNode[][] maze;
    public static QA[] QAs;
    private final int NUM_QUESTIONS = 20;
    private QAPanel qaPanel;
    final static int ROWS = 6;
    final static int COLS = 6;
    private int balli = 0;
    private int ballj = 0;
    private int width = 800;
    private int height = 800;
    private int xOffset = 100;
    private int yOffset = 100;

    public GameCard()
    {
        setLayout(new BorderLayout());
        setSize(width,height);
        setBackground(Color.WHITE);

        // Manager to listen to keyboard events
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyDispatcher());

        // create Question/Answers
        QAs = new QA[NUM_QUESTIONS];

        // create maze
        maze = new MazeNode[ROWS][COLS];
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                // maze[i][j] = new MazeNode(i % 2 == 0, i % 2 == 0);
                maze[i][j] = new MazeNode(false, false);
            }
        }
        maze[0][0].set(true, true);
        maze[1][0].set(true, false);
        maze[2][0].set(true, false);
        maze[3][0].set(true, false);
        maze[4][0].set(true, false);
        maze[0][2].set(false, true);
        maze[0][3].set(false, true);
        maze[5][0].set(false, true);
        maze[5][1].set(false, true);
        maze[1][1].set(true, false);
        maze[2][1].set(true, false);
        maze[3][1].set(true, true);
        maze[4][1].set(false, true);
        maze[4][2].set(false, true);
        maze[4][3].set(true, false);
        maze[5][2].set(false, true);
        maze[0][4].set(true, false);
        maze[1][4].set(true, false);
        maze[2][4].set(true, false);
        maze[3][4].set(true, false);
        maze[4][4].set(true, false);


        final String QA_Panel = "QAPanel";
        qaPanel = new QAPanel();
        add(qaPanel, BorderLayout.SOUTH);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawMaze(g);
        g.setColor(Color.RED);
        g.fillOval(balli * xOffset + 3*xOffset/2, ballj * yOffset + 3*yOffset/2, 20,20);
    }

    public void drawMaze(Graphics g) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (maze[i][j].getRight()) {
                    g.drawLine(xOffset + xOffset * i, yOffset + yOffset * j, xOffset + xOffset * (i + 1), yOffset + yOffset * j);
                }
                if (maze[i][j].getDown()) {
                    g.drawLine(xOffset + xOffset * i, yOffset + yOffset * j, xOffset + xOffset * i, yOffset + yOffset * (j + 1));
                }
            }
        }
    }


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
                    qaPanel.displayQA(QAs[0]);
                    if(!(maze[balli][ballj].getDown()))
                    {
                        balli--;
                        repaint();
                    }
                    else
                    {

                    }
                }
                if (e.getKeyCode() == 38) // up arrow
                {
                    if(!(maze[balli][ballj].getRight()))
                    {
                        ballj--;
                        repaint();
                    }
                    else
                    {

                    }
                }
                if (e.getKeyCode() == 39)  // right arrow
                {
                    if(!(maze[balli+1][ballj].getDown()))
                    {
                        System.out.println("got it balli = " + balli);
                        balli++;
                        repaint();
                    }
                    else
                    {
                        System.out.println("not it");
                    }
                }
                else if (e.getKeyCode() == 40) // down arrow
                {
                    if(!(maze[balli][ballj+1].getRight()))
                    {
                        ballj++;
                        repaint();
                    }
                }
                if(balli < 0)
                {
                    balli = 0;
                }
                if(ballj < 0)
                {
                    ballj = 0;
                }
                if(balli > ROWS-2)
                {
                    balli = ROWS-2;
                }
                if(ballj > COLS-3)
                {
                    ballj = COLS-3;
                }
            }
            return false;
        }
    }
    public static void readFromFileUsingScanner() throws FileNotFoundException
    {
        String q = "", a0 = "", a1 = "", a2 = "", a3 = "";

        Scanner reader = new Scanner(new File("src/Questions.txt"));
        int counter = 0;
        int qNum = 0;
        while (reader.hasNext())
        {
            String str = reader.nextLine();

            if(counter % 5 == 0)
            {
                if (counter > 0) // create previous QA and append to QAs
                {
                    QAs[qNum] = new QA(q, a0, a1, a2, a3, 0);
                    qNum++;
                }
                q = str;
            }
            else if(counter % 5 == 1)
            {
                a0 = str;
            }
            else if(counter % 5 == 2)
            {
                a1 = str;
            }
            else if(counter % 5 == 3)
            {
                a2 = str;
            }
            else if(counter % 5 == 4)
            {
                a3 = str;
            }
            counter++;
        }
        QAs[10].print();
    }
}
