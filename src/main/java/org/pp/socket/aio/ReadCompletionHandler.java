package org.pp.socket.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        String cmd = SocketUtil.readBufferToString(attachment);
        System.out.println("time server receive order :" + cmd);
        String currTime = "QUERY TIME ORDER".equalsIgnoreCase(cmd) ? (new SimpleDateFormat("yyyy年MM月dd日").format(new Date())) : "BAD ORDER"; // 生成发送给客户端的消息
        doWrite(currTime);
    }

    private void doWrite(final String currTime) {
        if(currTime != null && currTime.trim().length() > 0) {
            byte[] bytes = currTime.getBytes();
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining())
                        channel.write(attachment,attachment,this);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        ReadCompletionHandler.this.channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
