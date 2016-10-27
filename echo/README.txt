PROGETTO ECHO:
Ho creato due diversi file: "ClientChat" e "ServerChat".
	
	-ClientChat-
Il primo utilizza per comunicare un BufferedReader per ricevere le informazioni dalla classe ServerChat(dopo i controlli avvenuti nel lato Server) ed un PrintStream che invierà la stringa ricevuta in entrata.
Per la lettura da tastiera ho utilizzato un BufferedReader.
Una volta avviato il client verrà visualizzata la sequenza necessaria per la chiusura.
Chiuso il ClientChat non sarà più possibile scrivere a tastiera e nemmeno comunicare tra le due classi.

	-ServerChat-
Questa classe instaura innanzitutto una comunicazione con il lato client tramite la creazione di un ServerSocket(collegato nella porta 9999) e una volta riuscito (con successiva attribuzione della connessione ad un socket con "echoServer.accept()") procede con il dialogo; in caso contrario viene generata un'eccezione.
Qui,  come nella classe Client inizializzo i BufferedReader e Printwriter per cominicare.
Lo scopo della consegna era di accertarsi della corretta chiusura di un client attraverso 3 parole chiave utilizzate proprio da quest ultimo una dietro l'altra; per far ciò ho utilizzato un array di stringhe e mi sono concentrato sull'uguaglianza tra la stringa entrante e la prima dell'array.
Il ciclo è costituito da una variabile statica "gate" che rappresenta una sorta di cancello per la continuazione di comunicazione ed accertazione di avvenuta chiusura.
Tramite un "ClientSocket.close()", una volta trovata la sequenza,terminerà la comunicazione nel client.
