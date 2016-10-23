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
Per limitare il numero di thread creati, usufruendo di altri thread che avevano completato i propri task (perciò in stato di idle), ho utilizzato la classe ThreadPoolExecutor.
####ThreadPoolExecutor
Questa classe permette di tenere in riserva un determinato numero di thread, da riutilizzare con altri tasks

```
new ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
```
*corePoolSize è il numero di thread che verranno tenuti in riserva (anche se in idle)
*maximumPoolSize è il numero di thread massimo che potranno essere utilizzati
*keepAliveTime è il tempo che viene utilizzato per la terminazione dei thread se la grandezza della poolSize è maggiore di corePoolSize
*TimeUnit è l'unità utilizzata per keepAliveTime
*workQueue sono i tasks in attesa di un thread

Inizialmente la PoolSize è a 0.
Se  PoolSize < corePoolSize, appena arriva un task viene creato un thread.
Se  PoolSize == corePoolSize, i task vengono messi in attesa in workQueue.
Se  workQueue è piena, verranno creati thread fino al raggiungimento di maximumPoolSize

Se PoolSize > corePoolSize, verranno terminati i thread in idle che sono in più (dopo che avranno aspettato keepAliveTime) affinché la poolSize ritorni minore di corePoolSize


