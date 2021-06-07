package Server.Model.DAO;

import Network.Protocol;
import Server.Database;
import Server.Model.DTO.AnswersDTO;
import java.sql.*;
import java.util.ArrayList;

//답변
public class AnswersDAO
{
    public AnswersDAO()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.");
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println("DB 드라이버 로딩 실패: "+cnfe.toString());
        }
    }

    /*질문 아이디로 답변 내용 조회*/
    public static ArrayList<AnswersDTO> answerContents(int questionId)
    {
        ArrayList<AnswersDTO> answerList=new ArrayList<>();

        PreparedStatement pstmt = null;
        Connection conn=null;
        ResultSet rs=null;

        //질문 아이디로 답변 내용 구하는 SQL
        String SQL="SELECT ANSWER_CONTENT FROM ANSWER WHERE QUESTION_ID=?";

        try
        {
            conn= Database.getConnection();
            pstmt=conn.prepareStatement(SQL);
            pstmt.setInt(1, questionId);

            rs=pstmt.executeQuery();

            while (rs.next())
            {
                AnswersDTO dto=new AnswersDTO(rs.getString(1));
                answerList.add(dto);
            }

        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();;
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(pstmt!=null)
                {
                    pstmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }

            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return answerList;
    }

    /*질문 id로 답변 id 조회*/
    public static ArrayList<AnswersDTO> answerId(int questionId)
    {
        ArrayList<AnswersDTO> IdList= new ArrayList<>();

        PreparedStatement pstmt = null;
        Connection conn=null;
        ResultSet rs=null;

        //질문 아이디로 답변 아이디 구하는 SQL
        String SQL="SELECT ANSWER_ID FROM ANSWER WHERE QUESTION_ID=?";

        try
        {
            conn=Database.getConnection();
            pstmt=conn.prepareStatement(SQL);
            pstmt.setInt(1,questionId);

            rs=pstmt.executeQuery();

            while (rs.next())
            {
               AnswersDTO dto=new AnswersDTO(rs.getInt(1));
               IdList.add(dto);
            }

        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();;
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(pstmt!=null)
                {
                    pstmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }

            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return IdList;
    }

    /*질문 id로 답변 id, 내용 조회*/
    public static ArrayList<AnswersDTO> answerData(int questionId)
    {
        //답변 정보를 담을 Arraylist
        ArrayList<AnswersDTO> answersList= new ArrayList<>();

        PreparedStatement pstmt = null;
        Connection conn=null;
        ResultSet rs=null;

        //질문 아이디로 답변 정보 구하는 SQL
        String SQL="SELECT ANSWER_ID, ANSWER_CONTENT FROM ANSWER WHERE QUESTION_ID=?";

        try
        {
            conn=Database.getConnection();
            pstmt=conn.prepareStatement(SQL);
            pstmt.setInt(1, questionId);

            rs=pstmt.executeQuery();

            while (rs.next())
            {
                AnswersDTO dto=new AnswersDTO(rs.getInt(1), rs.getString(2));
                answersList.add(dto);
            }

        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();;
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(pstmt!=null)
                {
                    pstmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }

            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return answersList;
    }

    /*질문id로 중분류id 조회*/
    public static int[] subDivisionId(int questionId)
    {
        int[] subDivision_id = new int[0];

        PreparedStatement pstmt=null;
        Connection conn=null;
        ResultSet rs=null;

        String SQL1="SELECT COUNT(*) FROM ANSWERS WHERE QUESTION_ID=?";
        String SQL2="SELECT SUBDIVISION_ID FROM ANSWERS WHERE QUESTION_ID=?";

        try
        {
            conn=Database.getConnection();
            pstmt=conn.prepareStatement(SQL1);
            pstmt.setInt(1, questionId);
            rs=pstmt.executeQuery();

            PreparedStatement pstmt2=conn.prepareStatement(SQL2);
            pstmt2.setInt(1, questionId);
            ResultSet rs2=pstmt2.executeQuery();
            int size=0;
            int i=0;

            while (rs.next())
            {
                size=rs.getInt(1);
                subDivision_id=new int[size];

                while (rs2.next())
                {
                    subDivision_id[i++]=rs2.getInt(1);
                }
            }

        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();;
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(pstmt!=null)
                {
                    pstmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }

            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return subDivision_id;
    }

    /*답변 내용으로 중분류 id 구하기*/
    public static int selectSubdivId_content(String contents){
        int subdivId=0;    //중분분류id

        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String SQL = "SELECT SUBDIVISION_ID FROM ANSWERS WHERE ANSWER_CONTENT='"+contents+"'";
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                subdivId=rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return subdivId;
    }

    //답변id로 질문id 조회
    public static int selectQueId_ansId(int answerId){
        int questionId=0;    //질문id

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String SQL = "SELECT QUESTION_ID FROM ANSWERS WHERE ANSWER_ID=?";
        try {
            conn = Database.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, answerId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                questionId=rs.getInt(1);
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
        return questionId;
    }

    //답변id로 중분류id 조회
    public static int selectSubdivId_ansId(int answerId){
        int subdivId=0;    //중분류id

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String SQL = "SELECT SUBDIVISION_ID FROM ANSWERS WHERE ANSWER_ID=?";
        try {
            conn = Database.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, answerId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                subdivId=rs.getInt(1);
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
        return subdivId;
    }

    public static int getAnswerId_content(String content){
        int subdivId=0;    //중분류id

        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String SQL = "SELECT ANSWER_ID FROM ANSWERS WHERE ANSWER_CONTENT='" + content + "'";
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                subdivId=rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return subdivId;
    }

}