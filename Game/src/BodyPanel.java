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

    /*
    public void paintComponent(Graphics g) {
        drawMaze(g);
    }

    public void drawMaze(Graphics g) {
        int xOffset = 60;
        int yOffset = 120;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (maze[i][j].getRight()) {
                    g.drawLine(xOffset + yOffset * i, xOffset + yOffset * j, xOffset + yOffset * i, xOffset + yOffset * (j + 1));
                }
                if (maze[i][j].getDown()) {
                    g.drawLine(xOffset + yOffset * i, xOffset + yOffset * j, xOffset + yOffset * (i + 1), xOffset + yOffset * j);
                }
            }
        }
    }
    */
}