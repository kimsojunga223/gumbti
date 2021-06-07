package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import Server.Database;

import java.util.ArrayList;

public class Server {
    private static ServerSocket sSocket;
    private static ServerThread clients[];
    private static int clientCount;

    // 서버 생성자
    public Server() throws IOException {
        clients = new ServerThread[50]; // 최대 50명 수용가능
        clientCount = 0;
        sSocket = new ServerSocket(3000);
    }

    // 구동
    public static void run() {
        while (sSocket != null) {
            // 소켓연결
            Socket socket = null;
            try {
                socket = sSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // DB연결
            Connection conn = Database.getConnection();
            // 새로운쓰레드생성
            try {
                addThread(socket, conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void addThread(Socket socket, Connection conn) throws Exception {
        if (clientCount < clients.length) {
            clients[clientCount] = new ServerThread(socket, conn);
            clients[clientCount].start();
            clientCount++;
            System.out.println("쓰레드" + clientCount);
        } else {
            System.out.println("Client refused: maximum " + clients.length + " reached.");
        }
    }

    public static int findClient(int ID) {
        for (int i = 0; i < clientCount; i++)
            if (clients[i].getID() == ID)
                return i;
        return -1;
    }

    // 쓰레드 지우기
    public synchronized static void remove(int ID) throws IOException {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            if (pos < clientCount - 1)
                for (int i = pos + 1; i < clientCount; i++)
                    clients[i - 1] = clients[i];
            clientCount--;
            System.out.print(clientCount);
            toTerminate.getIo().close();
            toTerminate.stop();
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.run();
    }
}