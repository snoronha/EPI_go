//shows player's results at the end of the game including their score
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;


public class ResultsCard extends JPanel
{
    private int score = 0;

    public ResultsCard()
    {
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintScore(g);
    }

    public void setScore(int scr)
    {
        score = scr;
    }

    public void paintScore(Graphics g)
    {
        // Change x, y coordinates to be in terms if WIDTH, HEIGHT
        g.setColor(Color.ORANGE);
        g.fillRect(100, 50, 600, 600);
        g.setColor(Color.GREEN);
        g.fillRect(110, 60, 580, 580);
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Congratulations! You did it!", 160, 250);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("You scored " + score + " points!", 250, 300);
    }
}
