package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Network.Protocol;

public class IO {

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public IO(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public void close() throws IOException {
        if (socket != null)
            socket.close();
        if (is != null)
            is.close();
        if (os != null)
            os.close();
    }

    // 네트워크에서 서버로온거 읽기
    public Protocol read() throws IOException {
        byte[] header = new byte[Protocol.LEN_HEADER];
        Protocol protocol = new Protocol();
        int totalReceived = 0;
        int readSize = 0;
        is.read(header, 0, Protocol.LEN_HEADER);
        protocol.setPacketHeader(header);
        byte[] buf = new byte[protocol.getBodyLength()];
        while (totalReceived < protocol.getBodyLength()) {
            readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
            totalReceived += readSize;
        }
        protocol.setPacketBody(buf);
        return protocol;
    }

    public void send(Protocol p) throws IOException {
        os.write(p.getPacket());
        os.flush();
    }


    public Protocol recv(int type) throws Exception {
        byte[] header = new byte[Protocol.LEN_HEADER];
        Protocol protocol = new Protocol();
        try {
            int totalReceived, readSize;
            do {
                totalReceived = 0;
                is.read(header, 0, Protocol.LEN_HEADER);
                protocol.setPacketHeader(header);
                byte[] buf = new byte[protocol.getBodyLength()];
                while (totalReceived < protocol.getBodyLength()) {
                    readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
                    totalReceived += readSize;
                    if (readSize == -1) {
                        throw new Exception("통신오류: 연결 끊어짐");
                    }
                }
                protocol.setPacketBody(buf);
                if (protocol.getType() == Protocol.UNDEFINED)
                    throw new Exception("통신오류: 서버에서 오류 발생함");
                else if (protocol.getType() == type)
                    return protocol;
            } while (true); // 현재 필요한 응답이 아닐경우 무시하고 다음 응답을 대기
        } catch (IOException e) {
            throw new Exception("통신오류: 데이터 수신 실패함");
        }
    }
}