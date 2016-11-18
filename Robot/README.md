IMPORTANTE: Guardare il progetto SimpleDialogue (IntelliJ project)

Informazioni informali sul progetto

-Autore: Lucentini Riccardo

-Data ricevuta consegna: 22/10/2016

-Data consegna progetto: 18/11/2016

-Monte ore di lavoro: circa 25

-Descrizione generale: Come da consegna, questo progetto include funzionalità di Server e di Client (gestiti concorrentemente).
Gli elementi aggiuntivi non richiesti sono stati principalmente due: la grafica, sviluppata tramite JFXSceneBuilder, è molto semplice
ed intuitiva (il testo mostrato nelle finestre non svolge alcuna funzione); e il sistema di analisi dei messaggi, del quale, 
realizzato uno scheletro di base, è facilissimo aggiungere funzionalità. 
Altre aggiunte di poco conto potrebbero essere l' apertura di un URL sul browser di default lato Client in determinate situazioni.

-Esempio di funzionamento:
1)Apertura Server
2)Lancio Server, lancio ServerListener
3)Apertura Client
4)Inserisco IP del Server sul Client, se passo i controlli mi connetto al Server
5)Creo un protocollo pr il nuovo Client
6)Dopo lo scambio di conferme, il Client può iniziare a trasmettere, e il Server gli rispondera
7)Disconnessione di Server o Client

-Elementi non originali: A parte il pattern utilizzato per la gestione concorrente dei Clients, e alcune funzionalità, come appunto la
soprascritta apertura del browser, o la regex utilizzata per la validazione dell' IP, il codice è assolutamente originale.

-N.B.: L' utilizzo di file .xml per il salvataggio dati è riconducibile semplicemente alla facilità di scrittura nell' IDE, ma il loro 
utilizzo non presenta differenze da quello di file di testo normali. 

-Note: La popolazione dei file è la parte più leggera del lavoro, e se questi non sono completi o esaustivi è decisamente semplice 
migliorarli. Il tutto è ulteriormente agevolato dal salvataggio delle conversazioni coi vari utenti: rileggendole si troveranno
eventuali imperfezioni e/o migliorie da apportare.
In conclusione, codice più semplice e chiaro possibile, e sistema di analisi dei messaggi modificabile molto rapidamente.

-UML: Si trova allegato il file state_diagram.mdj contenente un diagramma degli stati il più esaustivo possibile.

