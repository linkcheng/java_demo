package cn.xyf.flowcount.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Client c = new Client();
        c.start();
    }

    public void start() {
        Socket s = null;
        try {
            // 1. 建立连接
            s = new Socket("127.0.0.1", 8080);
            // 2. 创建接收器，接受来自服务端的消息
            Thread t = new Thread(new Receiver(s));
            t.setDaemon(true);
            t.start();

            // 3. 发送消息，建议使用 PrintWriter
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String str;
            while ((str = input.readLine()) != null) {
                out.println(str);
                if(str.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Receiver implements Runnable {
        Socket socket;
        BufferedReader in;

        Receiver(Socket s) throws IOException {
            socket = s;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        @Override
        public void run() {
            String str;
            while (true) {
                try {
                    if ((str=in.readLine())==null) break;
                    System.out.println(str);
                } catch (IOException e) {
                    break;
                }
            }
        }
    }
}
