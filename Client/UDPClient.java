package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient  {
	private String ip;
	private int port;
	private byte[] recvBuf;	
	private DatagramSocket udpSoc;
	private DatagramPacket sendPac;
	private DatagramPacket recvPac;

	public UDPClient(String ip, String port) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
		this.recvBuf = new byte[65536];
		recvPac = new DatagramPacket(recvBuf, recvBuf.length);
		try {
			udpSoc = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private void sendMsg() throws Exception {
		for( int k = 0; k < 1000; k++) {
			sendPac = new DatagramPacket("1".getBytes(), "1".getBytes().length, InetAddress.getByName(ip), port);
			udpSoc.send(sendPac);
			udpSoc.receive(recvPac);
			Thread.sleep(20);
		}
	}

	public void UDPHandler() {
		try {
			sendMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}