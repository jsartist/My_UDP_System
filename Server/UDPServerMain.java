package server;

public class UDPServerMain {

	public static void main(String[] args) throws Exception {
		UDPServer showTime = new UDPServer();
		showTime.Handler();
	}
}