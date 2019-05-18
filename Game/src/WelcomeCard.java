// class welcomes user to game
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

public class WelcomeCard extends JPanel
{
    JTextArea welcomeText = new JTextArea("Welcome to the Maze of History!" ,10,50);
    public WelcomeCard()
    {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        setVisible(true);
        add(welcomeText);
    }
}
