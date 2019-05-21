// Show instructions on how to play the Maze game
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class InstructionCard extends JPanel
{
    public InstructionCard()
    {
        setBackground(Color.CYAN);
        setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintInstructions(g);
    }

    public void paintInstructions(Graphics g)
    {
        // Change x, y coordinates to be in terms if WIDTH, HEIGHT
        g.setColor(Color.ORANGE);
        g.fillRect(100, 50, 600, 600);
        g.setColor(Color.GREEN);
        g.fillRect(110, 60, 580, 580);
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Instructions for the Maze Game", 150, 150);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("You will have to navigate through a maze from Start to Finish.", 150, 200);
        g.drawString("It can have many pathways through that you can follow.", 150, 250);
        g.drawString("Navigate using the arrow keys.", 150, 300);
        g.drawString("If you get stuck, you will need to answer a question to proceed.", 150, 350);
        g.drawString("On a correct answer the barrier you hit disappears and you can proceed.", 150, 400);
        g.drawString("You will get points for each correct answer.", 150, 450);
        g.drawString("When you reach the end, you will receive a congratulations comment!", 150, 500);
    }

}
