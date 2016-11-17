Viene realizzato quanto seguito:

*un server che contiene un ascoltatore di richieste di connessione il quale per ogni connessione identifica l'utente e avvia una macchina a stati finiti personalizzata
*un protocollo che definisce il comportamento della macchina a stati finiti ed interpreta i messaggi ricevuti
*la macchina a stati finiti con memoria
*il client che invia i messaggi e riceve le risposte
*le classi accessorie

Il progetto è stato realizzato con interfaccia grafica java.swing (tramite l'editor grafico di Netbeans) e la memoria è rappresentata dal database costruito con il DBMS Sqlite (utilizzo di jdbc).
Si è preferito sviluppare il progetto in maniera da fornire un assaggio su tutte le potenzialità del software.

Nella cartella "Documentazione" è presente il diagramma UML dell'automa a stati finiti

E' stato scritto il javadoc e il codice opportunamente commentato.

N.B Il file "token.dat" rappresenta il codice univoco che identifica l'utente.
Il database contiene al momento una riga per tabella, per non creare confusione durante lo sviluppo, ma può essere popolato tramite l'uso del programma stesso.



17/11/2016,
Nicola Pasqualetto
