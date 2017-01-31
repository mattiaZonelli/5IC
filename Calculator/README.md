Si carica anche il progetto calcolatrice sebbene non richiesto in quanto svolto interamente tutto il progetto "QUIZ" . <br>
Il progetto viene caricato perchè già svolto prima del cambio di specfiche, lo si tenga in considerazione se lo si ritiene opportuno.<br>


Il funzionamento è il seguente:
<ol>
<li>Vengono digitate le cifre e gli operatori desiderati.
<li>Quando si preme "=" si effettua il parsing della stringa ottenuta tramite l' algoritmo di Shunting Yard (<a href="http://eddmann.com/posts/shunting-yard-implementation-in-java/"> Vedere qui </a>). Non è stata svolta la codifica di tale algoritmo, ma solo degli aggiustamenti.
<li>Viene implementata anche la visuale landscape con opportuno salvataggio del risultato.
<li>La dimensione del font risulta inversamente proporzionale al logaritmo in base 10 della lunghezza della stringa, il calcolo viene opportunamente modificato se la stringa comunque supera il limite massimo di righe.
<li>Vengono adottate le opportune tecniche per mantenere l'integrità dell'algoritmo e la correttezza del risultato.
</ol>
