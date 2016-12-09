# Secondo Project: ChatRoom

Consegna Prevista 
- Realizzare **ChatRoom**: l'utente mediante il client dialoga con altri utenti con protocollo UDP 
- Il server gestisce le richieste in modo concorrente.
- Registazione dei vari utenti, in un database


Rispettando la consegna prevista dal prof. ho realizzato le seguenti classi disposti in vari package
```
Client.java> classe che gestisce l'utente.
Server.java> classe che gestisce il server in UDP.
FXMLClientController.java> Controlla la grafica del cliente.
FXMLServerController.java> Controlla la grafica del server.
ClientFXMLDoc.xml> Documento in xml che contiene la grafica del cliente. 
ServerFXMLDoc.xml> Documento in xml che contiene la grafica del server. 
ChatRoom.java> Avvia grafica client **Contiene il MAIN per il client**.
ServerGraphics.java> Avvia grafica server **Contiene il MAIN per il server**.
ConnectionDB.java> Gestisce il database (sqlite)
```
Le richieste del prof. ovvero:

- Il protocollo utilizzato è UDP.
- Il server gestisce le richieste in modo concorrente.
- Gli utenti si iscrivono e della loro iscrizione si tiene traccia in un DB SQLite (ovvero MySQL ovvero MariaDB).

**sono state rispettate**.

```
ULTERIORI INIZIATIVE:
```

- registrazione con password e criptazione dei dati.
- La chat non è solo in localhost ma pure in LAN (tra diversi PC).

Dentro alla cartella sono previste ulteriori cartelle:
```
src> contiene tutte le classi elencate sopra.
doc> contiene la documentazione JavaDoc e diagrammi
dist> contiene i file eseguibili in .jar
database> contiene il database (senza nessun dato).
jdbc> contiene il driver jdbc per sqlite

```
# Come funziona il programma

```
SERVER
```
Si avvia la grafica del server che si trova nella classe **ServerGraphics.java** la quale mediante la classe FXMLServerController.java gestisce **Server.java**.
Dove si può inizializzare un server di tipo UDP che resta in ascolto. Appena arriva un pacchetto dal Client, verifica il tipo del pacchetto, che può essere di tipo registrazione di un nuovo utente, login del utente oppure o un normale messaggio. 
Il server appena riceve un pacchetto di tipo messaggio, lui invia il messaggio a tutti gli utenti connessi.

- Per la registazione e login il processo è un po' diverso.

Quando riceve un pacchetto di tipo registrazione il server verifica se esiste o meno l'utente, e se non esiste lo inserisce nel database **ChatRoomDB.db** mediante l'appoggio della classe **ConnectionDB.java**
Per quanto riguarda il login verifica se esiste l'utente e se le credenziali sono giuste.


```
CLIENT
```

Si avvia la grafica del client che si trova nella classe **ChatRoom.java** la quale mediante la classe FXMLClientController.java gestisce **Client.java**.
Appena si avvia la grafica conpare la schermata di login/registrazione, l'utente può desiderare di creare un chat in localhost (che non avrebbe molto senso) oppure creare la chat in LAN, dove al posto di localhost, li verrà chiesto di
inserire l'indirizzo IP del **Server**, poi li verrà chiesto l'username e password, in tutte i due i casi login/registazione queste informazioni verranno mandate al server in modo criptato e il server dopo aver verficato le varie condizioni permettera o meno 
di far accedere l'utente alla **Chatroom**. Dopo 3 tentativi di login falliti si chiuderà automaticamente la schermata del login. Appena si inserisce un nuovo utente tutti gli altri lo sapranno e se si vuole uscire dalla ChatRoom si può pure scrivere close o chiudi.


- Non sono stati inseriti alcuni file **.class** ed **.xml** di progetto NetBeans.

- Il progetto è stato realizzato con l'IDE **NetBeans IDE 8.1** e Java versione 8, jdk1.8.0_60.


Questo progetto l'avevo già cosegnato il 7/12 verso le 22.00, però l' 8/12 ho aggiunto e definito l'algoritmo e per sbaglio (già dal giorno precedente) avevo inserito il progetto nella cartella **ChatRoom**, anzichè **Chat**.
9/12 update solo del **README.md**


**Swapnil Paul, 07.12.2016**
