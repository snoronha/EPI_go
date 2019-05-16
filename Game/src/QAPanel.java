//class deals with the questions used in my game and implements radio buttons to do so
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QAPanel extends JPanel implements ActionListener
{
    JTextArea qaText = new JTextArea("Welcome to the qaPanel!" ,5,30);
    JRadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    ButtonGroup buttonGroup;
    JButton submitButton;
    public QAPanel()
    {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        // add question
        add(qaText, BorderLayout.NORTH);

        JPanel answers = new JPanel();
        radioButton1 = new JRadioButton("Option 1");
        radioButton2 = new JRadioButton("Option 2");
        radioButton3 = new JRadioButton("Option 3");
        radioButton4 = new JRadioButton("Option 4");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        answers.add(radioButton1);
        answers.add(radioButton2);
        answers.add(radioButton3);
        answers.add(radioButton4);
        add(answers, BorderLayout.CENTER);


        // add submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);                     //make sure player can click on buttons
        add(submitButton, BorderLayout.SOUTH);                      //adding buttons
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
            if ( buttonGroup.getSelection() != null) {
                System.out.print("Submit pressed: selected = " + buttonGroup.getSelection());
            }
            else
            {
                System.out.println("Submit pressed: no option selected");
            }
        }
    }
    public void displayQA()
    {
        radioButton1.setText("Success!");
    }
}
