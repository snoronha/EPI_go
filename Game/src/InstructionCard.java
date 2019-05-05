import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

public class InstructionCard extends JPanel
{
    JTextArea instructions = new JTextArea("You will have to navigate through a maze.\n" +
            "It will only have one pathway through and that is what you must follow.\n"+
            "If you get stuck, turn around and try a different way.\n"+
            "You will be asked questions which you must answer to continue with the maze.\n"+
            "If you get a question right, you keep going.If not, you go back to the last ckeckpoint(the previous question).\n"+
            "Use the 4 arrow keys to navigate through.\n"+
            "Enjoy!",10,50);
    public InstructionCard()
    {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        add(instructions);
    }
}
