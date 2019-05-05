import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class BodyPanel extends JPanel
{
    public BodyPanel()
    {
        setLayout(new CardLayout());
        setBackground(Color.CYAN);
    }
}
