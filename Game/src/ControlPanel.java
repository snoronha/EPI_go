import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel implements ActionListener
{
    final String INSTRUCTIONS_CARD = "Instructions";
    final String GAME_CARD         = "Game";
    public JPanel controlPanel, bodyPanel;
    JButton instructionButton,gameButton;
    public ControlPanel(JPanel bdyPanel)
    {
        bodyPanel = bdyPanel;
        controlPanel= new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.WHITE);
        instructionButton = new JButton("Instructions");
        gameButton = new JButton("Game");
        instructionButton.addActionListener(this);
        gameButton.addActionListener(this);
        controlPanel.add(instructionButton);
        controlPanel.add(gameButton);
    }

    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(bodyPanel.getLayout());
        if (e.getSource() == instructionButton) {
            cl.show(bodyPanel, INSTRUCTIONS_CARD);
        } else
        {
            cl.show(bodyPanel, GAME_CARD);
        }
    }
}