// class reads from question.txt file and adds different cards onto screen
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import javax.swing.JPanel;

public class BodyPanel extends JPanel
{
    public Game game;
    public WelcomeCard welcomeCard;
    public InstructionCard instructionCard;
    public GameCard gameCard;
    public ResultsCard resultsCard;
    public final String WELCOME_CARD      = "Welcome";
    public final String INSTRUCTIONS_CARD = "Instructions";
    public final String GAME_CARD         = "Game";
    public final String RESULTS_CARD      = "Results";

    public BodyPanel(Game g)
    {
        setLayout(new CardLayout());
        setBackground(Color.CYAN);
        game = g;
        welcomeCard     = new WelcomeCard();     // Welcome Card
        instructionCard = new InstructionCard(); // Instruction Card
        gameCard        = new GameCard(this);        // Game Card
        resultsCard     = new ResultsCard(gameCard);     // Results Card
        gameCard.setResultsCard(resultsCard);
        try {
            GameCard.readFromFileUsingScanner();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Add welcomeCard, instructionCard and gameCard to bodyPanel
        add(welcomeCard, WELCOME_CARD);
        add(instructionCard, INSTRUCTIONS_CARD);
        add(gameCard, GAME_CARD);
        add(resultsCard, RESULTS_CARD);
    }

    public void showCard(String card)
    {
        CardLayout cl = (CardLayout)(getLayout());
        cl.show(this,card);
    }

    public void setGameFrame(Game g)
    {
        game = g;
    }

}