import java.awt.*;
import javax.swing.*;

public class Game extends JFrame {
    public static void main(String[] args) {
        // write your code here
        Game g = new Game();
        g.setSize(800,800);
        g.setDefaultCloseOperation(EXIT_ON_CLOSE);
        g.setVisible(true);
    }

    final String WELCOME_CARD      = "Welcome";
    final String INSTRUCTIONS_CARD = "Instructions";
    final String GAME_CARD         = "Game";

    public Game()
    {

        setLayout(new BorderLayout()); //Use this for now.
        setSize(810, 510); //Set the size of the JFrame
        setTitle("The Maze of History!"); //Put Title on top of JFrame
        setResizable(false);
        setVisible(true);

        // Body Panel
        BodyPanel bp = new BodyPanel();

        // Control Panel
        ControlPanel cp = new ControlPanel(bp.bodyPanel);

        // Welcome Card
        WelcomeCard wc = new WelcomeCard();

        // Instruction Card
        InstructionCard ic = new InstructionCard();

        // Game Card
        GameCard gc = new GameCard();

        // Add welcomeCard, instructionCard and gameCard to bodyPanel
        bp.bodyPanel.add(wc.welcomeCard, WELCOME_CARD);
        bp.bodyPanel.add(ic.instructionCard, INSTRUCTIONS_CARD);
        bp.bodyPanel.add(gc.gameCard, GAME_CARD);

        // Add controlPanel and bodyPanel to JFrame
        add(cp.controlPanel, BorderLayout.NORTH);
        add(bp.bodyPanel, BorderLayout.CENTER);
    }
}