package cn.xyf.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClientDemo {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9999;

        try (Socket socket = new Socket(host, port);
             OutputStream os = socket.getOutputStream()){
            os.write("你好，这是一个TCP的客户端".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
