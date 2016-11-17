#Robot
##Descrizione Protocollo
Il protocollo da me descritto rappresenta un shop online di scarpe specialistiche.
L'utente una volta connesso avrà la possibilità di scegliere se visualizzare il catalogo.
Digitando no, al client verrà proposto di digitare il Marchio della scarpa, successivamente il protocollo si occuperà di cercare nel database le diverse scarpe.
A quel punto il protocollo chiederà diversi dati all'utente tra cui nome, cognome e indirizzo che verranno memorizzati nella classe 
```
User
```
ed infine verrà mostrato lo "scontrino".

##Libreria Utilizzata
Essendo i dati memorizzati su database SQLITE, ho utilizzato la libreria sqlite per effettuare le query.
Inoltre ho creato una classe che rendesse più agevole ricavare le informazioni dal database, e l'ho nominata:
```
DBUtility
```
