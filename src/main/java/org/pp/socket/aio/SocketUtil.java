package org.pp.socket.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SocketUtil {
    public static final String SHUTDOWN = "SHUTDOWN";

    public static String readBufferToString(ByteBuffer byteBuffer) {
        return readBufferToString(byteBuffer, Charset.forName("utf-8"));
    }

    public static String readBufferToString(ByteBuffer byteBuffer, Charset charset) {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return new String(bytes, charset);
    }

    public static void writeBufferToChannel(SocketChannel sc, String msg) throws IOException {
        if (msg != null && msg.trim().length() > 0) {
            byte[] bytes = msg.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            sc.write(buffer);
        }
    }
}
