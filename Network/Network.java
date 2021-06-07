package Network;

import java.io.IOException;
import java.net.Socket;

public class Network {

    private static Socket socket;
    private static IO io;

    public Network() {
        try {
            socket = new Socket("localhost", 3000);
            io = new IO(socket);
            System.out.println("서버 접속 중");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static int getLocalPort() {
        return socket.getLocalPort();
    }

    //시작
    public static void start() throws Exception {
        Protocol protocol = new Protocol(Protocol.START);
        io.send(protocol); // 전송

    }

    //다시 시작
    public static void again() throws Exception {
        Protocol protocol = new Protocol(Protocol.AGAIN);
        io.send(protocol);
    }

    //종료
    public static void exit(int id) throws Exception {
        Protocol protocol = new Protocol(Protocol.EXIT);
        protocol.setBody(id);
        io.send(protocol);
    }

    /*
     * [선택 관련(TYPE1, 2) 네트워크 제어]
     * divisionChoiceReq : 대분류의 선택을 서버로 전송한다. (T1_CD1_DIVISION)
     * checkDivisionCnt: 대분류를 덜 골랐을 경우를 걸러낸다. (T1_CD1_DIVISION 예외처리 1)
     * isOverlap: 대분류를 중복으로 골랐을 경우를 걸러낸다. (T1_CD1_DIVISION 예외처리 2)
     * answerChoiceReq: 질문지의 응답을 서버로 전송한다. (T1_CD2_ANSWER)
     * */
    // 대분류 선택 전달
    public static byte divisionChoiceReq(String[] divisionId) throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE1_CHOICE_REQ);
        protocol.setCode(Protocol.T1_CD1_DIVISION);
        protocol.setBody(divisionId);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE2_CHOICE_RES);
        return protocol.getCode();
    }

    // 개수 체크하기
    public static int checkDivisionCnt(String[] divisionId) throws Exception {
        int cnt = 0;
        for (int i = 0; i < divisionId.length; i++) {
            if(divisionId[i] != null)
                cnt++;
        }
        return cnt;
    }

    // 중복 체크하기
    public static boolean isOverlap(String[] divisionId) throws Exception {
        boolean check = false;
        for(int i = 0; i < divisionId.length; i++) {
            for(int j = 0; j < i; j++) {
                if(divisionId[i].equals(divisionId[j])) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
    // 대답 선택 전달
    public static byte answerChoiceReq(String answer) throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE1_CHOICE_REQ);
        protocol.setCode(Protocol.T1_CD2_ANSWER);
        protocol.setBody(answer);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE2_CHOICE_RES);
        return protocol.getCode();
    }

    /*
     * [조회 관련(TYPE3, 4) 네트워크 제어]
     * selectedDivisionReadReq: 선택한 대분류의 명을 들고 온다.(T3_CD1_CHOICEDIVISION)
     * lastResultReadReq: 최종 결과를 들고 온다. (T3_CD2_RESULT_FOR_FINAL)
     * detailReadReq: 자세히 보기 창에 들어갈 정보를 들고 온다. (T3_CD5_DETAIL)
     * */
    public static String[] selectedDivisionReadReq() throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE3_READ_REQ);
        protocol.setCode(Protocol.T3_CD1_CHOICEDIVISION);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE4_READ_RES);
        return (String[]) protocol.getBody();
    }

    public static String lastResultReadReq() throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE3_READ_REQ);
        protocol.setCode(Protocol.T3_CD2_RESULT_FOR_FINAL);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE4_READ_RES);
        return (String) protocol.getBody();
    }

    public static String[] divisionListReadReq(int i) throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE3_READ_REQ);
        protocol.setCode(Protocol.T3_CD3_RESULT_FOR_DIVISION);
        protocol.setBody(i);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE4_READ_RES);
        return (String[]) protocol.getBody();
    }

    public static String[] detailReadReq() throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE3_READ_REQ);
        protocol.setCode(Protocol.T3_CD5_DETAIL);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE4_READ_RES);
        return (String[]) protocol.getBody();
    }


    /*
     * [검사 관련(TYPE5, 6) 네트워크 제어]
     * lastCheckReq: 마지막 질문이 마지막 대분류에 속한 질문인지를 판단한다. (T5_CD3_LAST_QUESTION)
     * */
    // 마지막 질문인가
    public static String lastCheckReq(String str) throws Exception {
        Protocol protocol = new Protocol(Protocol.TYPE5_CHECK_REQ);
        protocol.setCode(Protocol.T5_CD3_LAST_QUESTION);
        // 로직에서 선택한 대분류 들고오기
        protocol.setBody(str);
        io.send(protocol);
        protocol = io.recv(Protocol.TYPE6_CHECK_RES);
        return (String) protocol.getBody();
    }
}