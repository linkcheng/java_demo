package cn.xyf.flowcount.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ServerClient> clients = new ArrayList<ServerClient>();

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

        while(true) {
            // 2. 监听客户端
            Socket s;
            try {
                s = ss.accept();
                Thread t = new Thread(new ServerClient(s));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

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
        System.out.println(msg);
        for(ServerClient c : clients) {
            c.out.println(msg);
        }
    }

    class ServerClient implements Runnable {
        String name;
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        ServerClient(Socket s) throws IOException {
            socket = s;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            clients.add(this);
            name = "客户端 "+s.getInetAddress().getHostName()+":"+s.getPort();
            send(name+":【上线了】");
        }

        @Override
        public void run() {
            String str = null;
            while(true){
                try {
                    if ((str=in.readLine())==null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 发送消息
                send(name+": "+str);
                if(str.equals("bye")){
                    break;
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clients.remove(this);
            send(name+":【下线了】");
        }
    }
}
