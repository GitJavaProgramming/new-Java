package org.pp.socket.bio;


import org.pp.socket.CommandConstant;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

class ClientHandler implements Runnable {

    private final Socket socket;

    private boolean isFirstConnected = true;

    //    private static final String CLOSE = "SHUTDOWN";
    private /*volatile*/ boolean STOP_AND_CLOSE = false;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("welcome: " + socket.getRemoteSocketAddress());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (!STOP_AND_CLOSE) { // 保证in、out不关闭

                // 连接建立后，开始通信
                if (isFirstConnected) {
                    out.println("welcome");
                    isFirstConnected = false;
                }

                /* 读写集中处理！！！ tip：可以分开到不同线程池吗？怎么进行数据交互？ */
//            processOps(in, out);
                String str = (String) processRead(in); // 强制转换string，还是使用泛型？？
                if (CommandConstant.CLOSE.equals(str)) {
                    STOP_AND_CLOSE = true;
                    out.println("thank you.");
                    System.out.println("client disconnect...");
                    continue; // break;
                }
                processWrite(out, str);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // java.net.SocketException: Connection reset
            // an I/O error has occurred.
            // 这里可以断线重连吗  检测连接状态，重连？？
            System.out.println("an I/O error has occurred 断开连接......");
            e.printStackTrace();
        }
    }


    // 对于java.io包来说，具体实现的流全部是4个大抽象类的子类，运用方法重载实现不同处理

    private static void processWrite(PrintWriter writer, Object message) {
        writer.println("Server Response: " + message);
    }

    private static Object processRead(BufferedReader reader) throws IOException {
        String str = reader.readLine(); // 按行读取...
        System.out.println("read Command ->  " + str); // 直接标准输出从客户端读到的消息
        return str;
    }

    private void processOps(BufferedReader reader, PrintWriter writer) throws IOException {
        String str = reader.readLine(); // 按行读取...
        System.out.println(str); // 直接标准输出从客户端读到的消息

        writer.println("Server Echo: " + str);
    }

    private void processReadOps(Reader reader) {
    }

    private void processReadOps(InputStream inputStream) {
    }

    private void processWriteOps(Writer writer) {
    }

    private void processWriteOps(OutputStream outputStream) {
    }

}