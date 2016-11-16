/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.robot.protocol.Protocol;
import com.utils.database.DBConnector;
import com.utils.database.DBReader;
import com.utils.database.DBWriter;
import com.utils.database.Field;
import com.utils.database.Tuple;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicola
 */
public class Robot {

    private DBConnector databaseConnector;
    private DBReader databaseReader;
    private DBWriter databaseWriter;

    public final String ROBOT_NAME = "Pandora";

    public String userName;

    public final int TIMEOUT = 2;

    public ArrayList<String> topics;

    public final String[] DEFAULT_TOPICS;

    public ArrayList<String> preferences;

    private final int MAX_TOPICS;

    public final int ROBOT_AGE;

    public String placeOfLiving;

    public String stateOfLiving;

    private int age;

    public boolean ended;

    public boolean alreadyMemorized;

    private long id;

    private int topicCounter;

    private Tuple topicRow;
    private String topicName;
    public final String PANDORASSTORY = "Has your curiosity ever got you into trouble? Have you ever been so desperate to know a secret that you took no notice of a warning? All through history there are stories of people being told not to open doors, caskets, cupboards, gates and all sorts of other things and, in so many of the stories, the people just did not listen. One person who did not listen was Pandora. Her story comes from Ancient Greece and her curiosity brought a whole heap of trouble!\n"
            + "\n"
            + "In ancient Greece there were two brothers named Epimetheus and Prometheus. They upset the gods and annoyed the most powerful of all Gods, Zeus, in particular. This was not the first time humans had upset Zeus, and once before, as punishment, he had taken from humans the ability to make fire. This meant they could no longer cook their meat and could not keep themselves warm.\n"
            + "\n"
            + "However, Prometheus was clever and he knew that, on the Isle of Lemnos, lived Hephaestos, the blacksmith. He had a fire burning to keep his forge hot. Prometheus travelled to Lemnos and stole fire from the blacksmith. Zeus was furious and decided that humans had to be punished once and for all for their lack of respect.\n"
            + "\n"
            + "Zeus came up with a very cunning plan to punish the two brothers. With the help of Hephaestos, he created a woman from clay. The goddess Athena then breathed life into the clay, Aphrodite made her very beautiful and Hermes taught her how to be both charming and deceitful. Zeus called her Pandora and sent her as a gift to Epimetheus.\n"
            + "\n"
            + "His brother Prometheus had warned him not to accept any gifts from the gods but Epimetheus was completely charmed by the woman and thought Pandora was so beautiful that she could never cause any harm, so he agreed to marry her.\n"
            + "\n"
            + "Zeus, pleased that his trap was working, gave Pandora a wedding gift of a beautiful box. There was one very, very important condition however, that she must never opened the box. Pandora was very curious about the contents of the box but she had promised that she would never open it.\n"
            + "\n"
            + "All she could think about was; what could be in the box? She could not understand why someone would send her a box if she could not see what was in it. It seemed to make no sense at all to her and she could think of nothing else but of opening the box and unlocking its secrets. This was just what Zeus had planned.\n"
            + "\n"
            + "Finally, Pandora could stand it no longer. When she knew Epimetheus was out of sight, she crept up to the box, took the huge key off the high shelf, fitted it carefully into the lock and turned it. But, at the last moment, she felt a pang of guilt, imagined how angry her husband would be and quickly locked the box again without opening the lid and put the key back where she had found it. Three more times she did this until, at last, she knew she had to look inside or she would go completely mad!\n"
            + "\n"
            + "She took the key, slid it into the lock and turned it. She took a deep breath, closed her eyes and slowly lifted the lid of the box. She opened her eyes and looked into the box, expecting to see fine silks, gowns or gold bracelets and necklaces or even piles of gold coins.\n"
            + "\n"
            + "But there was no gleam of gold or treasure. There were no shining bracelets and not one beautiful dress! The look of excitement on her face quickly turned to one of disappointment and then horror. For Zeus had packed the box full of all the terrible evils he could think of. Out of the box poured disease and poverty. Out came misery, out came death, out came sadness - all shaped like tiny buzzing moths.\n"
            + "\n"
            + "The creatures stung Pandora over and over again and she slammed the lid shut. Epimetheus ran into the room to see why she was crying in pain. Pandora could still hear a voice calling to her from the box, pleading with her to be let out. Epimetheus agreed that nothing inside the box could be worse than the horrors that had already been released, so they opened the lid once more.\n"
            + "\n"
            + "All that remained in the box was Hope. It fluttered from the box like a beautiful dragonfly, touching the wounds created by the evil creatures, and healing them. Even though Pandora had released pain and suffering upon the world, she had also allowed Hope to follow them. Hope is always something worth fighting for.\n";

