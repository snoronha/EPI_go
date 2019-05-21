//class deals with the questions used in my game and implements radio buttons to do so
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QAPanel extends JPanel implements ActionListener
{
    GameCard gameCard;
    int isCorrect;
    JTextArea qaText = new JTextArea("Welcome to the qaPanel!" ,5,30);
    JRadioButton radioButton0, radioButton1, radioButton2, radioButton3;
    ButtonGroup buttonGroup;
    JButton submitButton;
    public QAPanel(GameCard gc)
    {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        // add question
        add(qaText, BorderLayout.NORTH);

        JPanel answers = new JPanel();
        radioButton0 = new JRadioButton("Option 1"); radioButton0.setActionCommand("0");
        radioButton1 = new JRadioButton("Option 2"); radioButton1.setActionCommand("1");
        radioButton2 = new JRadioButton("Option 3"); radioButton2.setActionCommand("2");
        radioButton3 = new JRadioButton("Option 4"); radioButton3.setActionCommand("3");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton0);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        answers.add(radioButton0);
        answers.add(radioButton1);
        answers.add(radioButton2);
        answers.add(radioButton3);

        add(answers, BorderLayout.CENTER);

        // add submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);                     //make sure player can click on buttons
        add(submitButton, BorderLayout.SOUTH);                     //adding buttons

        // reference to gameCard
        gameCard = gc;
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
            if ( buttonGroup.getSelection() != null)
            {
                String selection = buttonGroup.getSelection().getActionCommand(); // "0", "1", "2" or "3"
                if(selection.equals("0"))
                {
                    if (isCorrect == 0)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.showModal(true);
                    }
                    else
                    {
                        gameCard.bodyPanel.game.showModal(false);
                    }
                }
                if(selection.equals("1"))
                {
                    if (isCorrect == 1)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.showModal(true);
                    }
                    else
                    {
                        gameCard.bodyPanel.game.showModal(false);
                    }
                }
                if(selection.equals("2"))
                {
                    if (isCorrect == 2)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.showModal(true);
                    }
                    else
                    {
                        gameCard.bodyPanel.game.showModal(false);
                    }
                }
                if(selection.equals("3"))
                {
                    if (isCorrect == 3)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.showModal(true);
                    }
                    else
                    {
                        gameCard.bodyPanel.game.showModal(false);
                    }
                }
                gameCard.isAnswering = false;
            }
            else
            {
                System.out.println("Submit pressed: no option selected");
            }
        }
    }
    public void displayQA(QA qa)
    {
        isCorrect = qa.isCorrect;
        qaText.setText(qa.question);
        radioButton0.setText(qa.answers[0]);
        radioButton1.setText(qa.answers[1]);
        radioButton2.setText(qa.answers[2]);
        radioButton3.setText(qa.answers[3]);
        buttonGroup.clearSelection();
    }
}
