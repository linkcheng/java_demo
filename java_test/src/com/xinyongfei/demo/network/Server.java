package com.xinyongfei.demo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }

    public void start() {
        ServerSocket ss = null;
        Socket s = null;
        try {
            // 1. 创建 ServerSocket
            ss = new ServerSocket(8080);
            while (true) {
                // 2. 监听客户端
                s = ss.accept();
                ServerThread st = new ServerThread(s);
                Thread t = new Thread(st);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ServerThread implements Runnable {
        Socket socket;
        String name;
        BufferedReader in = null;
        PrintWriter out = null;

        ServerThread(Socket s) {
            socket = s;
            name = "客户端 "+s.getInetAddress().getHostName()+":"+socket.getPort();
            System.out.println(name+" 上线了");
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String str = null;
            while(true){
                try {
                    if (!((str=in.readLine())!=null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(name+" 发来消息: "+str);
                // 3. 返回客户端消息

                if(out != null) {
                    out.println(str);
                    if(str.equals("bye")){
                        break;
                    }
                }
            }
        }
    }
}
