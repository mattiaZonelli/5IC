package robot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import robot.Server.User;

/**
 *
 * @author marco
 */
public final class Protocol {

    User user;

    Scanner scanner, tastiera;
    String mainPath = "src/file/";
    String key, pathFromFile, type, oldS;

    ArrayList data = new ArrayList();
    ArrayList<String> questions, answers, exceptionQuestions, exceptionAnswers,
            array, phrases, dialogue, words, noAnswers;
    JSONParser parser;
    JSONObject fileJsonObject, allJsonObject, singleJsonObject;

    boolean notFind = true;
    int i, oldState, state;
    Date now;
    SimpleDateFormat dateformat;

    ArrayList<String> ID = new ArrayList<>(Arrays.asList(
            "[QUESTION]", "[ANSWER]", "[EXCEPTION-QUESTION]",
            "[EXCEPTION-ANSWER]"
    ));

    String finalString;

    public Protocol(User user) throws IOException {
        state = 0;
        this.user = user;
        loadData();

        try (FileWriter file = new FileWriter(mainPath + "json/file1.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(fileJsonObject.toJSONString());
            String prettyJsonString = gson.toJson(je);
            file.write(prettyJsonString);
        }

    }

    public void loadData() {
        parser = new JSONParser();
        try {
            fileJsonObject = (JSONObject) parser.parse(new FileReader(mainPath + "json/file.json"));
        } catch (IOException | ParseException ex) {
            System.out.println("File [" + mainPath + "json/file.json] non trovato.");
        }
        allJsonObject = (JSONObject) fileJsonObject;

        for (Iterator iterator = allJsonObject.keySet().iterator(); iterator.hasNext();) {
            key = (String) iterator.next();
            singleJsonObject = (JSONObject) allJsonObject.get(key);
            type = (String) singleJsonObject.get("type");
            pathFromFile = (String) singleJsonObject.get("path");

            getDataAndStore();

            //Populate file
            i = 0;
            for (String id : ID) {
                String temp = (String) id.toLowerCase().replaceAll("[^A-Za-z-]", "");
                singleJsonObject.put(temp, data.get(i));
                i++;
            }

        }
    }

    public void getDataAndStore() {
        try {
            scanner = new Scanner(new File(mainPath + pathFromFile));
            array = new ArrayList();
            data.clear();
        } catch (FileNotFoundException ex) {
            System.out.println("File [" + mainPath + pathFromFile + "] non trovato.");
        }

        if (type.equals("suriettivo")) {
            for (i = 0; scanner.hasNext(); i++) {
                String d = scanner.nextLine();
                if (ID.contains(d) && i != 0) {
                    data.add(array.clone());
                    array.clear();
                } else if (i != 0) {
                    array.add(d);
                }
            }
            data.add(array.clone());
            array.clear();
        } else {
            questions = new ArrayList();
            answers = new ArrayList();
            exceptionQuestions = new ArrayList();
            exceptionAnswers = new ArrayList();

            for (i = 0; scanner.hasNext(); i++) {
                String d = scanner.nextLine();

                int q = d.indexOf(ID.get(0)) + 10;
                int a = d.indexOf(ID.get(1)) + 8;

                questions.add(i, d.substring(q, a - 8));
                answers.add(i, d.substring(a, d.length()));
            }

            data.add(questions);
            data.add(answers);
            data.add(exceptionQuestions);
            data.add(exceptionAnswers);
        }
    }

    public String state(String s) {
        notFind = true;
        analyze(s);
        switch (state) {
            case 0:
                break;
            case 1:
                if (user.getNameUser().isEmpty()) {
                    finalString += ", come ti chiami ?";
                    user.setNameUser("user");
                } else {
                    finalString = "Bellissimo nome " + s;
                    user.setNameUser(s);
                    state = 0;
                }
                break;
            case 2:
                now = new Date();
                dateformat = new SimpleDateFormat("HH:mm.ss");
                finalString += " " + dateformat.format(now);
                state = 0;
                break;
            case 3:
                now = new Date();
                dateformat = new SimpleDateFormat("dd MMMM yyyy");
                finalString += " " + dateformat.format(now);
                state = 0;
                break;
            case 4:
                if (notFind) {
                    autoLearn(oldS, s);
                    finalString = "Grazie del tuo contributo !";
                    state = 0;
                    oldState = 0;
                    notFind = false;
                }
                break;
            default:
                break;
        }
        return finalString;
    }

    public void analyze(String s) {
        if (!s.equals("")) {
            s = s.replaceAll("\\?", " ?.");
            if (oldState != 4) {
                phrases = new ArrayList(Arrays.asList(s.toLowerCase().split("[.,;:]")));
                dialogue = new ArrayList();
                research();
                makeAnswer();

                if (notFind) {
                    oldS = s;
                    singleJsonObject = (JSONObject) allJsonObject.get("noAnswer");
                    if (state != 1) {
                        state = Integer.parseInt((String) singleJsonObject.get("state"));
                        oldState = state;
                    }
                    noAnswers = (ArrayList) singleJsonObject.get(ID.get(1).toLowerCase().replaceAll("[^A-Za-z-]", ""));
                    finalString = WordUtils.capitalize(user.getNameUser().toLowerCase())
                            + " " + noAnswers.get((int) (Math.random() * noAnswers.size())).toLowerCase()
                            + ", digitami una risposta così ne terrò traccia";
                    notFind = false;
                }
            }
        } else {
            finalString = "Dimmi qualcosa dai";
        }
    }

    public void research() {
        for (String phrase : phrases) {
            notFind = true;
            for (Iterator iterator = allJsonObject.keySet().iterator(); iterator.hasNext() & notFind;) {
                key = (String) iterator.next();
                singleJsonObject = (JSONObject) allJsonObject.get(key);

                type = (String) singleJsonObject.get("type");
                questions = (ArrayList) singleJsonObject.get(ID.get(0).toLowerCase().replaceAll("[^A-Za-z-]", ""));
                answers = (ArrayList) singleJsonObject.get(ID.get(1).toLowerCase().replaceAll("[^A-Za-z-]", ""));
                exceptionQuestions = (ArrayList) singleJsonObject.get(ID.get(2).toLowerCase().replaceAll("[^A-Za-z-]", ""));
                exceptionAnswers = (ArrayList) singleJsonObject.get(ID.get(3).toLowerCase().replaceAll("[^A-Za-z-]", ""));

                matchAnswer(phrase, questions, answers, 0);
                if (notFind) {
                    matchAnswer(phrase, exceptionQuestions, exceptionAnswers, 1);
                }
            }
        }
    }

    public void matchAnswer(String s, ArrayList<String> q, ArrayList<String> a, int exception) {
        i = 0;
        for (String question : q) {
            boolean temp = exception == 0 ? qualityIndex(s, question) : s.contains(question);
            if (temp) {
                if (type.equals("suriettivo")) {
                    state = Integer.parseInt((String) singleJsonObject.get("state"));
                    dialogue.add(a.get((int) (Math.random() * a.size())));
                } else {
                    state = Integer.parseInt((String) singleJsonObject.get("state"));
                    dialogue.add(a.get(i));
                }
                notFind = false;
                break;
            }
            i++;
        }
    }

    public boolean qualityIndex(String s, String question) {
        int j = 0;
        words = new ArrayList(Arrays.asList(s.split(" ")));
        for (String word : words) {
            if (question.contains(word)) {
                j++;
            }
        }
        return (100 / words.size()) * j >= 80;
    }

    public void makeAnswer() {
        for (i = 1; i < dialogue.size(); i++) {
            dialogue.set(i, dialogue.get(i).toLowerCase());
        }
        finalString = StringUtils.join(dialogue, " comunque ");
    }

    public void autoLearn(String s, String s1) {
        String output = "[QUESTION]" + s + "[ANSWER]" + s1;
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(mainPath + "text/autoLearn.txt", true));
            out.write('\n');
            out.write(output);
            out.close();
        } catch (IOException ex) {}
        
    }

}
