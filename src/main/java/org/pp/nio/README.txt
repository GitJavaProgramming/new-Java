java.nio
    生成UML图可以看到，
        顶层抽象类：Buffer
        接口：DirectBuffer （package sun.nio.ch;）
    Buffer:缓冲区是特定原始类型的元素的线性有限序列。 除了其内容之外，缓冲区的基本属性还包括其容量，限制和位置。
            // Invariants: mark <= position <= limit <= capacity
            private int mark = -1;
            private int position = 0; // 当前读写的位置
            private int limit; // 当前读写的终点位置
            private int capacity; // 缓冲区可以保存多少数据
        提供三个基本操作方法：
            clear：极限设为容量，位置设为0。在使用一系列通道读取或放置操作填充此缓冲区之前，请调用此方法。
            flip：翻转此缓冲区。 将限制设置为当前位置，然后将该位置设置为零。 如果定义了标记，则将其丢弃。
                在执行一系列通道读取或放置操作之后，调用此方法以准备一系列通道写入或相对get操作。
            rewind：倒带此缓冲区。 将位置设置为零并将标记丢弃。假定已正确设置了限制，请在执行一系列通道写入或获取操作之前调用此方法。
    Buffer是抽象类，有众多直接子类，它们都是抽象类，最基本的字节缓冲区ByteBuffer、CharBuffer。
        使用静态工厂方法创建实例：
            Allocates a new byte buffer.
            The new buffer's position will be zero, its limit will be its capacity,
            its mark will be undefined, and each of its elements will be initialized to zero.
            // 分配缓冲区，新缓冲区的位置将为零，其极限将是其容量，其标记将是未定义的，并且其每个元素都将初始化为零。
            ByteBuffer.allocate(int capacity);  // Heap
            ByteBuffer.allocateDirect(int capacity);

    最后一点也是比较重要一点：标准IO、IO效率与缓冲区 详细见Unix环境高级编程 ch03-文件IO/IO效率、ch05-标准IO/缓冲

    字符编码Charset
    编码：字符串转换为字节序列，编码需要指定字符集（如GBK，unicode）。

    Channel 连接数据源/数据汇与缓冲区。查看UML以及Java源代码比较各个类的用途！
        SelectableChannel是一种支持阻塞IO和非阻塞IO的通道，SelectableChannel可以向Selector注册读写就绪等事件，Selector负责监控
        这些事件，等到事件发生时，SelectableChannel就可以执行匹配的相关操作。
            Selector
            为了完成就绪选择，要将不同的通道注册到一个Selector对象。为每个通道分配一个或多个SelectionKey，通过轮询Selector对象确定哪些
            通道已准备就绪。
        FileChannel 查看UML  AbstractInterruptibleChannel






