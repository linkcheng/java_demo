package com.xinyongfei.demo.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket s = null;
        try {
            // 1. 建立连接
            s = new Socket("127.0.0.1", 8080);
            // 2. 发送消息写字符串建议使用 PrintWriter
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            while ((str = reader.readLine()) != null) {
                out.println(str);

                // 3. 接收消息
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                System.out.println("收到服务端消息: " + br.readLine());

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
}
