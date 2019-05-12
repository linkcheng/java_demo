package com.xinyongfei.demo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ServerThread> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start();
    }

    public void start() {
        // 1. 创建 ServerSocket
        ServerSocket ss;
        try {
            ss = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        do {
            // 2. 监听客户端
            Socket s;
            ServerThread st;
            try {
                s = ss.accept();
                st = new ServerThread(s);
            } catch (IOException e) {
                continue;
            }

            send(st.name+":【上线了】");
            clients.add(st);

            Thread t = new Thread(st);
            t.start();
        } while (!clients.isEmpty());

        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对所有人发送消息
     * @param msg
     */
    public void send(String msg) {
        for(ServerThread c : clients) {
            c.out.println(msg);
        }
    }

    class ServerThread implements Runnable {
        Socket socket;
        String name;
        BufferedReader in;
        PrintWriter out;

        ServerThread(Socket s) throws IOException {
            socket = s;
            name = "客户端 "+s.getInetAddress().getHostName()+":"+socket.getPort();
            System.out.println(name+":【上线了】");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        }

        @Override
        public void run() {
            String str = null;
            while(true){
                try {
                    if ((str=in.readLine())==null) break;
                    System.out.println(name+": "+str);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 3. 返回客户端消息

                if(out != null && str != null) {
                    out.println(str);
                    send(name+": "+str);
                    if(str.equals("bye")){
                        break;
                    }
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clients.remove(this);
            System.out.println(name+":【下线了】");
            send(name+":【下线了】");
        }
    }
}
