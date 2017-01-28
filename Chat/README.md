# Chat UDP
Attenendomi a quanto richiesto, ho realizzato una chat udp.

## UDP
Per realizzare questa chatroom è stato utilizzato il protocollo UDP.
Vi è dunque la classe:
```
Client.java
```
Che crea dei DatagramPacket, e li invia in broadcast a tutto il gruppo di ip che è memorizzato nel client.
Dunque ogni client che sarà in ascolto riceverà il pacchetto e lo visualizzerà.
# Grafica
Chiedo venia per l'orripilante stile grafico, ma è il massimo che sono riuscito a fare.
Mi sono cimentato all'utilizzo di javaFX, e ho voluto suddividere la grafica in 3 Stage:
* Quello di login
* Quello di registrazione
* Quello della chatroom vera e propria

Chiaramente tutti dotati di proprio file XML.

# Registrazione al database
Essa avviene per mezzo della classe:
```
DBUtility.java
```
Essa implementa metodi che mi sono stati fondamentali per la realizzazione della chat.
Ho deciso di optare per una verifica di login a step: verificando inizialmente se il nickname è presente nel db, e successivamente controllando la password.
Se uno dei due (o entrambi) risultassero errati al controllo, viene visualizzato un messaggio di errore affianco ad una (o entrambe) delle TextView.
Stessa cosa vale per la registrazione che controlla l'esistenza del nickname nel db, e che quindi (essendo un attributo unique) ferma la registrazione.

# Server

Avevo inizialmente optato per una versione in cui il server distribuiva le informazioni a tutti i client """""""connessi""""""", o per meglio dire in ascolto, ma poi mi sono reso conto che i pacchetti venivano recepiti lo stesso anche senza l'ausilio di questa classe, rendendo la classe:
```
Server.java
```
definitivamente inutile.

# Problemi
Conosco l'esistenza sostanzialmente di un problema: ovvero quella della gestione della lista che mostra i client attualmente connessi.
Infatti, la lista non viene aggiornata correttamente, visualizzando solo i client che si sono messi in ascolto dopo di noi, piccolo problema che è possibile risolvere con una rivisitazione del funzionamento della lista.

# Librerie
Oltre al driver JDBC, essendo totalmente negato in campo di design, mi sono appoggiato ad un css denominato:
```
materialFX.css
```
che prometteva di portare il material design di google, su javaFX.
Nonostante ciò, sono riuscito a creare una grafica orribile.
