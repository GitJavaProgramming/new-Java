package org.pp.socket.mq;

import java.io.IOException;

public class ConsumeClient {

    public static void main(String[] args) throws IOException {
        MqClient client = new MqClient();
        String str = client.consume();
        if(str == null) {
            System.out.println("消息队列没有消息。");
        } else {
            System.out.println("获取的消息为：" + str);
        }
    }
}
