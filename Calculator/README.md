Si carica anche il progetto calcolatrice sebbene non richiesto in quanto svolto interamente tutto il progetto "QUIZ" . <br>
Il progetto viene caricato perchè già svolto prima del cambio di specfiche, lo si tenga in considerazione se lo si ritiene opportuno.<br>


Il funzionamento è il seguente:
1. Vengono digitate le cifre e gli operatori desiderati.
2. Quando si preme "=" si effettua il parsing della stringa ottenuta tramite l' algoritmo di Shunting Yard (<a href="http://eddmann.com/posts/shunting-yard-implementation-in-java/"> Vedere qui </a>)
3. Viene implementata anche la visuale landscape con opportuno salvataggio del risultato.
4. La dimensione del font risulta inversamente proporzionale al logaritmo in base 10 della lunghezza della stringa, il calcolo viene opportunamente modificato se la stringa comunque supera il limite massimo di righe.
5. Vengono adottate le opportune tecniche per mantenere l'integrità dell'algoritmo e la correttezza del risultato.
