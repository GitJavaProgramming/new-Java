package org.pp.socket.aio;

public class AsyncTimeServer {
    
    public static void main(String[] args) {
        new Thread(new AsyncTimeServerHandler(8000), "AIO-asyncTimeServerHandler-001").start();
    }
}
