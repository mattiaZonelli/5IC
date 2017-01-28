package com.gallez.pool.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pool on 27/01/17.
 */

public class QuestionFileGrabber {
    public static ArrayList<Question> convertFileToQuestion(InputStream is){
        ArrayList<Question> questions = new ArrayList<>();
        BufferedReader file = new BufferedReader(new InputStreamReader(is));
        try {
            String line = file.readLine();
            while (line != null) {
                String [] fields = line.replaceAll("\"","").replaceAll("&#039","").replaceAll("&quot","").replaceAll("&ldquo","").split(",");
                questions.add(new Question(fields));
                line = file.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return questions;
    }
}
