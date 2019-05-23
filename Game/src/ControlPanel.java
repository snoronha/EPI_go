import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener
{
    public BodyPanel bodyPanel;
    JButton instructionButton,gameButton, resetButton;                //buttons declared

    public ControlPanel(BodyPanel bdyPanel)
    {
        bodyPanel = bdyPanel;
        setLayout(new FlowLayout());                    //setting layout, background color
        setBackground(Color.WHITE);
        instructionButton = new JButton("Instructions");
        gameButton  = new JButton("Game");
        resetButton = new JButton("Reset");
        instructionButton.addActionListener(this);                     //make sure player can click on buttons
        gameButton.addActionListener(this);
        resetButton.addActionListener(this);

        add(instructionButton);                      //adding buttons
        add(gameButton);
        add(resetButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instructionButton)
        {                                           //showing instruction or game card depending on
            bodyPanel.showCard(bodyPanel.INSTRUCTIONS_CARD);
            bodyPanel.gameCard.stopTimer();
        }
        else if (e.getSource() == gameButton)
        {
            bodyPanel.showCard(bodyPanel.GAME_CARD);
            bodyPanel.gameCard.startTimer();
        }
        else
        {
            bodyPanel.showCard(bodyPanel.GAME_CARD);
            bodyPanel.gameCard.resetGame();
            bodyPanel.gameCard.startTimer();
        }
    }
}
