# Robot: deadline 18 novembre

Ecco quanto richiesto. 
- Si scrivano i sorgenti per le seguenti classi.
```
Server.java
Client.java
Protocol.java
```
in modo da realizzare un **robot**: l'utente mediante il client immagina di dialogare con un ipotetico interlocutore, il comportamento di tale interlocutore è descritto nel protocollo. 
- In fase di progetto si disegni il diagramme UML per la **macchina a stati finiti** (preferibilmente usando il software proposto dal docente) per il protocollo, tale diagramma va consegnato.
- Il server gestisce le richieste in modo concorrente.
- Va scritto il file 
```
README.md
```
con la descrizione informale del progetto.
- Il tutto va messo in una cartella Robot.
- Non consegnare file .class o xml di progetto NetBeans. In caso lavorare con il file
```
.gitignore
```
in locale.
- Qunto sopra è richiesto per raggiungere una valutazione di sufficienza abbondante. Ogni elemento ulteriore, ad iniziativa dello studente, sarà preso in considerazioni per le valutazioni eccellenti in sede di collquio orale.

# Chat: deadline 8 dicembre

Ecco quanto richiesto. 
- Si scrivano i sorgenti almeno per le seguenti classi.
```
Server.java
Client.java
```
in modo da realizzare una **chat** (chatroom): gli utenti mediante il client **grafico** realizzato con JavaFX (ovvero Java swing) sono in grado di dialogare fra loro in una chat (ovvero chatroom).
- Il protocollo utilizzato è UDP.
- Il server gestisce le richieste in modo concorrente.
- Gli utenti si iscrivono e della loro iscrizione si tiene traccia in un DB SQLite (ovvero MySQL ovvero MariaDB).
- Va scritto il file 
```
README.md
```
con la descrizione informale del progetto.
- Il tutto va messo in una cartella Chat.
- Non consegnare file .class o xml di progetto NetBeans. In caso lavorare con il file
```
.gitignore
```
in locale.
- Qunto sopra è richiesto per raggiungere una valutazione di sufficienza abbondante. Ogni elemento ulteriore, ad iniziativa dello studente, sarà preso in considerazioni per le valutazioni eccellenti in sede di collquio orale.

# Android QUIZ: deadline 27 gennaio 2017

Il progetto è obbligatorio per il raggiungimento di una valutazione sufficiente,

Il progetto comporta la creazione di tre `Activity`: la prima contiene due bottoni per lanciare rispettivamente la seconda e la terza. La seconda e la terza contengono un quiz con domande (V/F o a risposta multipla e di argomento a piacere), una volta terminato il quiz mediante un bottone viene terminata l'`Activity` e si torna alla prima `Activity` dando, cioè visualizzando, uno dei seguenti esiti: "hai risposto correttamente al primo QUIZ", "non hai risposto correttamente al primo QUIZ", "hai risposto correttamente al secondo QUIZ" e "non hai risposto correttamente al secondo QUIZ" (visualizzazione con `Toast` o su testo della prima `Activity`.

Suggeriamo per le widget: `Button`, `EditText`, `CheckBox`, con attenzione a `RadioButton` (vedi [qui](https://developer.android.com/guide/topics/ui/controls/radiobutton.html) per la docuentazione.

Chi usasse un DB SQLite per registrare i QUIZ verrò valutato per l'eccellenza. 

Il progetto va accompagnato dalla documentazione minima scritta nel file 
```
README.md
```
curare
```
.gitignore
```
nella propria cartella di lavoro (nella macchina di sviluppo).

# Android calcolatrice: deadline 1 febbraio 2017

Il progetto è facoltativo per chi intenda raggiungere valutazioni sufficienti, obbligatorio per chi intenda raggiungere valutazioni oltre la sufficienza a meno che non lavori col DB SQLite nel progetto precedente.

Il progetto comporta la realizzazione di una calcolatrice (non scientifica).
Il progetto va accompagnato dalla documentazione minima scritta nel file 
```
README.md
```
curare
```
.gitignore
```
nella propria cartella di lavoro (nella macchina di sviluppo).
