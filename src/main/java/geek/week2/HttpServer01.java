package geek.week2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8801);
            while (Boolean.TRUE) {
                try {
                    Socket socket = serverSocket.accept();
                    service(socket);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),Boolean.TRUE);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio1";
//            如果不写，读时不知道什么时候结束，可能会读错
            printWriter.println("Content-Length:"+body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
