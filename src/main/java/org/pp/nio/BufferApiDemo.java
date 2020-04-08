package org.pp.nio;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BufferApiDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(20);
//        buffer.clear();
        buffer.put("hello world".getBytes());
        System.out.println(buffer.position());  // 11
        System.out.println(buffer.limit()); // 100

        // 将限制设置为当前位置，然后将该位置设置为零
        buffer.flip();
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.limit()); // 11
        // 准备读取数据
        int limit = buffer.limit();
        byte[] bytes = new byte[limit];
        buffer.get(bytes); // 把position到limit之间的字节读到byte[]中
        System.out.println(new String(bytes)); //使用字节数组，可以转换为其他任何对象，只要类型兼容！
        System.out.println(Arrays.toString(bytes));
        System.out.println(buffer.position()); // 11
        System.out.println(buffer.limit()); // 11

//        buffer.mark();

        // clear Buffer 填充缓冲区前使用
        buffer.clear();  // mark=-1 被清除了！！
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.limit()); // 100
        System.out.println(buffer.hasRemaining()); // true 数据并没有清除

//        buffer.reset();

        // 此时limit已经等于容量
        limit = buffer.limit();
        bytes = new byte[limit];
        buffer.get(bytes);  // 从buffer中读数据会影响标志位 position、limit
        System.out.println(new String(bytes));
        System.out.println(new String(bytes).length());
        System.out.println(Arrays.toString(bytes));

        // 查阅Java几种引用的强弱比较
        WeakReference/*<ByteBuffer>*/ weakReference = new WeakReference(buffer);
        weakReference.clear();
//        buffer.rewind();  //  写数据
        /*public final Buffer rewind() {
            position = 0;
            mark = -1;
            return this;
        }*/

    }
}
