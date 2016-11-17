# Project: Robot

Consegna Prevista 
- Realizzare **Robot**: l'utente mediante il client immagina di dialogare con un ipotetico interlocutore, il comportamento di tale interlocutore è descritto nel protocollo. 
- Il server gestisce le richieste in modo concorrente.

Rispettando la consegna prevista dal prof. ho realizzato le seguenti classi
```
Client.java> classe che gestisce l'utente.
Server.java> classe che gestisce il server insieme al alla classe protocol tutto in modo conccorente.
Protocol.java> classe che contiene tutto il protocollo del robot.
ClientGraphics.java> grafica per l'utente.
RobotGraphics.java> grafica per il server ed è pure la classe che **contiene il metodo MAIN**.
FXMLClientController.java> Controlla la grafica del cliente.
FXMLServerController.java> Controlla la grafica del server.
ClientFXMLDoc.xml> Documento in xml che contiene la grafica del cliente. 
ServerFXMLDoc.xml> Documento in xml che contiene la grafica del server. 

```
Seguendo la traccia della consegna si sono rispettati i punti fondamentali della consegna ovvero
Il dialogo con l'interlocutore, creazione del UML per la macchina a stati finiti e gestione delle 
richieste in modo concorrente.

Ulteriori iniziative sono state adottate dallo studento ovvero creazione della grafica in JavaFX e in certi casi
si salva le informazioni temporaneamente.

- Il protocollo preve un dialogo tra ipotetica intelligenza artificiale, il server manda affermazioni o domande
per dialogare con l'utente, è previsto pure una limitata pare dove l'utente può fare limitate domande.
- Il protocollo contiene più o meno 20 stati (Alcuni stati principali e il resto sono sotto stati).

Dentro alla cartella a questa cartella sono previsto ulteriori cartelle:
```
src> contiene tutte le classi elencate sopra.
doc> contiene la documentazione JavaDoc e diagramma delle classi e diagrammi per òa macchina a stati finiti
dist> contiene file eseguibile se si apre **Robotgraphics** si può utilizzare il programma senza compilarlo.

```
- Non sono stati inseriti alcuni file **.class** ed **.xml** di progetto NetBeans.

- Il progetto è stato realizzato con l'IDE **NetBeans IDE 8.1** e Java versione 8.

**Swapnil Paul, 17.11.2016**