    public Robot() {
        topicCounter = 0;
        databaseConnector = new DBConnector("users");
        alreadyMemorized = false;
        DEFAULT_TOPICS = new String[]{"MUSIC", "READING", "MOVIES", "COOKING"};
        ended = false;
        ROBOT_AGE = 20;
        userName = "user";
        topics = new ArrayList<>();
        preferences = new ArrayList<>();
        for (String s : DEFAULT_TOPICS) {
            topics.add(s);
        }
        MAX_TOPICS = topics.size();

    }

    public String randomTopics() {
        return topics.remove((int) (Math.random() * 1000) % topics.size());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getROBOT_NAME() {
        return ROBOT_NAME;
    }

    public void addPreference(String what) {
        preferences.add(what);
    }

    public int getMAX_TOPICS() {
        return MAX_TOPICS;
    }

    public int availableTopics() {
        return topics.size(); //perchè faccio un remove
    }

    public synchronized void end() {
        ended = true;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setPlaceOfLiving(String placeOfLiving) {
        this.placeOfLiving = placeOfLiving;
    }

    public String getPlaceOfLiving() {
        return placeOfLiving;
    }

    public String getStateOfLiving() {
        return stateOfLiving;
    }

    public void setStateOfLiving(String stateOfLiving) {
        this.stateOfLiving = stateOfLiving;
    }

    public void registerUser(Long id, String ip, Integer port) {

        DBWriter writer = databaseConnector.newDBWriter();
        DBReader reader = databaseConnector.newDBReader();
        boolean exists = reader.exists(new Field("ID", id), "Socket_info");
        if (!exists) {
            Tuple t = new Tuple();
            t.insert("ID", id);
            t.insert("IP_ADDRESS", ip);
            t.insert("PORT", port);
            writer.write(t, "Socket_info");
        } else {
            Tuple t = new Tuple();
            t.insert("IP_ADDRESS", ip);
            t.insert("PORT", port);
            writer.update(t, "Socket_info", "ID = " + id);
            // se l'ha già trovato costruisci il robot precedente
            buildRobot(id);
            System.out.println("Set fields");
        }

    }

    private void setField(String fieldName, Object value) {
        System.out.println(fieldName);
        switch (fieldName.toUpperCase()) {
            case "NAME":
                setUserName((String) value);
                break;
            case "AGE":
                setAge(value == null ? -1 : (Integer) value);
                break;
            case "PLACEOFLIVING":
                setPlaceOfLiving((String) value);
                break;
            case "STATEOFLIVING":
                setStateOfLiving((String) value);
                break;
            case "SPORT":
                if ((value == null ? 0 : (Integer) value) == 1) {
                    addPreference(fieldName);
                }
                break;
            case "READING":
                if ((value == null ? 0 : (Integer) value) == 1) {
                    addPreference(fieldName);
                }
                break;

            case "MUSIC":

                if ((value == null ? 0 : (Integer) value) == 1) {
                    addPreference(fieldName);
                }
                break;

            case "MOVIES":
                if ((value == null ? 0 : (Integer) value) == 1) {
                    addPreference(fieldName);
                }
                break;
            case "COOKING":
                if ((value == null ? 0 : (Integer) value) == 1) {
                    addPreference(fieldName);
                }
                break;
        }
    }

    public void buildRobot(long id) {
        DBReader reader = databaseConnector.newDBReader();
        Tuple t = new Tuple();
        reader.read(t, "ID = " + id, "UserData");
        preferences.clear();
        for (Object o : t) {
            Field f = (Field) o;
            setField(f.getName(), f.getValue());
        }
        alreadyMemorized = true;
    }

    public boolean userDataWrite(String DBName) {
        boolean result = false;
        String control = "";
        try {
            control = databaseConnector.getConnection().getMetaData().getURL().substring(databaseConnector.getConnection().getMetaData().getURL().lastIndexOf("\\") + 1, databaseConnector.getConnection().getMetaData().getURL().lastIndexOf("."));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while writing into " + DBName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (control.equals("users")) {
            if (DBName.equals("UserData")) {
                DBWriter writer = databaseConnector.newDBWriter();
                Tuple t = new Tuple();
                t.insert("ID", id);
                t.insert("Name", userName);
                t.insert("Age", age);
                t.insert("PlaceOfLiving", placeOfLiving);
                t.insert("StateOfLiving", stateOfLiving);
                for (String s : preferences) {
                    t.insert(s.charAt(0) + s.substring(1).toLowerCase(), 1);
                }
                if (!t.contains(null)) {
                    if (alreadyMemorized) {
                        writer.update(t, DBName, "ID=" + this.getId());
                    } else {
                        writer.write(t, DBName);
                        alreadyMemorized = true;
                    }
                    result = true;
                } else {
                    result = false;
                }

            } else {
                result = false;
                throw new UnsupportedOperationException();
            }
        } else {
            this.setDatabaseConnector(DBConnector.change("users"));
            System.out.println("****************************************************************************************");
            result = userDataWrite(DBName);
            this.setDatabaseConnector(DBConnector.change(control));

        }
        return result;
    }

    public void writeData(String dBName, List<Field> fields, String whereCondition) {
        DBWriter writer = databaseConnector.newDBWriter();
        Tuple t = new Tuple();
        for (Field f : fields) {
            t.insert(f.getName(), f.getValue());
        }
        writer.update(t, dBName, whereCondition);

    }

    public void writeData(String dBName, Field field, String whereCondition) {
        DBWriter writer = databaseConnector.newDBWriter();
        Tuple t = new Tuple();
        t.insert(field.getName(), field.getValue());
        writer.update(t, dBName, whereCondition);

    }

    public Field readField(String dbName, String whereCondition, String fieldName) {
        DBReader reader = databaseConnector.newDBReader();
        Field f = null;
        //if (reader.exists(f, fieldName)) {
        Tuple row = new Tuple();
        reader.read(row, whereCondition, dbName);
        for (Object o : row) {
            Field field = (Field) o;
            if (field.getName().equals(fieldName)) {
                f = field;
            }
        }
        //}
        return f;
    }

    public String askCookingQuestions() {
        String output = "";
        System.out.println("TOPIC COUNTER: "+topicCounter);  
        switch (topicCounter) {
            case 0:
                output = "What's the name of the recipe you want to propose me?";
                break;
            case 1:
                output = "Oh. Great name, how do I have to cook it? ";
                break;
            case 2:
                output = "How does it taste? ";
                break;
            case 3:
                output = "Is it fattening? ";
                break;
            case 4:
                output = "Do you enjoy eating it? ";
                break;
        }
        return output;
    }

    public String askBookQuestions() {
        String output = "";
        System.out.println("TOPIC COUNTER: "+topicCounter);  
        switch (topicCounter) {
            case 0:
                output = "What's the title of the book? ";
                break;
            case 1:
                output = "And the author? ";
                break;
            case 2:
                output = "Great. What about the genre? ";
                break;
            case 3:
                output = "Can you give me a brief summary? ";
                break;
            case 4:
                output = "Do you like it? ";
                break;

        }
        return output;
    }

    public String askMusicQuestions() {
        String output = "";
        System.out.println("TOPIC COUNTER: "+topicCounter);  
        switch (topicCounter) {
            case 0:
                output = "How is this song named? ";
                break;
            case 1:
                output = "By whom was it sung? ";
                break;
            case 2:
                output = "Great. What about the genre? ";
                break;
            case 3:
                output = "So, in which year was it released? ";
                break;
            case 4:
                output = "I can't remeber the lyrics now, please write me some lines. ";
                break;
            case 5:
                output = "Oh. Now I remember. Toss me the video link, thanks :D ";
                break;
        }
        return output;
    }

    public String askMoviesQuestions() {
        String output = "";
        System.out.println("TOPIC COUNTER: "+topicCounter);     
        switch (topicCounter) {
            case 0:
                output = "What's the title? ";
                break;
            case 1:
                output = "Interesting. What about the genre? ";
                break;
            case 2:
                output = "Can you summarize me the story a little? ";
                break;
            case 3:
                output = "Oh. I'm going to watch the trailer for sure. Can you give me the link? ";
                break;
            case 4:
                output = "Do you like it? ";
                break;
        }
        return output;
    }

    public String ask() {
        String outputMessage = null;
        System.out.println("TOPIC NAME: "+topicName);
        if (topicName != null) {
            switch (topicName.toUpperCase()) {
                case "COOKING":
                    outputMessage = askCookingQuestions();
                    break;
                case "MOVIES":
                    outputMessage = askMoviesQuestions();
                    break;

                case "MUSIC":
                    outputMessage = askMusicQuestions();
                    break;

                case "READING":
                    outputMessage = askBookQuestions();
                    break;

            }
            //fai domande personalizzate per argomento corrente.
            //chiedi due commenti e server si occupa di inserire gli altri con joptionpane
        }
        return outputMessage;

    }

    public boolean learnCooking(String input) {
        boolean isEnded = false;
        switch (topicCounter) {
            case 0:
                topicRow = new Tuple();
                topicRow.addField(new Field("Name", input));
                break;
            case 1:
                topicRow.addField(new Field("Procedure", input));
                break;
            case 2:
                topicRow.addField(new Field("Taste/Flavours", input));
                break;
            case 3:
                topicRow.addField(new Field("Fattening", Protocol.isPositive(input)));
                break;
            case 4:
                topicRow.addField(new Field("UserLikes", Protocol.isPositive(input)?1:0));
                isEnded = true;
                topicRow.addField(new Field("RobotLikes", (int) (Math.random() * 2)));
                databaseConnector.newDBWriter().write(topicRow, "Cooking");
                break;
        }
        topicCounter++;
        return isEnded;
    }

    public boolean learnBook(String input) {
        boolean isEnded = false;
        switch (topicCounter) {
            case 0:
                topicRow = new Tuple();
                topicRow.addField(new Field("Title", input));
                break;
            case 1:
                topicRow.addField(new Field("Author", input));
                break;
            case 2:
                topicRow.addField(new Field("Genre", input));
                break;
            case 3:
                topicRow.addField(new Field("Plot", input));
                break;
            case 4:
                topicRow.addField(new Field("UserLikes", Protocol.isPositive(input)?1:0));
                isEnded = true;
                topicRow.addField(new Field("RobotLikes", (int) (Math.random() * 2)));
                databaseConnector.newDBWriter().write(topicRow, "Reading");
                break;
        }
        topicCounter++;
        return isEnded;
    }

    public boolean learnMovie(String input) {
        boolean isEnded = false;
        switch (topicCounter) {
            case 0:
                topicRow = new Tuple();
                topicRow.addField(new Field("Title", input));
                break;
            case 1:
                topicRow.addField(new Field("Genre", input));
                break;
            case 2:
                topicRow.addField(new Field("Plot", input));
                break;
            case 3:
                topicRow.addField(new Field("TrailerLink", input));
                break;
            case 4:
                topicRow.addField(new Field("UserLikes", Protocol.isPositive(input)));
                isEnded = true;
                topicRow.addField(new Field("RobotLikes", (int) (Math.random() * 2)));
                databaseConnector.newDBWriter().write(topicRow, "Movies");
                break;
        }
        topicCounter++;
        return isEnded;
    }

    public boolean learnMusic(String input) {
        boolean isEnded = false;
        switch (topicCounter) {
            case 0:
                topicRow = new Tuple();
                topicRow.addField(new Field("Title", input));
                break;
            case 1:
                topicRow.addField(new Field("Author", input));
                break;
            case 2:
                topicRow.addField(new Field("Genre", input));
                break;
            case 3:
                topicRow.addField(new Field("Year", input));
                break;
            case 4:
                topicRow.addField(new Field("Lyrics", input));
                break;
            case 5:
                topicRow.addField(new Field("YoutubeLink", input));
                isEnded = true;
                topicRow.addField(new Field("RobotLikes", (int) (Math.random() * 2)));
                databaseConnector.newDBWriter().write(topicRow, "Music");
                break;
        }
        topicCounter++;
        return isEnded;
    }

    public boolean learn(String input) {
        boolean ended = false;
        switch (topicName.toUpperCase()) {
            case "COOKING":
                ended = learnCooking(input);
                break;
            case "MOVIES":
                ended = learnMovie(input);
                break;

            case "MUSIC":
                ended = learnMusic(input);
                break;

            case "READING":
                ended = learnBook(input);
                break;

        }
        return ended;
    }

    public void setTopic() {
        topicRow = new Tuple();
        int index = (int) (Math.random() * preferences.size());
        topicName = preferences.remove(index);
        DBReader reader = databaseConnector.newDBReader();
        int proposed = 0;
        int cnt = (int) (Math.random() * 5) + 1;//da 1 a 5 tentativi
        int i = 0;
        do {
            reader.randomRead(topicRow, "UserLikes<>0", topicName);
            //per un numero casuale minore di 5 cerca una riga che non abbia proposed a true
            proposed = (int) topicRow.getField("Proposed").getValue();
            i++;
        } while (proposed == 1 && i < cnt);

    }

    public String getTopicName() {
        return topicName;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public String topicTalk() {
        String out = "";

        switch (topicName.toUpperCase()) {
            case "COOKING":
                out = proposeRecipe(topicRow);
                break;
            case "MOVIES":
                out = proposeMovie(topicRow);
                break;

            case "MUSIC":
                out = proposeSong(topicRow);
                break;

            case "READING":
                out = proposeBook(topicRow);
                break;

        }
        return out;
    }

    public DBConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public void setDatabaseConnector(DBConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    private void setProposed(String what, Tuple t) {
        this.writeData(what, new Field("Proposed", 1), "ID=" + t.getField("ID").getValue());
    }

    public int getTopicCounter() {
        return topicCounter;
    }

    public void resetTopicCounter() {
        topicCounter = 0;
    }

    private String proposeRecipe(Tuple t) {
        String out = "";
        if (topicCounter == 0) {
            out += "Listen. I've got here a recipe you may like. It's called " + t.getField("Name").getValue() + " . It's " + t.getField("Taste/Flavours").getValue();
            if (((int) (t.getField("Fattening").getValue())) == 1) {
                out += " but a little fattening ";
            } else {
                out += " and healthy ";
            }
            out += ". I'll tell you the procedure: \n" + t.getField("Procedure").getValue();
            setProposed("Cooking", t);
        } else if (topicCounter == 1) {
            out = "What do you think?";
        } else if (topicCounter == 2) {
            DBReader reader = databaseConnector.newDBReader();
            Tuple tuple = new Tuple();
            reader.read(tuple, "ID=" + t.getField("ID").getValue(), "Comments");
            if (tuple.isEmpty()) {
                out = "I haven't tried it already. Next time I'll tell you what I think if I remeber";
            } else {
                int randomColumn = (int) (Math.random() * 5) + 1;
                String opinion;
                if ((int) (tuple.getField("IsPositive").getValue()) == 1) {
                    opinion = (String) tuple.getField("Positive" + randomColumn).getValue();
                } else {
                    opinion = (String) tuple.getField("Negative" + randomColumn).getValue();
                }
                out = opinion;
            }
        } else {
            out = "Do you like this recipe?";
        }
        topicCounter++;
        return out;

    }

    private String proposeMovie(Tuple topicRow) {
        String out = "";
        if (topicCounter == 0) {
            out += "Hey. I wanna suggest you this movie. It's called \"" + topicRow.getField("Title").getValue() + "\". It's a " + topicRow.getField("Genre").getValue() + ".\n"
                    + "Here's the plot: \n" + topicRow.getField("Plot").getValue();
            setProposed("Movies", topicRow);
        } else if (topicCounter == 1) {
            out = "Give a glimpse to the trailer: " + topicRow.getField("TrailerLink").getValue() + "\nWhat do you think about it? ";
        } else if (topicCounter == 2) {
            DBReader reader = databaseConnector.newDBReader();
            Tuple tuple = new Tuple();
            reader.read(tuple, "ID=" + topicRow.getField("ID").getValue(), "Comments");
            if (tuple.isEmpty()) {
                out = "I haven't tried it already. Next time I'll tell you what I think if I remeber";
            } else {
                int randomColumn = (int) (Math.random() * 5) + 1;
                String opinion;
                if ((int) (tuple.getField("IsPositive").getValue()) == 1) {
                    opinion = (String) tuple.getField("Positive" + randomColumn).getValue();
                } else {
                    opinion = (String) tuple.getField("Negative" + randomColumn).getValue();
                }
                out = opinion;
            }
        } else {
            out += "Are you going to watch it? ";
        }
        topicCounter++;
        return out;
    }

    private String proposeSong(Tuple topicRow) {
        String out = "";
        if (topicCounter == 0) {
            out += "You may like this song. \n" + topicRow.getField("Title").getValue() + " by " + topicRow.getField("Author").getValue() + "\nIt was realeased in " + topicRow.getField("Year").getValue();
        } else if (topicCounter == 1) {
            if ((Integer) (topicRow.getField("Popular").getValue()) == 1) {
                out += "It's really popular. I bet you know this piece: ";
            } else {
                out += "It's not really popular but maybe you already listened to it. Here are a few of the lyrics: ";
            }
            out += "\n----------------------------------------------------------------\n" + topicRow.getField("Lyrics").getValue() + "\n----------------------------------------------------------------\n";
            out += "Do you know it?";
        } else if (topicCounter == 2) {
            DBReader reader = databaseConnector.newDBReader();
            Tuple tuple = new Tuple();
            reader.read(tuple, "ID=" + topicRow.getField("ID").getValue(), "Comments");
            if (tuple.isEmpty()) {
                out = "I haven't tried it already. Next time I'll tell you what I think if I remeber";
            } else {
                int randomColumn = (int) (Math.random() * 5) + 1;
                String opinion;
                if ((int) (tuple.getField("IsPositive").getValue()) == 1) {
                    opinion = (String) tuple.getField("Positive" + randomColumn).getValue();
                } else {
                    opinion = (String) tuple.getField("Negative" + randomColumn).getValue();
                }
                out = opinion;
            }
        } else {
            out = "Take a minute to listen to it. " + topicRow.getField("YoutubeLink").getValue() + "\nDo you like it? ";
        }
        topicCounter++;
        return out;
    }

//mettere libri. fare iterazione di argomenti e fare domande se non presente niente. In alternativa chiedere se propone alcuni argomenti e registrarli.
    private String proposeBook(Tuple topicRow) {
        String out = "";
        if (topicCounter == 0) {
            out = "Since you're a nerd as much as I am I want to adivise you " + topicRow.getField("Title").getValue() + " by " + topicRow.getField("Author").getValue() + "\n";
            out += "It's a " + topicRow.getField("Genre").getValue() + " story. In summary:  \n" + topicRow.getField("Plot").getValue();
        } else if (topicCounter == 1) {
            out = "What do you think?";
        } else if (topicCounter == 2) {
            DBReader reader = databaseConnector.newDBReader();
            Tuple tuple = new Tuple();
            reader.read(tuple, "ID=" + topicRow.getField("ID").getValue(), "Comments");
            if (tuple.isEmpty()) {
                out = "I haven't tried it already. Next time I'll tell you what I think if I remeber";
            } else {
                int randomColumn = (int) (Math.random() * 5) + 1;
                String opinion;
                if ((int) (tuple.getField("IsPositive").getValue()) == 1) {
                    opinion = (String) tuple.getField("Positive" + randomColumn).getValue();
                } else {
                    opinion = (String) tuple.getField("Negative" + randomColumn).getValue();
                }
                out = opinion;
            }
        } else {
            out = "Tell me if you like it, ok? :)";
        }
        topicCounter++;
        return out;
    }

    public boolean isAlreadyMemorized() {
        return alreadyMemorized;
    }

    public boolean isEnded() {
        return ended;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
