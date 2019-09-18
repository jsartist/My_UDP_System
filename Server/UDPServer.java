package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UDPServer {
	private int port;
	private byte[] byteData;
	private String sqlID;
	private String sqlPW;
	private String dbName;
	private DatagramSocket udpSoc;
	private DatagramPacket udpPac;
	private DBCPClass dbcp;
	private Scanner sc;
	public static long sec;

	public UDPServer() throws Exception {
		this.port = 3002;
		this.udpSoc = new DatagramSocket(port);	
		this.sc = new Scanner(System.in);
		this.sec = 0;
	}

	private int DBInformation() {
		int sel = 0;
		System.out.print("1. JDBC\t2.DBCP : ");
		sel = sc.nextInt();
		if(sel != 1 && sel != 2) {
			System.out.println("??");
			System.exit(1);
		}
			System.out.print("SQL ID: ");
			sqlID = sc.next();
			System.out.print("SQL PASSWORD: ");
			sqlPW = sc.next();
			System.out.print("DATABASE NAME: ");
			dbName = sc.next();

		return sel;
	}

	private String selDBConnect(int sel) throws Exception {
		String dbStyle = null;
		if (sel == 1) {
			dbStyle = "JDBC";
		}
		else if(sel == 2) {
			dbStyle = "DBCP";
			dbcp = new DBCPClass(sqlID, sqlPW, dbName);
		}
		else {
			System.exit(1);
		}
		System.out.println("DB 설정이 완료되었습니다. 선택된 DB Style: " + dbStyle);
		return dbStyle;
	}

	private void DynamicThread(String dbStyle) throws Exception {
		System.out.println("UDP Server 시작합니다.");
		if (dbStyle.equals("JDBC")) {
			while(true) {
				byteData = new byte[1024];
				makeThreadWithJDBC();
			}
		}
		else if (dbStyle.equals("DBCP")) {
			while(true) {
				byteData = new byte[1024];
				makeThreadWithDBCP();
			}
		}
		else {

		}
	}

	private void makeThreadWithJDBC() throws Exception {
		udpPac = new DatagramPacket(byteData, byteData.length);
		udpSoc.receive(udpPac);
		new UDPThreadWithJDBC(udpSoc, udpPac, sqlID, sqlPW, dbName).start();
		System.out.println("총 시간: " + sec  + "ms");
	}

	private void makeThreadWithDBCP() throws Exception {
		udpPac = new DatagramPacket(byteData, byteData.length);
		udpSoc.receive(udpPac);
		new UDPThreadWithDBCP(udpSoc, udpPac, dbcp).start();
		System.out.println("총 시간: " + sec  + "ms");
	}

	public void Handler() throws Exception {
		int selectDBStyle = 0;
		String dbStyle = null;
		selectDBStyle = DBInformation();
		dbStyle = selDBConnect(selectDBStyle);
		DynamicThread(dbStyle);
	}
}