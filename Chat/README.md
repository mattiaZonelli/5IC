# Secondo Project (deadline 8 dicembre) : ChatRoom

Consegna Prevista 
- Realizzare **ChatRoom**: l'utente mediante il client collegandosi col server riesce a dialogare con altri utenti con protocollo UDP (il server invia tutti i pacchetti in modalità **broadcasting**). 
- Il server gestisce le richieste in modo concorrente.


Rispettando la consegna prevista dal prof. ho realizzato le seguenti classi
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

- Ho adottato ulteriori iniziative ovvero registrazione con password e criptazione dei dati.
- La chat non è solo in localhost ma pure in LAN (tra diversi PC).



Dentro alla cartella a questa cartella sono previsto ulteriori cartelle:
```
src> contiene tutte le classi elencate sopra.
doc> contiene la documentazione JavaDoc e diagrammi
dist> contiene i file eseguibili in .jar
database> contiene il database (senza nessun dato).
jdbc> contiene il driver jdbc per sqlite

```

- Non sono stati inseriti alcuni file **.class** ed **.xml** di progetto NetBeans.

- Il progetto è stato realizzato con l'IDE **NetBeans IDE 8.1** e Java versione 8.


Questo progetto l'avevo già cosegnato il 7/12 verso le 22.00, però l' 8/12 ho aggiunto e definito l'algoritmo e per sbaglio (già dal giorno precedente) avevo inserito il progetto nella cartella **ChatRoom**, anzichè **Chat**.



**Swapnil Paul, 07.12.2016**
