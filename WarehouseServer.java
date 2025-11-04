import java.net.*;
import java.io.*;



public class WarehouseServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket WarehouseServerSocket = null;
		boolean listening = true;
		String WarehouseServerName = "WarehouseServer";
		int WarehouseServerNo = 4545;
		
		int apples = 1000;
		int oranges = 1000;
		
		SharedWarehouseState ourSharedWarehouseState = new SharedWarehouseState(apples, oranges);
		
		try {
			WarehouseServerSocket = new ServerSocket(WarehouseServerNo);
		} catch (IOException e) {
			System.err.println("Could not start " + WarehouseServerName + " specified port.");
		      System.exit(-1);
		}
		
		System.out.println(WarehouseServerName + " started");
		
		while(listening) {
			new WarehouseServerThread(WarehouseServerSocket.accept(), ourSharedWarehouseState).start();
			System.out.println("New " + WarehouseServerName + " thread started");
		}
		WarehouseServerSocket.close();
	}

}
