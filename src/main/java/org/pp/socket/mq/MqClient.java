package org.pp.socket.mq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MqClient {

    public void produce(String message) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.port);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(message);
            out.flush();
        }
    }

    public String consume() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.port);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            out.println("CONSUME");
            out.flush();

            String msg = in.readLine();

            return msg;
        }
    }
}
