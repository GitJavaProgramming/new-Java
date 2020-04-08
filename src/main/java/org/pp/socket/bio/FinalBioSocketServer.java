package org.pp.socket.bio;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.pp.socket.CommandConstant.SERVER_PORT;

/**
 * Bio server socket最终版本，你还能想到改进point吗？？
 * 阻塞的通信，在接收连接、读写数据时
 */
public final class FinalBioSocketServer {

    private static ExecutorService service = null;

    public static void main(String[] args) {

//        hook(); // 阻塞形式下小心异常处理器跳过它

        initService();

        initServer(service);
    }

    /**
     * 阻塞通信 在接收连接、读写数据时
     */
    public static void initServer(ExecutorService service) {
        try (/*ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();ServerSocket serverSocket = serverSocketChannel.socket();*/
                ServerSocket serverSocket = new ServerSocket(/*PORT 指定port查看默认选项信息*/)) {
//            serverSocket.getChannel();  // 可以获得channel
            // SO_REUSEADDR、SO_RCVBUF跟具体平台有关
            System.out.println("Default ReuseAddress Value : " + serverSocket.getReuseAddress());
            System.out.println("Default ReceiveBufferSize Value : " + serverSocket.getReceiveBufferSize());

            option(serverSocket);
            bindSocketAddress(serverSocket, SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept(); // 阻塞等待客户端连接，连接请求队列为空则会一直等待，当有客户端连接时才返回。

//                handleClientConnection(socket);
//                handleClientConnection2(socket);
                handleClientConnection(service, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();   // 打印异常栈，自定义异常处理器
        } finally {
            hook();
        }
    }

    /**
     * 方法3、用线程池处理/调度客户端socket连接
     *
     * @param service 调度线程池
     * @param socket  客户端连接
     */
    public static void handleClientConnection(ExecutorService service, Socket socket) {
        service.execute(new ClientHandler(socket));
        // 小心线程池关闭，关闭后下一次连接将会异常  运行2此客户端试试
        // Exception in thread "main" java.util.concurrent.RejectedExecutionException:
        // Task java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask@77f03bb1 rejected from
        // java.util.concurrent.ScheduledThreadPoolExecutor@25618e91[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 1]
//        service.shutdown();
    }

    /**
     * 生成线程池，用于处理客户端连接
     * 关于线程池：在核心线程为满工作状态时，是不会往调度队列里面排队的
     */
    private static ExecutorService initService() {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        if (service == null) {
            service = Executors.newScheduledThreadPool(poolSize);
        }
        return service;
    }

    /**
     * 方法2、给每个客户端分配一个线程池
     *
     * @param socket
     */
    public static void handleClientConnection2(Socket socket) {
        // Runtime.getRuntime().availableProcessors() 返回可用cpu个数
        // 计算型/io型 计算型让cpu计算单元尽量占用，所以应该减少进程切换
        // io型进程切换，所以使用2倍cpu核数
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(poolSize);
        service.execute(new ClientHandler(socket));
        service.shutdown();
    }

    /**
     * 方法1、给每个客户端分配一个线程
     *
     * @param socket 连接的socket
     */
    public static void handleClientConnection(Socket socket) {
        new Thread(new ClientHandler(socket)).start();
    }

    /**
     * 配置ServerSocket （TCP Connection） 选项
     */
    public static void option(ServerSocket serverSocket) throws SocketException {

        // 启用或禁用SocketOptions#SO_TIMEOUT SO_TIMEOUT
        // 当等待客户端连接超时将会抛出java.net.SocketTimeoutException
//        serverSocket.setSoTimeout(300); // 等待客户端连接超时时间

        // TCP 三次握手、四次挥手
        // SocketOptions#SO_REUSEADDR
        // MSL是Maximum Segment Lifetime英文的缩写，中文可以译为“报文最大生存时间”
        // 服务器端关闭一个tcp连接时，Server端连接会保持一段时间处于超时状态，通常被认为是TIME_WAIT状态或者2MSL等待状态
        // 对于使用已知socket地址和端口的应用程序来说，如果（Server端）有一个连接正在超时状态可能无法绑定所需的SocketAddress
        serverSocket.setReuseAddress(true); // 设置允许重用服务器所绑定的地址
        // SocketOptions#SO_RCVBUF
        serverSocket.setReceiveBufferSize(1024); // 设置接收数据缓冲区大小

//        serverSocket.setPerformancePreferences();
    }

    public static void bindSocketAddress(ServerSocket serverSocket, SocketAddress socketAddress) throws IOException {
        serverSocket.bind(socketAddress);
    }

    /**
     * 重载版本，绑定指定端口就行
     */
    public static void bindSocketAddress(ServerSocket serverSocket, int port) throws IOException {
        serverSocket.bind(new InetSocketAddress(port));
    }

    public static void hook() {
        System.out.println("register hook...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("服务器超时关闭...");
            service.shutdown(); // 关闭线程池
        }));

        /*
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("服务器超时关闭...");
            }
        }));
        */
    }
}
