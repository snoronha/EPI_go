// class sets layout and adds control and body panels to screen
//LIST OF THINGS I NEED TO DO
//add visuals for GameCard screen
//question feedback - relate to scoring system
//edit scoring system based on questions
//make new levels, make new questions, make GameModePanel - pending time
//video
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.GridLayout;
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
        d.setSize(300, 200); // setsize of dialog
        d.setLocationRelativeTo(this);
        // create a label

        JPanel panel = new JPanel(new GridLayout(1,3));
        panel.add(new JLabel("          "));

        JLabel label = new JLabel("");
        if(isSuccess)
        {
            label.setText("Good job! You got it correct!");
        }
        else
        {
            label.setText("Not quite. Please try again.");
        }
        d.add(label, BorderLayout.CENTER);
        panel.add(new JLabel(""));

        d.setVisible(true); // set visibility of dialog
    }
}