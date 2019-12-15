package org.pp.mq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer implements Runnable {

    public static final int port = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                System.out.println("接收到原始数据：" + str);
                if ("CONSUME".equals(str)) {
                    // 从消息队列中消费一条信息
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                } else {
                    Broker.produce(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("等待接收连接...");
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            new Thread(new BrokerServer(serverSocket.accept())).start();
        }
    }
}
