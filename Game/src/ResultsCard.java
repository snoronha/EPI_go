//shows player's results at the end of the game including their score
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;


public class ResultsCard extends JPanel
{
    GameCard gamCard;

    public ResultsCard(GameCard gmCard)
    {
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        gamCard = gmCard;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintScore(g);
    }

    public void setScore(int scr)
    {
        gamCard.score = scr;
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
        g.drawString("You scored " + gamCard.score + " points!", 220, 300);

        if(gamCard.score < 100)
        {
            g.drawString("You are not good at this game!", 220, 350);
        }
        else if(gamCard.score >= 100 && gamCard.score < 200)
        {
            g.drawString("You are OK at this game!", 220, 350);
        }
        else if(gamCard.score >= 200 && gamCard.score < 300)
        {
            g.drawString("You are decent at this game!", 220, 350);
        }
        else if(gamCard.score >= 300 && gamCard.score < 400)
        {
            g.drawString("You are really good at this game!", 220, 350);
        }
        else if(gamCard.score >= 400)
        {
            g.drawString("You are a god at this game!", 220, 350);
        }

    }
}
