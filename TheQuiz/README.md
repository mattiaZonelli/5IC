# App Android Quiz

Consegna Prevista 
- Realizzare **Android App Quiz**

Rispettando la consegna del prof. ho realizzato le seguenti classi

```
MainActivity.java> Gestisce scelta del primo o secondo quiz
activity_main.xml>  Documento in xml che contiene la grafica per la MainActivity.
QuizActivity.java> Gestisce le domande e le risposte V/F del quiz
activity_quiz.xml> Documento in xml che contiene la grafica per QuizActivity
RankActivity.java> Gestisce della classifica
activity_rank.xml> Documento in xml che contiene la grafica per RankActivity
```
Nel progetto ho incluso un database esterno di tipo **sqlite** dove sono registrate le domande e le risposte. 
L'utente inzia con inserendo un unsername e scegliendo se fare quiz 1 o 2. Dopo aver finito il quiz l'utente 
verrà registrato sul database e in una terza activity vedrà la classifica delle delle risposte mediante una **ListView** presi dal database.

il database si trova in TheQuiz\app\src\main\assets\quizdb

- Il progetto è stato realizzato da l'IDE **Android Studio 2.2.1** con versione API minima 19.

**Swapnil Paul, 26.01.2017**