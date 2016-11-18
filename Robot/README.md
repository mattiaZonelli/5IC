#ROBOT - Descrizione Informale
```
Autore : Marco Tasca
Data   : 18/11/2016
```
##Descrizione Generale
Il progetto da me consegnato ha le principali funzionalità richieste: Server, Client, Protocol. Ho aggiunto la grafica tramite JavaFX e
il tool Scene Builder sia per il Server che per il Client. Inoltre ho aggiunto la concorrenza, quindi più Client possono connettersi allo 
stesso Server.

##Descrizione Protocollo
La realizzazione del protocollo si basa sull'utilizzo dei file Json e Text. Il file Json mi ha permesso di avere un zona dove riporre i
percorsi dei file con delle opportune opzioni. Tramite librerie esterne [VEDI FINE] è stato quasi immeddiata la ricerca e l'estrapolazione
dei dati da oggetti Json, anche chiamati JsonObject. La principale scelta che mi ha portato ad usare questo tipo di formato file è stata 
sicuramente la possibilità di poter accedere a tutte le domande presenti nei file con un solo accesso ad un file Json.

JSON prima di caricare i dati :
```
"OBJECT": {
      "state": "NUMBER_STATE",
      "path" : "FILE_PATH",
      "type" : "FILE_TYPE"
}
```

JSON dopo aver caricato i dati :
```
"OBJECT": {
      "state": "NUMBER_STATE",
      "path" : "FILE_PATH",
      "type" : "FILE_TYPE"
      "question": ["TEXT", "TEXT"],
      "answer": ["TEXT", "TEXT"],
      "exception-question": ["TEXT", "TEXT"],
      "exception-answer": ["TEXT", "TEXT"]
}
```

##AutoLearn
Data la moltitudine di frasi componibili, ho dato la possibilità al Robot di "imparare" frasi e risposte, date da un utente o chiunque
voglia. Ciò rende i file più popolati, alzando la possibilità che il Robot risponda nel migliore dei modi.

##Librerie Esterne
L'utilizzo di file Json in Java deve essere supportato da librerie scaricabili a parte. Le librerie che mi sono servite per realizzare
questo progetto sono state le seguenti:
```
commons-lang.jar
json-simple.jar
gson.jar
```
