import javax.swing.JFrame;
import java.awt.*;

public class GardenGrows extends JFrame {

    public static void main(String[] args) {
        // Create a Game
        GardenGrows gg = new GardenGrows();
        gg.setSize(1000, 500);
        gg.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public GardenGrows() {
        setTitle("Garden Grows"); // Put Title on top of JFrame
        setResizable(false);
        setVisible(true);

        Garden g = new Garden();
        add(g);

    }
}