package Server.Model.DAO;

import Server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

	public DivisionDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		}
	}

	//분류id로 분류이름 조회
	public static String diviName(int division_id){
		String name = null;
    	PreparedStatement pstmt =null;
        Connection conn = null;
        ResultSet rs = null;

		String SQL = "SELECT DIVISION_NAME FROM DIVISION WHERE DIVISION_ID=?";
		try {
			conn = Database.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, division_id);
            rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return name;
	}
}
