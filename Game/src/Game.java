// class sets layout and adds control and body panels to screen
//LIST OF THINGS I NEED TO DO
//question feedback - right or wrong
//results panel
//edit scoring system
//make new levels, make new questions, make GameModePanel - pending time
//video
import java.awt.BorderLayout;
import javax.swing.*;

public class Game extends JFrame
{
    private BodyPanel bodyPanel;
    private ControlPanel controlPanel;


    public static void main(String[] args)
    {
        // Create a Game
        Game g = new Game();
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
        bodyPanel = new BodyPanel(this);

        // Control Panel
        controlPanel = new ControlPanel(bodyPanel);


        // Add controlPanel and bodyPanel to JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);

    }

    public void showModal(boolean isSuccess)
    {
        // create a dialog Box
        JDialog d = new JDialog(this, "dialog Box");
        // create a label
        JLabel label;
        if(isSuccess)
        {
            label = new JLabel("Good job! You got it right!");
        }
        else
        {
            label = new JLabel("Not quite. Please try again");
        }
        d.add(label);

        // setsize of dialog
        d.setSize(300, 200);
        // set visibility of dialog
        d.setVisible(true);
    }
}