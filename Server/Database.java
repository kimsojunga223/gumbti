package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String user = "GUMBTI_YG";
            String pw = "514";
            String url = "jdbc:oracle:thin:@localhost:1521:xe";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, pw);

        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : " + sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return conn;
    }
}
