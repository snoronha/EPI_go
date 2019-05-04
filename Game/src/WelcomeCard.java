import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

public class WelcomeCard
{
    public JPanel welcomeCard;
    JTextArea welcomeText = new JTextArea("Welcome to the game!" ,10,50);
    public WelcomeCard()
    {
        welcomeCard = new JPanel(new BorderLayout());
        welcomeCard.setBackground(Color.RED);
        welcomeCard.add(welcomeText);
    }
}
