package Server.Model.DAO;

import Server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//버스정류장
public class BusstopsDAO {

	public BusstopsDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		}
	}
	// 법정동명을 순위대로 저장한 ArrayList를 반환하는 함수
	  public static ArrayList<String> getRankDistrictList() {
		 ArrayList<String> rankResult = new ArrayList<String>();	// 순위대로 지역을 저장할 ArrayList
		Connection conn = null; 
	        PreparedStatement pstm = null; 
	        ResultSet rs = null;  
	        
		        try {
		            	//SQL 결과[ 법정동명 | 해당 순위 ] 나옴 1위부터 정렬 
		        	//(공동순위 시 공동된 수만큼 띄우고 순위 차례로 나옴)
		       	 	String SQL = "SELECT BUS_DISTRICT,RANK() OVER (ORDER BY cnt DESC) AS RANK FROM (SELECT bus_district, COUNT(*) cnt FROM BUSSTOPS GROUP BY bus_district)" ;
		       	 	conn =Database.getConnection();
		              pstm = conn.prepareStatement(SQL);
		              rs = pstm.executeQuery();

		          while(rs.next()) {	// 근데 공동 순위라면..? 어떻게 처리하는게 좋을까...?
		            	 String district = rs.getString(1);	// 지역명 가져옴
		            	 rankResult.add(district); // ArrayList에 저장
		              }
		            
		        } catch (SQLException sqle) {
		            System.out.println("SELECT문에서 예외 발생");
		            sqle.printStackTrace();
		            
		        }finally{
		            // DB 연결을 종료한다.
		            try{
		                if ( rs != null ){rs.close();}   
		                if ( pstm  != null ){pstm .close();}   
		                if ( conn != null ){conn.close(); }
		            }catch(Exception e){
		                throw new RuntimeException(e.getMessage());
		            }
		            
		        }
		    return rankResult;
	    }
	  
	  // 해당 법정동명의 순위를 반환하는 함수
	  public int getRankDistrict(String districtName) {	// 순위가 궁금한 법정동명 입력
		  	int rank = 0 ; // 반환하는 순위를 저장할 변수
			Connection conn = null; 
	        PreparedStatement pstm = null; 
	        ResultSet rs = null;  
	        
		        try {
		            //SQL 결과 [ 해당 순위 ] 나옴
		        	//(공동순위 시 공동된 수만큼 띄우고 순위 차례로 나옴)
		        	String SQL = "SELECT RANK "
		        			+ "FROM (SELECT BUS_DISTRICT, RANK() OVER (ORDER BY CNT DESC) AS RANK "
		        			+ "FROM (SELECT BUS_DISTRICT, COUNT(*) CNT FROM PARK  GROUP BY BUS_DISTRICT)) "
		        			+ "WHERE BUS_DISTRICT='"
		        			+ districtName
		        			+ "' ";
		        	
		        	  conn = Database.getConnection();
		              pstm = conn.prepareStatement(SQL);
		              rs = pstm.executeQuery();

		              while(rs.next()) {	// 근데 공동 순위라면..? 어떻게 처리하는게 좋을까...?
		            	rank = rs.getInt(1);	// 해당순위를 담음
		              }
		            
		        } catch (SQLException sqle) {
		            System.out.println("SELECT문에서 예외 발생");
		            sqle.printStackTrace();
		            
		        }finally{
		            // DB 연결을 종료한다.
		            try{
		                if ( rs != null ){rs.close();}   
		                if ( pstm  != null ){pstm .close();}   
		                if ( conn != null ){conn.close(); }
		            }catch(Exception e){
		                throw new RuntimeException(e.getMessage());
		            }
		            
		        }
		    return rank;
	    }
}
