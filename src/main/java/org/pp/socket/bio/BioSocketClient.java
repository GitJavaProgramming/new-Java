package org.pp.socket.bio;

import org.pp.socket.CommandConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static org.pp.socket.CommandConstant.SERVER_HOST;
import static org.pp.socket.CommandConstant.SERVER_PORT;

/**
 * 表示连接的客户端
 * 阻塞通信，在连接服务器、读写数据、soLinger选项关闭时
 */
public class BioSocketClient {

    //    private static final String CLOSE = "SHUTDOWN";
    private static /*volatile*/ boolean STOP_AND_CLOSE = false;

    public static void main(String[] args) {
        try (Socket socket = new Socket()/* 创建socket，连接到指定ip和端口 */) {

            /* config socket */
            option(socket);
            connect(socket);

            /* ops */
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true/* enable autoFlush */);
            process(in, out);
            process2(socket, in, out);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void process2(Socket socket, BufferedReader in, PrintWriter out) throws IOException {
        System.out.println("输入要发送给服务器的消息（输入SHUTDOWN结束）：");
        Scanner scanner = new Scanner(System.in);
        while (!STOP_AND_CLOSE) {
            String line = scanner.nextLine();

            /* 读写集中处理！！！ */
//        processWrite(out, line);
            // 按行读取并打印
            processWrite(out, line);
            processRead(in); // 连续两次写再读将抛出异常，无法按行读取数据，使用其他读取方法in.read(...)

            if (CommandConstant.CLOSE.equals(line)) {
                STOP_AND_CLOSE = true;
            }
        }
    }

    private static void processWrite(PrintWriter out, Object message) {
        out.println/*write 单处理*/(message);
    }

    /**
     * 按行读取并打印
     */
    private static void processRead(BufferedReader in) throws IOException {

        String readFromServer = in.readLine();
        System.out.println(readFromServer);
    }

    private static void process(BufferedReader in, PrintWriter out) throws IOException {
        String readFromServer = in.readLine();
        System.out.println(readFromServer);
        // 连接建立后，开始通信
        out.println/*write 单处理*/("Hi Server, socket connected and i am pp."); // 发送完成后直接主动关闭socket
        readFromServer = in.readLine();
        System.out.println(readFromServer);
    }

    private static void connect(Socket socket/*socket可以显式的指定服务器地址和客户端地址
                            见构造方法重载版本 Socket(String host, int port, InetAddress localAddr, int localPort)*/) throws IOException {
        // 构造方法、connect、bind方法会抛出异常，具体异常查看源码。。
        // java.net.UnknownHostException
        // java.net.SocketException
        // 其他异常请分析源码...
        socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
    }

    private static void option(Socket socket) throws IOException {
        socket.setReuseAddress(true);
        // 使用Nagle算法（去查查吧！）
        // Nagle的算法通常会在TCP程序里添加两行代码，在未确认数据发送的时候让发送器把数据送到缓存里。任何数据随后继续直到得到明显的数据确认或者直到攒到了一定数量的数据了再发包。
        socket.setTcpNoDelay(true);
        // socket close时，是否立即关闭底层socket，可以指定最大停留时间（秒为单位）
        socket.setSoLinger(true, 1);
        // 长时间处于空闲的socket是否自动关闭它
        socket.setKeepAlive(false);
        // 设置此套接字的性能偏好
        // 分别指示短连接时间、低延迟和高带宽的相对重要性
        // socket连接之后，此选项无效
        socket.setPerformancePreferences(1, 1, 1);
    }
}
