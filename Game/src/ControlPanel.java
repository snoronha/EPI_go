import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener
{
    public BodyPanel bodyPanel;
    JButton instructionButton,gameButton;                //buttons declared

    public ControlPanel(BodyPanel bdyPanel)
    {
        bodyPanel = bdyPanel;
        setLayout(new FlowLayout());                    //setting layout, background color
        setBackground(Color.WHITE);
        instructionButton = new JButton("Instructions");
        gameButton = new JButton("Game");
        instructionButton.addActionListener(this);                     //make sure player can click on buttons
        gameButton.addActionListener(this);
        add(instructionButton);                      //adding buttons
        add(gameButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instructionButton)
        {                                           //showing instruction or game card depending on
            bodyPanel.showCard(bodyPanel.INSTRUCTIONS_CARD);
        } else
        {
            bodyPanel.showCard(bodyPanel.GAME_CARD);
        }
    }
}
