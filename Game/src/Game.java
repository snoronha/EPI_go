// class sets layout and adds control and body panels to screen
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Game extends JFrame
{
    private BodyPanel bodyPanel;
    private ControlPanel controlPanel;

    public static void main(String[] args)
    {
        // Create a Game
        Game g = new Game();
        System.out.println("Game = " + g);
        g.setSize(800,800);
        g.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public Game()
    {
        setLayout(new BorderLayout()); // Use this for now.
        setTitle("The Maze of History!"); // Put Title on top of JFrame
        setResizable(false);
        setVisible(true);

        // Body Panel
        bodyPanel = new BodyPanel();

        // Control Panel
        controlPanel = new ControlPanel(bodyPanel);

        // Add controlPanel and bodyPanel to JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);

    }

}