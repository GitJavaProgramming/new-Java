package org.pp.java8.async.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.*;

public class TcpCase {
    private static Vertx vertx = Vertx.vertx();

    public static void server() {
//        Buffer myKeyStoreAsABuffer = vertx.fileSystem().readFileBlocking("/path/to/your/server -keystore.jks");
//        JksOptions jksOptions = new JksOptions().setValue(myKeyStoreAsABuffer).setPassword("password-of-your-keystore");
//        NetServerOptions options = new NetServerOptions()
//                .setSsl(true)
//                .setKeyStoreOptions(jksOptions);
        NetServerOptions options = new NetServerOptions();
        NetServer server = vertx.createNetServer(options);
        // 连接处理
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                System.out.println("I received some bytes: " + buffer.length());
            });
            // 向已连接的客户端socket写buffer 写操作时异步操作
            Buffer buffer = Buffer.buffer().appendFloat(12.34f).appendInt(123);
            socket.write(buffer);
            // Write a string in UTF-8 encoding
            socket.write("some data");
            // Write a string using the specified encoding
            socket.write("some data", "UTF-16");

            // socket关闭时通知
            socket.closeHandler(v -> {
                System.out.println("The socket has been closed");
            });
        });
        /**
         * 默认主机是 0.0.0.0 ，意味着 '监听所有可用的地址' ，默认端口是0，
         * 这是一个特殊值，告诉 服务器随机找一个未使用的本地端口使用。
         */
//        server.listen();
        server.listen(4321, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
        vertx.setTimer(2000, handler -> {
            // server关闭
            server.close(res -> {
                if (res.succeeded()) {
                    System.out.println("Server is now closed");
                    vertx.close();
                } else {
                    System.out.println("close failed");
                }
            });
        });
    }

    public static void client() {
        NetClientOptions options = new NetClientOptions()
                .setConnectTimeout(10000)
                .setReconnectAttempts(10) // 重连次数
                .setReconnectInterval(500); // 重连间隔
        NetClient client = vertx.createNetClient(options);
        client.connect(4321, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Connected!");
                NetSocket socket = res.result();
                socket.handler(buffer -> {
                    System.out.println("client : " + new String(buffer.getBytes()));
                });
            } else {
                System.out.println("Failed to connect: " + res.cause().getMessage());
            }
        });
    }

    public static void main(String[] args) {
        server();
        client();
    }
}
