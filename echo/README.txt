Ho modificato il sorgente Server.java tale che, se io inserisco per tre volte di fila una stessa parola, 
il server chiude la comunicazione(mediante il metoto .close()).
Ho utilizzato una variabile contatore che si incrementa ogni volta che registra che l'utente ha 
digitato la stessa parola. Quando contatore == 2 la comunicazione si interrompe