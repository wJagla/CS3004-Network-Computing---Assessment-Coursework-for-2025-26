import java.net.*;
import java.io.*;


public class WarehouseServerThread extends Thread {
	
	private Socket warehouseSocket = null;
	private SharedWarehouseState mySharedWarehouseState;
	private String clientType = "";
	private String clientID = "";
	
	public WarehouseServerThread(Socket warehouseSocket, SharedWarehouseState sharedObject) {
		this.warehouseSocket = warehouseSocket;
		this.mySharedWarehouseState = sharedObject;
	}
	
	public void run() {
		try {
			System.out.println("Initialising");
			PrintWriter out = new PrintWriter(warehouseSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(warehouseSocket.getInputStream()));
			String inputLine, outputLine;
			
			out.println("Please identify client type");
			clientType = in.readLine();
			System.out.println("Client type: " + clientType);
			if(!clientType.equals("Supplier")) {
				out.println("Please identify client ID");
				clientID = in.readLine();
			} else {
				clientID = "Supplier";
			}
			System.out.println("Client ID: " + clientID);

			
			while((inputLine = in.readLine()) != null) {
				try {
					mySharedWarehouseState.acquireLock();
					outputLine  = mySharedWarehouseState.processInput(clientType, inputLine);
					out.println(outputLine);
					mySharedWarehouseState.releaseLock();
				
					
				} catch(InterruptedException e) {
		    		  System.err.println("Failed to get lock when reading:"+e);
		    	  }
			}
			
			out.close();
			in.close();
			warehouseSocket.close();
			
		} catch (IOException e) {
		      e.printStackTrace();
	    }
	}
}
