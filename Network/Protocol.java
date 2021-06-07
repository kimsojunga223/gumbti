package Network;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Objects;

public class Protocol {
    final static public int UNDEFINED 				= 99;
    final static public int START                   = 0;
    final static public int TYPE1_CHOICE_REQ 		= 1;
    final static public int TYPE2_CHOICE_RES 		= 2;
    final static public int TYPE3_READ_REQ 		    = 3;
    final static public int TYPE4_READ_RES 		    = 4;
    final static public int TYPE5_CHECK_REQ 		= 5;
    final static public int TYPE6_CHECK_RES 		= 6;
    final static public int NEXT                    = 7;
    final static public int AGAIN            		= 8;
    final static public int EXIT 					= 9;

    final static public int T1_CD1_DIVISION			= 0;
    final static public int T1_CD2_ANSWER  			= 1;

    final static public int T2_CD1_FAIL 			=99;
    final static public int T2_CD2_SUCCESS 			= 1;

    final static public int T3_CD1_CHOICEDIVISION       = 1;
    final static public int T3_CD2_RESULT_FOR_FINAL     = 2;
    final static public int T3_CD3_RESULT_FOR_DIVISION  = 3;
    final static public int T3_CD4_RESULT_PICTURE       = 4;
    final static public int T3_CD5_DETAIL 			    = 5;    // 분류별 순위


    final static public int T4_CD1_FAIL 			=99;
    final static public int T4_CD2_SUCCESS 			= 1;

    //final static public int T5_CD1_REQUIRED_CHOICE	= 0;
    //final static public int T5_CD2_OVERLAP	= 1;
    final static public int T5_CD3_LAST_QUESTION    = 2;

    final static public int T6_CD1_FAIL 			=99;
    final static public int T6_CD2_SUCCESS 			= 1;

    // 길이
    public static final int LEN_TYPE = 1;
    public static final int LEN_CODE = 1;
    public static final int LEN_BODY = 4;
    public static final int LEN_HEADER = 6;

    private byte type;
    private byte code;
    private int bodyLength;
    private byte[] body;

    public Protocol() {
        this(UNDEFINED, 0);
    }
    public Protocol(int type) {
        this(type, 0);
    }
    public Protocol(int type, int code) {
        setType(type);
        setCode(code);
        setBodyLength(0);
    }

    public byte getType() {
        return type;
    }
    public void setType(int type) {
        this.type = (byte) type;
    }

    public byte getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = (byte) code;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    // Body Length는 직접 설정할 수 없음
    private void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public Object getBody() {
        return deserialize(body);
    }

    public void setBody(Object body) {
        byte[] serializedObject = serialize(body);
        this.body = serializedObject;
        setBodyLength(serializedObject.length);
    }

    // 현재 header와 body로 패킷을 생성하여 리턴
    public byte[] getPacket() {
        byte[] packet = new byte[LEN_HEADER + getBodyLength()];
        packet[0] = getType();
        packet[1] = getCode();
        // 바디길이 저장하기
        System.arraycopy(intToByte(getBodyLength()), 0, packet, LEN_TYPE+LEN_CODE, LEN_BODY);
        // 바디가 있으면
        if (getBodyLength() > 0)
            System.arraycopy(body, 0, packet, LEN_HEADER, getBodyLength());
        return packet;
    }

    // 매개 변수 packet을 통해 header만 생성
    public void setPacketHeader(byte[] packet) {
        byte[] data;
        setType(packet[0]);
        setCode(packet[LEN_TYPE]);
        data = new byte[LEN_BODY];
        //길이 설정
        System.arraycopy(packet, LEN_TYPE + LEN_CODE, data, 0, LEN_BODY);
        setBodyLength(byteToInt(data));
    }

    // 매개 변수 packet을 통해 body를 생성
    public void setPacketBody(byte[] packet) {
        byte[] data;

        if (getBodyLength() > 0) {
            data = new byte[getBodyLength()];
            System.arraycopy(packet, 0, data, 0, getBodyLength());
            setBody(deserialize(data));
        }
    }

    // 직렬화
    private byte[] serialize(Object b) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(b);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 역직렬화
    private Object deserialize(byte[] b) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object ob = ois.readObject();
            return ob;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] intToByte(int i) {
        return ByteBuffer.allocate(Integer.SIZE / 8).putInt(i).array();
    }

    private int byteToInt(byte[] b) {
        return ByteBuffer.wrap(b).getInt();
    }
}