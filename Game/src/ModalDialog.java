import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class ModalDialog
{
    public JLabel label;
    public JDialog dialog;

    public ModalDialog(Game g)
    {
        // create a dialog Box
        dialog = new JDialog(g, "Maze Game Popup");
        dialog.setSize(240, 100); // setsize of dialog
        dialog.setLocationRelativeTo(g);

        label = new JLabel("");
        dialog.add(label);
    }

    public void setText(String text)
    {
        label.setText("           " + text);
    }

    public void setVisibility(boolean isVisible)
    {
        dialog.setVisible(isVisible); // set visibility of dialog
    }
}
