# Secondo Project (deadline 8 dicembre) : ChatRoom

Consegna Prevista 
- Realizzare **ChatRoom**: l'utente mediante il client dialoga con altri utenti con protocollo UDP 
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
Le richieste del prof. ovvero
- Il protocollo utilizzato è UDP.
- Il server gestisce le richieste in modo concorrente.
- Gli utenti si iscrivono e della loro iscrizione si tiene traccia in un DB SQLite (ovvero MySQL ovvero MariaDB).

**sono state rispettate**.

-Ho adottato ulteriori iniziative ovvero registrazione con password e criptazione dei dati.
-La chat non è solo in localhost ma pure in LAN (tra diversi PC).



Dentro alla cartella a questa cartella sono previsto ulteriori cartelle:
```
src> contiene tutte le classi elencate sopra.
doc> contiene la documentazione JavaDoc e diagramma delle classi e diagrammi per òa macchina a stati finiti
dist> contiene file eseguibile se si apre **Robotgraphics** si può utilizzare il programma senza compilarlo.
database> contiene il database (senza nessun dato).
jdbc> contiene il driver jdbc per sqlite

```

- Non sono stati inseriti alcuni file **.class** ed **.xml** di progetto NetBeans.

- Il progetto è stato realizzato con l'IDE **NetBeans IDE 8.1** e Java versione 8.


**Swapnil Paul, 07.12.2016**
