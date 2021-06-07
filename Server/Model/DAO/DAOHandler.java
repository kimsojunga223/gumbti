package Server;

import Server.Model.DAO.SubDivisionDAO;
import Server.Model.DTO.SubDivisionDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOHandler
{
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String user = "JYP";
            String pw = "123";
            String url = "jdbc:oracle:thin:@localhost:1521:xe";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("DB 연결 성공");

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

    public static void main (String [] args)
    {
        getConnection();

        //중분류 id로 원래 이름 조회
        System.out.println("중분류 id로 원래 이름 조회:"); //오락
        String name=SubDivisionDAO.subDivisionOrigin_id(20);
        System.out.println(name);

        //중분류 id로 중분류 이름 조회
        System.out.println("중분류 id로 중분류 이름 조회: "); //실내운동
        name=SubDivisionDAO.subDivisionName_id(5);
        System.out.println(name);

        //중분류 id로 중분류 정보조회
        System.out.println("중분류 id로 중분류 정보조회: "); //5, 실내운동, null
        ArrayList<SubDivisionDTO> dto=SubDivisionDAO.subDivisionInfo_id(5);
        for(int i=0; i<dto.size(); i++)
        {
            System.out.println(dto.get(i).getSubdivision_id()+", "+dto.get(i).getSubdivision_name()+", "+dto.get(i).getOrigin_name());
        }

        //중분류 이름으로 원래 이름 조회 /외않되?
        System.out.println("중분류 이름으로 원래 이름 조회: 일식");//PC방....
        name=SubDivisionDAO.subDivisionOrigin_name("일식");
        System.out.println(name);

        //중분류 정보 조회
        System.out.println("중분류 정보 조회:");
        dto=SubDivisionDAO.subDivisionInfo();
        for(int i=0; i<dto.size(); i++)
        {
            System.out.println(dto.get(i).getSubdivision_id()+", "+dto.get(i).getSubdivision_name()+", "+dto.get(i).getOrigin_name());
        }

        //중분류 id 조회
        System.out.println("중분류 id 조회:");
        dto=SubDivisionDAO.subDivisionId();
        for(int i=0; i<dto.size(); i++)
        {
            System.out.println(dto.get(i).getSubdivision_id()+", "+dto.get(i).getSubdivision_name()+", "+dto.get(i).getOrigin_name());
        }

        //중분류 이름 조회
        System.out.println("중분류 이름 조회:");
        dto=SubDivisionDAO.subDivisionName();
        for(int i=0; i<dto.size(); i++)
        {
            System.out.println(dto.get(i).getSubdivision_id()+", "+dto.get(i).getSubdivision_name()+", "+dto.get(i).getOrigin_name());
        }

        //데이터 수 뽑기
        System.out.println("데이터 수:");
        int num=SubDivisionDAO.countSubDivision();
        System.out.println(num);

    }
}

