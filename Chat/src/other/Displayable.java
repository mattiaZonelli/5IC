package other;

import javafx.scene.control.TextArea;
import java.util.Scanner;

/**
 * Created by Rikkardo on 13/11/2016.
 */
public class Displayable {

    private TextArea area;
    private int maxChars;

    public Displayable(TextArea area, int maxChars){
        this.area = area;
        this.maxChars = maxChars;
    }

    public synchronized void display(String info){
        Scanner s = new Scanner(info);
        String line = "";
        String word = "";
        while(s.hasNext()){
            word = s.next();
            if(line.length()+word.length()>maxChars) {
                try {
                    area.appendText(line + "\n");
                } catch(NullPointerException e){
                    area.appendText(line + "\n");
                }
                line = word;
            } else{
                if(line.equals("")){
                    line = word;
                } else{
                    line += " " + word;
                }

            }
        }
        try {
            area.appendText(line + "\n");
        } catch(NullPointerException e){
            area.appendText(line + "\n");
        }
    }

    public synchronized void clean(){
        area.setText("");
    }

}
