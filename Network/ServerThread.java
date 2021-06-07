package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.Database;
import Server.Model.DAO.*;

import Network.Protocol;
import Server.Model.Service.Logic;

import javax.naming.ldap.SortKey;
import javax.xml.transform.Result;

public class ServerThread extends Thread {

    private Logic logic;
    private int ID;
    private Socket socket;
    private Connection conn;
    private Protocol p;
    private IO io;

    // 스레드 생성자
    public ServerThread(Socket socket, Connection conn) throws IOException {
        ID = socket.getPort();
        this.socket = socket;
        this.conn = conn;
        io = new IO(socket);
        logic = new Logic();
    }

    // 실행
    @Override
    public void run() {
        while (true) {
            try {
                p = io.read();
                handle(p);
            } catch (Exception e) {
                this.stop();
            }
        }
    }
    public void handle(Protocol p) throws Exception {
        byte packetType = p.getType();
        switch (packetType) {
            case Protocol.START:
                break;
            //선택 요청
            case Protocol.TYPE1_CHOICE_REQ:
                io.send(choiceReq(p));
                break;

            //조회 요청
            case Protocol.TYPE3_READ_REQ:
                io.send(readRes(p));
                break;

            //확인 요청
            case Protocol.TYPE5_CHECK_REQ:
                io.send(checkRes(p));
                break;

            //다시하기 요청:
            case Protocol.AGAIN:
                againReq(p);
                break;

            //끝냄 요청
            case Protocol.EXIT:
                finishReq(p);
                break;
        }
    }

    //얘는 모르겠당
    public Protocol choiceReq(Protocol protocol) throws Exception {
        Protocol sendData = new Protocol(Protocol.TYPE2_CHOICE_RES);
        switch (protocol.getCode())
        {
            case Protocol.T1_CD1_DIVISION:
                String[] selectedDivision = (String[]) protocol.getBody();
                if(Network.checkDivisionCnt(selectedDivision) != 3)
                    sendData.setCode(Protocol.T2_CD1_FAIL);
                else {
                    if(Network.isOverlap(selectedDivision))
                        sendData.setCode(Protocol.T2_CD1_FAIL);
                    else {
                        sendData.setCode(Protocol.T2_CD2_SUCCESS);
                        // 로직에 대분류 선택 저장
                        logic.setMyInterests(selectedDivision);
                        logic.initMapping();
                    }
                 }
                return sendData;

            case Protocol.T1_CD2_ANSWER:
                String answer = (String) protocol.getBody();
                System.out.println(answer);
                // 로직에 답 저장, dao에서 어떤 대분류, 중분류의 답변인지 매칭 필요
                try {
                    int id = AnswersDAO.getAnswerId_content(answer);
                    if(answer.equals(null))
                        throw new Exception();
                    logic.storageWeights(id);
                    sendData.setCode(Protocol.T2_CD2_SUCCESS);
                    sendData.setBody(id);
                } catch (Exception e) {
                    sendData.setCode(Protocol.T2_CD1_FAIL);
                }
                return sendData;
        }
        return sendData;
    }

    public Protocol readRes(Protocol protocol) {
        Protocol sendData = new Protocol(Protocol.TYPE4_READ_RES);

        switch (protocol.getCode())
        {
            case Protocol.T3_CD1_CHOICEDIVISION:
                int [] subId = logic.rankSubDivision();
                String[] resultArr = new String[3];

                try {
                    for(int i = 0; i < resultArr.length; i++) {
                        resultArr[i] = SubDivisionDAO.subDivisionName_id(subId[i]);
                    }
                    sendData.setCode(Protocol.T4_CD2_SUCCESS);
                    sendData.setBody(resultArr);
                } catch (Exception e) {
                    sendData.setCode(Protocol.T4_CD1_FAIL);
                }
                return sendData;

            case Protocol.T3_CD2_RESULT_FOR_FINAL:

                try {
                    logic.storageDistictResult();
                    String result = logic.finalDistictName();
                    sendData.setCode(Protocol.T2_CD2_SUCCESS);
                    sendData.setBody(result);
                } catch (Exception e) {
                    sendData.setCode(Protocol.T4_CD1_FAIL);
                }
                return sendData;

            case Protocol.T3_CD3_RESULT_FOR_DIVISION:
                try {
                    int [] sub = logic.rankSubDivision();
                    for(int i = 0; i < sub.length; i++)
                        System.out.println(sub[i]);
                    int idx = (int) protocol.getBody();
                    String [] result = logic.districtRank(sub[idx]);
                    sendData.setCode(Protocol.T4_CD2_SUCCESS);
                    sendData.setBody(result);
                } catch (Exception e) {
                    sendData.setCode(Protocol.T4_CD1_FAIL);
                }
                return sendData;


            case Protocol.T3_CD5_DETAIL:
                try {
                    sendData.setCode(Protocol.T4_CD2_SUCCESS);
                    sendData.setBody(logic.finalDistictRank());
                } catch (Exception e) {
                    sendData.setCode(Protocol.T4_CD1_FAIL);
                }
                return sendData;
        }
        return protocol;
    }

    public Protocol checkRes(Protocol protocol){
        Protocol sendData = new Protocol(Protocol.TYPE6_CHECK_RES);
        switch (protocol.getCode())
        {
//            case Protocol.T5_CD1_REQUIRED_CHOICE:
//                String[] selectedDivision = (String[]) protocol.getBody();
//                //if(Network.checkDivisionCnt(selectedDivision) == 3)
////                    sendData.setCode(Protocol.T6_CD1_FAIL);
////                else
////                    sendData.setCode(Protocol.T6_CD2_SUCCESS);
//                return sendData;


            case Protocol.T5_CD3_LAST_QUESTION:
                String mydiv = (String) protocol.getBody();
                if(logic.getMyInterests()[2].equals(mydiv)) {
                    logic.saveRankSubDiv();
                    sendData.setCode(Protocol.T6_CD2_SUCCESS);
                    sendData.setBody("99");
                } else if(logic.getMyInterests()[0].equals(mydiv)) {
                    sendData.setCode(Protocol.T6_CD2_SUCCESS);
                    sendData.setBody(logic.getMyInterests()[1]);
                } else if(logic.getMyInterests()[1].equals(mydiv)) {
                    sendData.setCode(Protocol.T6_CD2_SUCCESS);
                    sendData.setBody(logic.getMyInterests()[2]);
                } else {
                    sendData.setCode(Protocol.T6_CD1_FAIL);
                }
                return sendData;
        }
        return sendData;
    }

    public void againReq(Protocol protocol) throws Exception {
        Server.addThread(socket, conn);
        Server.run();
    }

    public void finishReq(Protocol protocol) throws IOException{
        int s2 = (int) protocol.getBody();
        Server.remove(s2);
    }

    public IO getIo() {
        return io;
    }

    public void setIo(IO io) {
        this.io = io;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }
}