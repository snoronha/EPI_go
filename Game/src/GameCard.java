//class sets location of ball on screen and makes sure it stays within the lines/boundaries
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameCard extends JPanel
{
    public MazeEdge[][] maze;
    public static QA[] QAs;
    private final int NUM_QUESTIONS = 20;
    private QAPanel qaPanel;
    final static int ROWS = 6;
    final static int COLS = 6;
    private int balli     = 0;
    private int ballj     = 0;
    private int WIDTH     = 800;
    private int HEIGHT    = 800;
    private int xOffset   = 90;
    private int yOffset   = 90;
    private int currentQAIndex;
    public int edgeI = -1;
    public int edgeJ = -1;
    public boolean isEdgeDown = false;
    private int score;

    // sounds
    // String testSoundName = "src/test.wav";
    // Clip testSound;


    public GameCard()
    {
        setLayout(new BorderLayout());
        setSize(WIDTH,HEIGHT);
        setBackground(Color.WHITE);

        // Manager to listen to keyboard events
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyDispatcher());

        // create Question/Answers
        QAs = new QA[NUM_QUESTIONS];

        // create maze
        maze = new MazeEdge[ROWS][COLS];
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                maze[i][j] = new MazeEdge(false, false);
            }
        }
        maze[0][0].set(true, false);
        maze[1][0].set(true, false);
        maze[2][0].set(true, false);
        maze[3][0].set(true, false);
        maze[4][0].set(true, false);
        maze[5][0].set(false, true);

        maze[0][1].set(false, true);
        maze[1][1].set(true, false);
        maze[2][1].set(true, false);
        maze[3][1].set(true, true);
        maze[4][1].set(false, true);
        maze[5][1].set(false, true);

        maze[0][2].set(false, true);
        maze[1][2].set(true, false);
        maze[2][2].set(false, true);
        maze[4][2].set(false, true);
        maze[5][2].set(false, true);

        maze[0][3].set(false, true);
        maze[1][3].set(true, false);
        maze[2][3].set(true,false);
        maze[3][3].set(false, true);
        maze[4][3].set(true, true);
        maze[5][3].set(false, true);


        maze[0][4].set(true, true);
        maze[1][4].set(true, true);
        maze[2][4].set(true, false);
        maze[3][4].set(true, false);
        maze[4][4].set(true, false);
        //maze[5][4].set(false, true);

        maze[0][5].set(true, false);
        maze[1][5].set(true, false);
        maze[2][5].set(true, false);
        maze[3][5].set(true, false);
        maze[4][5].set(true, false);


        final String QA_Panel = "QAPanel";
        qaPanel = new QAPanel(this);
        add(qaPanel, BorderLayout.SOUTH);

        // sounds
        /*
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(testSoundName).getAbsoluteFile());
            testSound = AudioSystem.getClip();
            testSound.open(audioInputStream);
            testSound.start();
        }
        catch(Exception e)
        {
            System.out.println("exception = " + e);
        }
        */
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawMaze(g);
        g.setColor(Color.RED);
        g.fillOval(balli * xOffset + 3*xOffset/2, ballj * yOffset + 3*yOffset/2, 20,20);

        // paintScore()
        paintScore(g);
    }
    public void paintScore(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(WIDTH - 129, 21, 108, 83);
        g.setColor(Color.GREEN);
        g.fillRect(WIDTH - 125, 25, 100, 75);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(score), WIDTH - 110, 70);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.drawString("Score", WIDTH - 100, 20);
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
                    if(!maze[balli][ballj].getDown()) // no down edge at (i, j)
                    {
                        balli--;
                        score += 10;
                        repaint();
                    }
                    else // down edge exists at (i, j)
                    {
                        qaPanel.displayQA(getCurrentQA());
                        incrementQAIndex();
                        isEdgeDown = true;
                        edgeI = balli;
                        edgeJ = ballj;
                    }
                }
                if (e.getKeyCode() == 38) // up arrow
                {
                    if(!maze[balli][ballj].getRight())
                    {
                        ballj--;
                        score += 10;
                        repaint();
                    }
                    else   //right edge exists at (i,j)
                    {
                        qaPanel.displayQA(getCurrentQA());
                        incrementQAIndex();
                        isEdgeDown = false;
                        edgeI = balli;
                        edgeJ = ballj;
                    }
                }
                if (e.getKeyCode() == 39)  // right arrow
                {
                    if(!maze[balli+1][ballj].getDown())
                    {
                        balli++;
                        score += 10;
                        repaint();
                    }
                    else   //down edge exists at (i+1,j)
                    {
                        qaPanel.displayQA(getCurrentQA());
                        incrementQAIndex();
                        isEdgeDown = true;
                        edgeI = balli+1;
                        edgeJ = ballj;
                    }
                }
                else if (e.getKeyCode() == 40) // down arrow
                {
                    if(!maze[balli][ballj+1].getRight())
                    {
                        ballj++;
                        score += 10;
                        repaint();
                    }
                    else  //right edge exists at (i,j+1)
                    {
                        qaPanel.displayQA(getCurrentQA());
                        incrementQAIndex();
                        isEdgeDown = false;
                        edgeI = balli;
                        edgeJ = ballj+1;
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
                if(ballj > COLS-2)
                {
                    ballj = COLS-2;
                }
            }
            if(isGameComplete())     //game complete code here
            {
                System.out.println("complete");
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
                    QAs[qNum] = new QA(q, a0, a1, a2, a3);
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
        QAs[qNum] = new QA(q, a0, a1, a2, a3);
        qNum++;
    }
    public QA getCurrentQA()
    {
        return QAs[currentQAIndex];
    }
    public int incrementQAIndex()
    {
        currentQAIndex++;
        currentQAIndex = currentQAIndex % NUM_QUESTIONS;
        return currentQAIndex;
    }
    public void removeMazeEdge(int i, int j, boolean isDown)
    {
        if(isDown)
        {
            maze[i][j].setDown(false);
        }
        else
        {
            maze[i][j].setRight(false);
        }
        score += 40;
        repaint();
    }
    public boolean isGameComplete()
    {
        if(balli == ROWS - 2 && ballj == COLS - 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
