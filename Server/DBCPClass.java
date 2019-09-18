package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPClass {
	private static String id;
	private static String pw;
	private static String dbName;
	private String[] msg;
	private static BasicDataSource datasource;
	private Connection con;
	private PreparedStatement prestmt;
	private ResultSet rs;

	public DBCPClass(String id, String pw, String dbName) throws Exception {
		this.id = id;
		this.pw = pw;
		this.dbName = dbName;
		msg  = new String[65536];
		datasource = DBCPClass.getDataSource();
	}

	private static BasicDataSource getDataSource() {
		if (datasource == null) {
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName("org.mariadb.jdbc.Driver"); //드라이버 적재
			ds.setUrl("jdbc:mariadb://localhost:3306/" + dbName); //DB 주소
			ds.setUsername(id); //ID
			ds.setPassword(pw); //PW
			ds.setMinIdle(4); //커넥션 최소 갯수 설정 (default: 0)
			ds.setMaxIdle(50); //커넥션 풀에 반납할 때 최대로 유지될 수 있는 커넥션 개수(default: 8)
			ds.setMaxOpenPreparedStatements(180); //최대 개방 statement 수 설정
			datasource = ds; 
		}
		return datasource; //datasource 리턴
	}

	public String[] selects() throws SQLException { // select 부분
		int student_id[] = new int[128];
		String name[] = new String[128];
		String email_1[] = new String[128];
		String email_2[] = new String[128];
		String sql = "select * from student"; 
		con = datasource.getConnection();
		prestmt = con.prepareStatement(sql);
		rs = prestmt.executeQuery();

		try  {   			
			int i = 0;
			try {
				while (rs.next()) {
					student_id[i] = rs.getInt("student_id");
					name[i] = rs.getString("name");
					email_1[i] = rs.getString("EMAIL_1");
					email_2[i] = rs.getString("EMAIL_2");
					msg[i] = 1 + "/" + student_id[i] + "/" + name[i] + "/" + email_1[i] + "/" + email_2[i];
					i++;
				}
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
			}
			i = 0;
		} catch (SQLException e) {
			System.out.println("query error");
		}
		finally {
			rs.close();
			prestmt.close();
			con.close();
		}
		return msg;
	}
}