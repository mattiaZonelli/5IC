##Descrizione procedimento risolutivo

Attenendomi a quanto richiesto dalla specifiche, è stato creato un server Echo che risponde al client con ciò che riceve in input.

##Server
Il server una volta creato chiederà all'utente la porta di ascolto e di digitare la sequenza di parole che il server dovrà ricevere affinché la connessione venga chiusa.
La sequenza verrà dunque splittata e memorizzata in un array di stringhe (wordSequence).
Non appena il server riceve la prima parola della sequenza wordSequence, incrementerà un indice, che continuerà ad essere incrementato fino ad arrivare alla lunghezza dell'array.
Una volta raggiunto il massimo, saranno state inserite tutte le parole necessarie per la disconnessione, quindi il server appenderà un carattere speciale di terminazione alla fine della
stringa che viene inviata al client ed interrompe la connessione con quest'ultimo (setta variabile stopped), per dedicarsi ad un altro client.

##Client
Il client una volta digitato ip e porta del server, riceverà la sequenza di parole da scrivere per disconnettersi dal server e potrà comunicare con esso.
Se dovesse trovare come ultimo carattere della stringa ricevuta dal server un '\0' interrompe la comunicazione con il server (setta la propria variabile stopped).

##Ipotesi aggiuntive
Il server di norma è un componente che riceve più richieste contemporaneamente.
Perciò ho introdotto questa feature utilizzando i Thread.
EchoServer, una volta in run, si limiterà ad accettare le connessioni.
Per ogni un client che si connette, EchoServer crea un oggetto ClientListener (memorizzato nella lista clients) che ascolterà il singolo client.
Sarà dunque client listener a gestire la comunicazione e ad inviare lo stop della connessione al client una volta digitata la sequenza di parole.

