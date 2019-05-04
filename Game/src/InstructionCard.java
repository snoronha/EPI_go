import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
public class InstructionCard
{
    public JPanel instructionCard;
    JTextArea instructions = new JTextArea("You will have to navigate through a maze.\n\n" +
            "It will only have one pathway through and that is what you must follow.\n\n"+
            "If you get stuck, turn around and try a different way.\n\n"+
            "You will be asked questions which you must answer to continue with the maze.\n\n"+
            "If you get a question right, you keep going.If not, you go back to the last ckeckpoint(the previous question).\n\n"+
            "Use the 4 arrow keys to navigate through.\n\n"+
            "Enjoy!",10,50);
    public InstructionCard()
    {
        instructionCard = new JPanel(new BorderLayout());
        instructionCard.setBackground(Color.RED);
        instructionCard.add(instructions);
    }
}
