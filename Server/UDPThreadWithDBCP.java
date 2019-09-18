package server;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class UDPThreadWithDBCP extends Thread {
	private DatagramSocket udpSoc;
	private DatagramPacket recvPac;
	private DatagramPacket sendPac;
	private DBCPClass dbcp;
	private String[] sendMsg;
	private Timer times;
	public UDPThreadWithDBCP( DatagramSocket udpSoc, DatagramPacket udpPac, DBCPClass dbcp ) throws Exception {
		this.udpSoc = udpSoc;
		this.recvPac = udpPac;
		this.dbcp = dbcp;
		this.times = new Timer();
		this.sendMsg = new String[65536];
	}
	
	private void InformationMsg() throws Exception {
		sendMsg = dbcp.selects();
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
			times.timeEnd();
			ReceiveMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		UDPServer.sec += times.Check();
	}
}