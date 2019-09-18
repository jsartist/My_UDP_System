package server;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class UDPThreadWithJDBC extends Thread {
	private DatagramSocket udpSoc;
	private DatagramPacket recvPac;
	private DatagramPacket sendPac;
	private JDBCClass jdbc;
	private String[] sendMsg;
	private Timer times;
	public UDPThreadWithJDBC( DatagramSocket udpSoc, DatagramPacket udpPac, String id, String pw, String dbName) throws Exception {
		this.udpSoc = udpSoc;
		this.recvPac = udpPac;
		this.sendMsg = new String[65536];
		this.jdbc = new JDBCClass(id, pw, dbName);
		this.times = new Timer();
	}
	
	private void InformationMsg() throws Exception {
		sendMsg = jdbc.selects();
	}
	
	private void ReceiveMsg() throws Exception {
		int k = -1;
		while(sendMsg[++k] != null) {
			sendPac = new DatagramPacket(sendMsg[k].getBytes(), sendMsg[k].getBytes().length, recvPac.getAddress(), recvPac.getPort() );
			udpSoc.send(sendPac);
		}
	}
	
	public void run(){
		try {
			times.timeStart();
			InformationMsg();
			ReceiveMsg();
			times.timeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
		UDPServer.sec += times.Check();
	}
}