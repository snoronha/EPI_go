import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener
{
    final String INSTRUCTIONS_CARD = "Instructions";
    final String GAME_CARD         = "Game";
    public String currentCard      = "";
    public JPanel bodyPanel;
    JButton instructionButton,gameButton;

    public ControlPanel(JPanel bdyPanel)
    {
        bodyPanel = bdyPanel;
        setLayout(new FlowLayout());
        setBackground(Color.WHITE);
        instructionButton = new JButton("Instructions");
        gameButton = new JButton("Game");
        instructionButton.addActionListener(this);
        gameButton.addActionListener(this);
        add(instructionButton);
        add(gameButton);
    }

    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(bodyPanel.getLayout());
        if (e.getSource() == instructionButton) {
            cl.show(bodyPanel, INSTRUCTIONS_CARD);
            currentCard = INSTRUCTIONS_CARD;
        } else
        {
            cl.show(bodyPanel, GAME_CARD);
            currentCard = GAME_CARD;
        }
    }
}
