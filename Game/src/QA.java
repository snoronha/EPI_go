//finds out the correct answer and sets it to a boolean isCorrect
public class QA
{
    String question;
    String[] answers;
    int isCorrect;
    public QA(String q, String a0,String a1, String a2, String a3)
    {
        question = q;
        answers = new String[4];
        if (a0.indexOf("|") >= 0)
        {
            a0 = a0.substring(0, a0.indexOf("|"));
            isCorrect = 0;
        }
        if (a1.indexOf("|") >= 0)
        {
            a1 = a1.substring(0, a1.indexOf("|"));
            isCorrect = 1;
        }
        if (a2.indexOf("|") >= 0)
        {
            a2 = a2.substring(0, a2.indexOf("|"));
            isCorrect = 2;
        }
        if (a3.indexOf("|") >= 0)
        {
            a3 = a3.substring(0, a3.indexOf("|"));
            isCorrect = 3;
        }
        answers[0] = a0;
        answers[1] = a1;
        answers[2] = a2;
        answers[3] = a3;
    }
    public String getQuestion()
    {
        return question;
    }
    public String[] getAnswers()
    {
        return answers;
    }
    public boolean isAnswerCorrect(int index)
    {
        return index == isCorrect;
    }
    public void print()
    {
        System.out.printf("%s\n\t%s\n\t%s\n\t%s\n\t%s\n", question, answers[0], answers[1], answers[2],answers[3]);
    }
}

