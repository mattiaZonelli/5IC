##Disclaimer

Versione **NON** definitiva e che potrà essere soggetta a cambiamenti 
fino a martedì

##Descrizione procedimento risolutivo

Una volta stabilti i punti terminali della comunicazione, l'utente potrà 
interagire con il server tramite gli stream.
Il server all'interno della variabile "wordSequence", terrà traccia 
delle parole che giungono nello stream di input.
Ogni volta che al server viene inoltrata la parola presente in 
"wordSequence", viene incrementato il contatore "stopCounter" che una 
volta giunto a 3 interromperà la comunicazione con il socket connesso.
Quando "stopCounter" arriverà a 3:
1) Verrà settata la variabile "stopped" del server a true, così il 
server terminerà la comunicazione con il client connesso, e si potrà 
mettere in ascolto di eventuali altri client.

2) Il server notificherà al client della terminazione della 
comunicazione inviando la stringa "exit" tramite l'OutputStream.

Il client una volta che avrà ricevuto la stringa "exit" dal server, 
setterà la propria variabile "stopped" a true, in modo da interrompere 
la comunicazione con l'altro punto terminale.

Non è necessario effettuare la chiusura dei socket perché, essendo il 
codice all'interno del blocco try catch, verranno chiusi automaticamente
