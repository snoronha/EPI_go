//class deals with the questions used in my game and implements radio buttons to do so
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QAPanel extends JPanel implements ActionListener
{
    GameCard gameCard;
    int isCorrect;
    JTextArea qaText = new JTextArea("" ,1,10);
    JRadioButton radioButton0, radioButton1, radioButton2, radioButton3;
    ButtonGroup buttonGroup;
    JButton submitButton;
    public boolean correctChosen = false;
    public int counter = 0;
    public QAPanel(GameCard gc)
    {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // add question
        add(qaText, BorderLayout.NORTH);

        JPanel answers = new JPanel(new GridLayout(2,2));
        radioButton0 = new JRadioButton(""); radioButton0.setActionCommand("0");
        radioButton1 = new JRadioButton(""); radioButton1.setActionCommand("1");
        radioButton2 = new JRadioButton(""); radioButton2.setActionCommand("2");
        radioButton3 = new JRadioButton(""); radioButton3.setActionCommand("3");

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
        JPanel submitPanel = new JPanel(new GridLayout(1, 3));
        submitPanel.add(new JLabel(""));
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);                     //make sure player can click on buttons
        submitButton.setSize(new Dimension(80, 30));
        submitButton.setBackground(Color.BLUE);
        submitButton.setOpaque(true);
        submitPanel.add(submitButton);
        submitPanel.add(new JLabel(""));
        add(submitPanel, BorderLayout.SOUTH);  //adding buttons

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
                        gameCard.bodyPanel.game.dialog.setText("Yes! You got it right!");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                        correctChosen = true;
                    }
                    else
                    {
                        gameCard.bodyPanel.game.dialog.setText("Sorry. Please try again.");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                    }
                }
                if(selection.equals("1"))
                {
                    if (isCorrect == 1)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.dialog.setText("Yes! You got it right!");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                        correctChosen = true;
                    }
                    else
                    {
                        gameCard.bodyPanel.game.dialog.setText("Sorry. Please try again.");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                    }
                }
                if(selection.equals("2"))
                {
                    if (isCorrect == 2)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.dialog.setText("Yes! You got it right!");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                        correctChosen = true;
                    }
                    else
                    {
                        gameCard.bodyPanel.game.dialog.setText("Sorry. Please try again.");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                    }
                }
                if(selection.equals("3"))
                {
                    if (isCorrect == 3)
                    {
                        gameCard.removeMazeEdge(gameCard.edgeI, gameCard.edgeJ, gameCard.isEdgeDown);
                        gameCard.bodyPanel.game.dialog.setText("Yes! You got it right!");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                        correctChosen = true;
                    }
                    else
                    {
                        gameCard.bodyPanel.game.dialog.setText("Sorry. Please try again.");
                        gameCard.bodyPanel.game.dialog.setVisibility(true);
                        counter++;
                    }
                }
                gameCard.isAnswering = false;
                System.out.println("Counter = " + counter + " correctChosen = " + correctChosen);
                if(correctChosen)
                {
                    if(counter == 1)
                    {
                        gameCard.score += 100;
                    }
                    else if(counter == 2 )
                    {
                        gameCard.score += 50;
                    }
                    else if(counter == 3 )
                    {
                        gameCard.score += 25;
                    }
                    else if(counter == 4)
                    {
                        gameCard.score += 0;
                    }
                    correctChosen = false;
                }

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
