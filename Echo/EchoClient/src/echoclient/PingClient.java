package echoclient;

public class PingClient implements Runnable {

	boolean ping = true;
	
	@Override
	public void run() 
	{
		
	}
	public boolean isConnected()
	{
		return ping;
	}

}
