package cn.xyf.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerDemo {
    public static void main(String[] args) {
        int port = 9999;

        try(ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len=is.read(buffer)) != -1) {
                baos.write(buffer,0, len);
            }
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
