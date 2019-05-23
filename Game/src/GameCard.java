//class sets location of ball on screen and makes sure it stays within the lines/boundaries
//it also makes the actual maze, paints the score on the screen and tests if the game is complete
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

import java.awt.event.KeyEvent;

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
    public int score;
    public boolean isAnswering = false;
    public BodyPanel bodyPanel;
    public ResultsCard resultsCard;
    private Timer timer;
    private int timerCount = 0;

    // sounds
    // String testSoundName = "src/test.wav";
    // Clip testSound;


    public GameCard(BodyPanel bdyPanel)
    {
        bodyPanel = bdyPanel;
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setBackground(Color.ORANGE);

        // Manager to listen to keyboard events
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyDispatcher());

        // create Question/Answers
        QAs = new QA[NUM_QUESTIONS];

        // create maze
        maze = new MazeEdge[ROWS][COLS];
        createMaze();

        qaPanel = new QAPanel(this);
        add(qaPanel, BorderLayout.SOUTH);

        createTimer();

    }

    public void resetGame()
    {
        balli = 0;
        ballj = 0;
        edgeI = -1;
        edgeJ = -1;
        score = 0;
        timerCount  = 0;
        isEdgeDown  = false;
        isAnswering = false;
        createMaze();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintMaze(g);
        paintActiveEdge(g);
        paintBall(g);
        paintScore(g);
        paintTimer(g);
    }

    public void paintBall(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval(balli * xOffset + 3*xOffset/2, ballj * yOffset + 3*yOffset/2, 20,20);
    }

    public void paintTimer(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.drawString("Time", WIDTH - 100, 140);
        g.setColor(Color.WHITE);
        g.fillRect(WIDTH - 125, 145, 100, 75);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
        g.setColor(Color.BLACK);
        if (timerCount/2 < 10)
        {
            g.drawString(String.valueOf(timerCount/2), WIDTH - 90, 200);
        }
        else if (timerCount/2 < 100)
        {
            g.drawString(String.valueOf(timerCount/2), WIDTH - 100, 200);
        }
        else
        {
            g.drawString(String.valueOf(timerCount/2), WIDTH - 110, 200);
        }
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

    public void paintMaze(Graphics g) {
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

    public void paintActiveEdge(Graphics g)
    {
        if (edgeI >= 0 && edgeJ >= 0)
        {
            g.setColor(Color.RED);
            if (isEdgeDown)
            {
                g.fillRect(xOffset + xOffset * edgeI - 3, yOffset + yOffset * edgeJ, 6, yOffset);
            }
            else
            {
                g.fillRect(xOffset + xOffset * edgeI, yOffset + yOffset * edgeJ - 3, xOffset, 6);
            }
        }
    }

    // Implement a KeyEventDispatcher that listens to KeyEvents
    private class MyKeyDispatcher implements KeyEventDispatcher
    {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            if (!isAnswering)
            {
                // KeyEvent.KEY_PRESSED, KeyEvent.KEY_RELEASED or KeyEvent.KEY_TYPED?
                if (e.getID() == KeyEvent.KEY_PRESSED)
                {
                    if (e.getKeyCode() == 37) // left arrow
                    {
                        if (balli > 0)
                        {
                            if (!maze[balli][ballj].getDown()) // no down edge at (i, j)
                            {
                                balli--;
                                repaint();
                            }
                            else // down edge exists at (i, j)
                            {
                                isAnswering = true;
                                qaPanel.displayQA(getCurrentQA());
                                incrementQAIndex();
                                isEdgeDown = true;
                                edgeI = balli;
                                edgeJ = ballj;
                            }
                        }
                    }
                    if (e.getKeyCode() == 38) // up arrow
                    {
                        if (ballj > 0)
                        {
                            if (!maze[balli][ballj].getRight())
                            {
                                ballj--;
                                repaint();
                            }
                            else   // right edge exists at (i,j)
                            {
                                isAnswering = true;
                                qaPanel.displayQA(getCurrentQA());
                                incrementQAIndex();
                                isEdgeDown = false;
                                edgeI = balli;
                                edgeJ = ballj;
                            }
                        }
                    }
                    if (e.getKeyCode() == 39)  // right arrow
                    {
                        if (balli < ROWS - 2) {
                            if (!maze[balli + 1][ballj].getDown())
                            {
                                balli++;
                                repaint();
                            }
                            else   // down edge exists at (i+1,j)
                            {
                                isAnswering = true;
                                qaPanel.displayQA(getCurrentQA());
                                incrementQAIndex();
                                isEdgeDown = true;
                                edgeI = balli + 1;
                                edgeJ = ballj;
                            }
                        }
                    }
                    else if (e.getKeyCode() == 40) // down arrow
                    {
                        if (ballj < COLS - 2)
                        {
                            if (!maze[balli][ballj + 1].getRight())
                            {
                                ballj++;
                                repaint();
                            }
                            else  //right edge exists at (i,j+1)
                            {
                                isAnswering = true;
                                qaPanel.displayQA(getCurrentQA());
                                incrementQAIndex();
                                isEdgeDown = false;
                                edgeI = balli;
                                edgeJ = ballj + 1;
                            }
                        }
                    }
                }
                if (isGameComplete())     //game complete code here
                {
                    System.out.println("complete");
                    resultsCard.setScore(score);
                    bodyPanel.showCard(bodyPanel.RESULTS_CARD);
                }
            }
            return false;
        }
    }

    public void createTimer()
    {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                timerCount += 1;
                repaint();
                // if (timerCount == 20) timer.stop();
            }
        };
        timer = new Timer(500, taskPerformer);

    }

    public void startTimer()
    {
        timer.start();
    }

    public void stopTimer()
    {
        timer.stop();
    }

    // Read Questions.txt: this file contains questions and multiple choice answers
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
        QAs       = RandomizeQAs(QAs);
        qNum++;
    }

    // Randomize the QAs array
    public static QA[] RandomizeQAs(QA[] array)
    {
        Random rgen = new Random();  // Random number generator
        for (int i = 0; i < array.length; i++)
        {
            int randomPosition = rgen.nextInt(array.length);
            QA temp  = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    public QA getCurrentQA()
    {
        return QAs[currentQAIndex];
    }

    public int incrementQAIndex()
    {
        qaPanel.counter = 0;
        qaPanel.correctChosen = false;
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
        edgeI = -1;
        edgeJ = -1;
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

    public int getScore()
    {
        return score;
    }

    public void setResultsCard(ResultsCard resCard)
    {
        resultsCard = resCard;
    }

    public void createMaze()
    {
        // fill empty maze (no edges)
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                maze[i][j] = new MazeEdge(false, false);
            }
        }
        fillRandomMaze();

    }

    public void fillRandomMaze()
    {
        Random rgen = new Random();
        int randomInt = rgen.nextInt(3);
        System.out.println("RAND = " + randomInt);

        if (randomInt == 0)
        {
            // Maze 0
            maze[0][0].set(true, false); maze[1][0].set(true, false);
            maze[2][0].set(true, false); maze[3][0].set(true, false);
            maze[4][0].set(true, false); maze[5][0].set(false, true);

            maze[0][1].set(false, true); maze[1][1].set(true, false);
            maze[2][1].set(true, false); maze[3][1].set(true, true);
            maze[4][1].set(false, true); maze[5][1].set(false, true);

            maze[0][2].set(false, true); maze[1][2].set(true, false);
            maze[2][2].set(false, true); maze[3][2].set(false, true);
            maze[4][2].set(false, true); maze[5][2].set(false, true);

            maze[0][3].set(false, true); maze[1][3].set(true, false);
            maze[2][3].set(true, false); maze[3][3].set(false, true);
            maze[4][3].set(true, true);  maze[5][3].set(false, true);

            maze[0][4].set(true, true);  maze[1][4].set(true, true);
            maze[2][4].set(true, false); maze[3][4].set(true, false);
            maze[4][4].set(true, false);

            maze[0][5].set(true, false); maze[1][5].set(true, false);
            maze[2][5].set(true, false); maze[3][5].set(true, false);
            maze[4][5].set(true, false);
        }
        else if (randomInt == 1)
        {
            // Maze 1
            maze[0][0].set(true, false); maze[1][0].set(true, false);
            maze[2][0].set(true, true);  maze[3][0].set(true, true);
            maze[4][0].set(true, true);  maze[5][0].set(false, true);

            maze[0][1].set(false, true); maze[1][1].set(true, false);
            maze[2][1].set(true, false); maze[3][1].set(true, true);
            maze[4][1].set(false, true); maze[5][1].set(false, true);

            maze[0][2].set(true, true);  maze[1][2].set(true, false);
            maze[2][2].set(false, true); maze[3][2].set(false, true);
            maze[4][2].set(false, true); maze[5][2].set(false, true);

            maze[0][3].set(false, true); maze[1][3].set(true, false);
            maze[2][3].set(true, false); maze[3][3].set(false, true);
            maze[4][3].set(true, true);  maze[5][3].set(false, true);


            maze[0][4].set(true, true);  maze[1][4].set(true, true);
            maze[2][4].set(true, true);  maze[3][4].set(true, true);
            maze[4][4].set(true, true);

            maze[0][5].set(true, false); maze[1][5].set(true, false);
            maze[2][5].set(true, false); maze[3][5].set(true, false);
            maze[4][5].set(true, false);
        }
        else if (randomInt == 2)
        {
            // Maze 2
            for (int i = 0; i < ROWS - 1; i++) {
                for (int j = 0; j < COLS - 1; j++) {
                    maze[i][j] = new MazeEdge(true, true);
                }
            }
            // last row
            maze[0][5].set(true, false); maze[1][5].set(true, false);
            maze[2][5].set(true, false); maze[3][5].set(true, false);
            maze[4][5].set(true, false);
            // last col
            maze[5][0].set(false, true); maze[5][1].set(false, true);
            maze[5][2].set(false, true); maze[5][3].set(false, true);
        }

    }

}
