package fabiomanfrin.quiz;

import java.util.ArrayList;

/**
 * Created by Fabio on 26/01/2017.
 */
public class Risultato {
    private ArrayList<Tupla> q;
    private ArrayList<String> a;
    private int correct;
    Risultato(ArrayList<Tupla> questions,ArrayList<String> answer,int correct){
        q=questions;
        a=answer;
        this.correct=correct;
    }
    public String getRisultato() {
        String risultato="";
        for(int i=0;i<q.size();i++){
            risultato=risultato+(i+1)+") "+q.get(i).getQuestion()+"\n"+"\n"+q.get(i).getAnswer()+",\t"+a.get(i)+"\n"+"\n";
        }
        return risultato;
    }
}
