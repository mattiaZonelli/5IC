#Quiz
Prendendo in esame quanto richiesto, è stato realizzato un applicazione android che permettesse di rispondere alle domande di un quiz

##Funzionamento
L'applicazione ha utilizzato domande che sono state fornite da una API online (OpenTrivia) in formato JSON.
Le domande sono dunque state convertite in un formato CSV piu facile da gestire ed inserite all'interno di un database SQLite alla creazione della classe:
```
DatabaseHelper.java
```
Una volta prese le domande, esse vengono messe in un arraylist di oggetti
```
Question.java
```
Che contiene tutte le domande (E per motivi pratici implementa Serializable) e le possibili risposte che vengono mescolate ogni volta che viene creato l'oggetto, ed infine quella corretta.
A questo punto l'arraylist viene mescolato tramite il metodo
```
Collections.shuffle(List<?> list)
```
E passate all'activity successiva.
L'activity contenente le domande invece utilizza una struttura creata tramite l'ausilio di un ViewPager e dei Fragment che permettono una visualizzazione a "Carosello",con la possibilità di scrollare tra una domanda e l'altra tramite swipes.

Infine il numero delle risposte corrette viene passato indietro alla MainActivity che mostra un Toast scrivendo che il quiz è stato passato se si ha risposto correttamente ad almeno metà delle domande.

##Problemi

Ho riscontrato un problema nella colorazione della status bar che non si sa per qualche motivo rimane bianca
