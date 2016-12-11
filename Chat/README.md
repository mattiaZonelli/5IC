#CHAT ROOM (UDP) - Descrizione Informale
```
Autore : Marco Tasca
Data   : 8/12/2016
```
##Obiettivi raggiunti
  * Server
  * Client
  * Grafica (JavaFX) SOLO GRAFICA CLIENT
  * Concorrenza
  * Protocollo UDP
  * Memorizzazione DATABASE

###Obiettivi raggiunti non richiesti (Creatività)
  * Possibilià di inviare e scaricare immagini (jpg, png)

###Idee non realizzate
  * Visualizzazione utenti connessi
  * Messaggi tra Client (anche se la funzione "directMessage(message,client)" è stata creata)

##Descrizione generale
In questo progetto mi sono dedicato molto all'aspetto grafico della schermata dell'utente tramite JAVAFX e anche i fogli di stile CSS.
In particolare molto curata è il pannello di registrazione e login. La grafica è tutta all'interno di un solo file xml per motivi
pratici e gestione più veloce. Gli utenti hanno la possibilià di inviare messaggi e immagini (jpg, png), visualizzandone l'ora
a cui sono state mandate, oltre al nome e al contenuto del messaggio.

##Qualità progetto
Ho aggiunto nel modo più esaustivo possibile la documentazione all'interno del progetto. Quasi ogni funzione di tutti i file del progetto 
sono stati commentati tramite Javadoc. Ho cercato di rendere il programma il più flessibile e dinamico possibile creando molte funzioni
con un nome appropriato, dando la possibilità di apportare modifiche nel minor tempo possibile.

##Problemi
Ho avuto parecchi problemi nell'invio di immagini, però poi sono stati sistemati.

##Librerie Esterne
Per la creazione di un ottima grafica mi son serviti i componenti della libreria jfoenix e font-awesome, mentre per la gestione e 
l'interazione del database con le classi del progetto mi è servita la libreria sqlite.
Le librerie che mi sono servite per realizzare questo progetto sono state le seguenti:
```
jfoenix.jar
font-awesome.jar
sqlite-db.jar
```
