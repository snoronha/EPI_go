// class sets layout and adds control and body panels to screen
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Game extends JFrame
{
    private BodyPanel bodyPanel;
    private ControlPanel controlPanel;
    public ModalDialog dialog;


    public static void main(String[] args)
    {
        // Create a Game
        Game g = new Game();
        g.setSize(800,800);
        g.setDefaultCloseOperation(EXIT_ON_CLOSE);
        g.dialog = new ModalDialog(g);
        g.dialog.setVisibility(false);
    }

    public Game()
    {
        setLayout(new BorderLayout()); // Use this for now.
        setTitle("The Maze of History!"); // Put Title on top of JFrame
        setResizable(false);
        setVisible(true);

        bodyPanel = new BodyPanel(this); // Body Panel
        controlPanel = new ControlPanel(bodyPanel); // Control Panel

        // Add controlPanel and bodyPanel to JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);

    }


}