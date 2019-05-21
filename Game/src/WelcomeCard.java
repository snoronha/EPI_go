// class welcomes user to game
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class WelcomeCard extends JPanel
{

    public WelcomeCard()
    {
        setBackground(Color.YELLOW);
        setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintWelcome(g);
    }

    public void paintWelcome(Graphics g)
    {
        // Change x, y coordinates to be in terms if WIDTH, HEIGHT
        g.setColor(Color.ORANGE);
        g.fillRect(100, 50, 600, 600);
        g.setColor(Color.GREEN);
        g.fillRect(110, 60, 580, 580);
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Welcome to the Maze of History!", 130, 300);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.drawString("A fun game on Egyptian History", 230, 350);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
        g.drawString("Developed by Aarav Noronha", 200, 400);
    }
}
