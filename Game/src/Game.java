import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

public class Game extends JFrame
{
    public static void main(String[] args) throws FileNotFoundException {
        // Create a Game
        Game g = new Game();
        g.setSize(800,800);
        g.setDefaultCloseOperation(EXIT_ON_CLOSE);
        g.setVisible(true);
    }

    final String WELCOME_CARD      = "Welcome";
    final String INSTRUCTIONS_CARD = "Instructions";
    final String GAME_CARD         = "Game";
    final String BALL_CARD         = "Ball";


    public Game() throws FileNotFoundException {

        setLayout(new BorderLayout()); //Use this for now.
        setSize(810, 510); //Set the size of the JFrame
        setTitle("The Maze of History!"); //Put Title on top of JFrame
        setResizable(false);
        pack();
        setVisible(true);

        // Body Panel
        BodyPanel bodyPanel = new BodyPanel();

        // Control Panel
        ControlPanel controlPanel = new ControlPanel(bodyPanel);

        // Welcome Card
        WelcomeCard welcomeCard = new WelcomeCard();

        // Instruction Card
        InstructionCard instructionCard = new InstructionCard();

        // Game Card
        GameCard gameCard = new GameCard();
        try {
            GameCard.readFromFileUsingScanner();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Ball Card
        BallCard ballCard = new BallCard();



        // Add welcomeCard, instructionCard and gameCard to bodyPanel
        bodyPanel.add(welcomeCard, WELCOME_CARD);
        bodyPanel.add(instructionCard, INSTRUCTIONS_CARD);
        bodyPanel.add(gameCard, GAME_CARD);
        bodyPanel.add(ballCard,BALL_CARD );


        // Add controlPanel and bodyPanel to JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);

    }

}