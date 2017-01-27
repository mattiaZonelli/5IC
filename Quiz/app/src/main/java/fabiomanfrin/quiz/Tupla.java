package fabiomanfrin.quiz;

/**
 * Created by Fabio on 25/01/2017.
 */
public class Tupla {
    private String question;
    private String answer;
    private String difficulty;

    public Tupla(String question, String answer,String difficulty){
        this.question=question;
        this.answer=answer;
        this.difficulty=difficulty;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
    public String getDifficulty(){return difficulty;}
}
