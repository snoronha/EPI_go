// class reads from question.txt file and adds different cards onto screen
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import javax.swing.JPanel;

public class BodyPanel extends JPanel
{
    public WelcomeCard welcomeCard;
    public InstructionCard instructionCard;
    public GameCard gameCard;
    final String WELCOME_CARD      = "Welcome";
    final String INSTRUCTIONS_CARD = "Instructions";
    final String GAME_CARD         = "Game";

    public BodyPanel()
    {
        setLayout(new CardLayout());
        setBackground(Color.CYAN);

        welcomeCard     = new WelcomeCard();     // Welcome Card
        instructionCard = new InstructionCard(); // Instruction Card
        gameCard        = new GameCard();        // Game Card
        try {
            GameCard.readFromFileUsingScanner();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Add welcomeCard, instructionCard and gameCard to bodyPanel
        add(welcomeCard, WELCOME_CARD);
        add(instructionCard, INSTRUCTIONS_CARD);
        add(gameCard, GAME_CARD);

    }

}