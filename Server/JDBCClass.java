package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCClass {
	private String driver;
	private String url;
	private String id;
	private String pw;
	private String dbName;
	private String[] msg;
	private Connection con;
	private PreparedStatement pre;
	private ResultSet rs;

	public JDBCClass(String id, String pw, String dbName) {
		this.id = id;
		this.pw = pw;
		this.dbName = dbName;
		this.driver = "org.mariadb.jdbc.Driver";
		this.url  = "jdbc:mariadb://localhost:3306/";
		this.msg  = new String[65536];
		Server_DB();
	}

	private void Server_DB() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + dbName, id, pw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] selects() throws SQLException { // select 부분
		int student_id[] = new int[128];
		String name[] = new String[128];
		String email_1[] = new String[128];
		String email_2[] = new String[128];
		String sql = "select * from student";
		pre = con.prepareStatement(sql);
		rs = pre.executeQuery();

		try  { 	
			int i = -1;
			try {
				while (rs.next()) {
					student_id[++i] = rs.getInt("student_id");
					name[i] = rs.getString("name");
					email_1[i] = rs.getString("EMAIL_1");
					email_2[i] = rs.getString("EMAIL_2");
					msg[i] = 1 + "/" + student_id[i] + "/" + name[i] + "/" + email_1[i] + "/" + email_2[i];
				}
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
			}
			i = -1;
		} catch (SQLException e) {
			System.out.println("query error");
		}
		finally {
			rs.close();
			pre.close();
			con.close();
		}
		return msg; // 클라이언트로 전송할 메세지 배열 msg 반환
	}	
}