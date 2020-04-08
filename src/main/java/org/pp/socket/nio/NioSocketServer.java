package org.pp.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static org.pp.socket.CommandConstant.SERVER_PORT;

/**
 * 非阻塞的服务器
 * 现在是非阻塞通信，在接收连接和读写数据时都不阻塞，但是在选择器轮询时，没有就绪事件仍然会阻塞
 * 比较：
 */
public class NioSocketServer {

    public static void main(String[] args) {
//        testBioServer();

        // 1、获得通道 通道连接了数据源/数据汇和缓冲区Buffer
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            // 2、设置通道相关参数，并绑定监听的端口
            serverChannel.configureBlocking(false); // 配置非阻塞模式
            serverChannel.socket().bind(new InetSocketAddress(SERVER_PORT));

            // 3、打开选择器，并向选择器注册事件，选择器与通道关联后会监控通道注册的事件
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 方法栈最后调用的本地方法 private native int poll0(long var1, int var3, int[] var4, int[] var5, int[] var6, long var7);
                // 此方法执行处于阻塞模式的选择操作。仅在至少选择一个通道、调用此选择器的 wakeup 方法，或者当前的线程已中断（以先到者为准）后此方法才返回。
                selector.select();
                // Selector维护一个已注册的事件集合，通过select()方法阻塞轮询事件
                // public abstract Set<SelectionKey> selectedKeys();
                Iterator iter = selector.selectedKeys().iterator(); // 对每个事件进行处理，一个事件处理完成后将会被移除
                while (iter.hasNext()) {
                    SelectionKey key = (SelectionKey) iter.next();
                    iter.remove(); // 删除已选的key,以防重复处理
                    // 每次向选择器注册通道时就会创建一个选择键。通过调用某个键的 cancel 方法、关闭其通道，
                    // 或者通过关闭其选择器来取消该键之前，它一直保持有效。取消某个键不会立即从其选择器中移除它；相反，会将该键
                    // 添加到选择器的已取消键集，以便在下一次进行选择操作时移除它。可通过调用某个键的 isValid 方法来测试其有效性。
                    if (!key.isValid()) { // 还会存在无效的key吗？？？？
                        continue;
                    }
                    if (key.isAcceptable()) {
                        processAccept(key, selector);
                    } else if (key.isReadable()) {
                        processRead(key);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);  // 通道读数据入Buffer
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("receive：" + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(("Server Echo : " + msg).getBytes()); // 数据写入缓冲区
        channel.write(outBuffer); // Buffer写入channel
    }

    public static void processAccept(SelectionKey key, Selector selector) throws IOException {
        // 为什么你知道强制转换为ServerSocketChannel，因为前面注册的时候你将它注册到这个selector并绑定了accept事件
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        // 默认阻塞模式，需要配置非阻塞才能使用Buffer，否则报异常 java.nio.channels.IllegalBlockingModeException
        SocketChannel channel = serverSocketChannel.accept(); // 这个channel将用于后续的处理
        channel.configureBlocking(false);
        //在这里可以给客户端发送信息哦
        channel.write(ByteBuffer.wrap("Server Echo : welcome".getBytes()));
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 测试bio，程序将会阻塞
     */
    public static void testBioServer() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT/*PORT 指定port查看默认选项信息*/)) {
            // io阻塞，查看accept调用栈 在java.net.DualStackPlainSocketImpl类中配置configureBlocking(nativefd, false);
            // 最后调用本地方法！！ static native int accept0(int fd, InetSocketAddress[] isaa) throws IOException;
            serverSocket.accept();
            System.out.println("socket blocking accept"); // 没建立连接就不会执行到这里
        } catch (IOException e) {
            e.printStackTrace();   // 打印异常栈，自定义异常处理器
        }
    }
}
