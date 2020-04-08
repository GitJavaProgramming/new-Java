package org.pp.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static org.pp.socket.CommandConstant.SERVER_HOST;
import static org.pp.socket.CommandConstant.SERVER_PORT;

public class NioSocketClient {
    public static void main(String[] args) {

        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            // 客户端也用非阻塞方式读写
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select();
                Iterator iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = (SelectionKey) iter.next();
                    iter.remove();
                    if (key.isConnectable()) {
                        processConnect(key, selector);
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processConnect(SelectionKey key, Selector selector) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 如果正在连接，则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.write(ByteBuffer.wrap("Command : Connected".getBytes()));
        channel.register(selector, SelectionKey.OP_READ);
    }

    public static void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        /**
         *  出现粘包问题
         *  这里要自己处理怎么从Buffer中读取一行或者按自定义规则读取
         * 可能的结果之一：
         * 54
         * Server Echo : welcomeServer Echo : Command : Connected
         * 可能的结果之一：
         * 21
         * Server Echo : welcome
         * 33
         * Server Echo : Command : Connected
         *
         */
        System.out.println(msg.length());
        System.out.println(msg);
    }
}
