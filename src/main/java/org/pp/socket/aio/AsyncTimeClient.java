package org.pp.socket.aio;

public class AsyncTimeClient {
    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1",8000), "AIO-asyncTimeClientHandler-001").start();
    }
}
