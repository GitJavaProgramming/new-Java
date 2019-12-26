Iterable  迭代器接口
实现这个接口可以迭代遍历

    AbstractCollection 四大抽象子类
        AbstractList
        AbstractQueue
        AbstractSet
        AbstractDeque

    List接口在AbstractList抽象类实现Iterator，可用于公共的toString---AbstractCollection

    Set接口 内部使用Map接口实现
        哈希表实现
        平衡二叉树-红黑树实现
            实现排序Set接口 比较器外部传入

    Queue接口
        public interface BlockingQueue<E> extends Queue<E>
        public interface Deque<E> extends Queue<E>
        abstract class AbstractQueue<E> implements Queue<E>
        队列基本实现：继承AbstractQueue 实现BlockingQueue/Deque
        常用队列---
            PriorityQueue
            LinkedList实现的双端队列
            BlockingQueue---阻塞队列系列 并发包下面很多个实现类（使用并发api实现阻塞）
                    ArrayBlockingQueue ：一个由数组结构实现的有界阻塞队列。
                    LinkedBlockingQueue ：一个由链表结构实现的有界阻塞队列。
                    PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
                    DelayQueue：一个使用优先级队列实现的无界阻塞队列。
                    SynchronousQueue：一个不存储元素的阻塞队列。
                    LinkedTransferQueue：一个由链表结构实现的无界阻塞队列。
                    LinkedBlockingDeque：一个由链表结构实现的双向阻塞队列。
            ADT：4种形式的处理方式,具体参考实现类源码如：java.util.concurrent.ArrayBlockingQueue
                            Throws exception    Special value   Blocks              Times out
                Insert       add(e)              offer(e)        put(e)              offer(e, time, unit)
                Remove       remove()            poll()          take()              poll(time, unit)
                Examine      element()           peek()          not applicable      not applicable

    Map
        HashMap Map.Entry Node数组
        TreeMap 平衡二叉树-红黑树
            如果一颗二叉树除了最下面一层可能不满外，其他各层都是满的，该二叉树就是平衡二叉树。

     红黑树参考：
         https://blog.csdn.net/v_JULY_v/article/details/6105630
         其中包括红黑树的概念、红黑树的插入、删除、旋转修正

序列化接口 包下面所有类基本都实现了Serializable接口，可以序列化
    实现 Serializable 接口允许对象序列化，以保存和恢复对象的全部状态，并且允许类在写入流时的状态和从流读取时的状态之间变化。
    它自动遍历对象之间的引用，保存和恢复全部图形。

    实现 Externalizable 接口允许对象假定可以完全控制对象的序列化形式的内容和格式。
    调用 Externalizable 接口的方法（writeExternal 和 readExternal）来保存和恢复对象状态。
    当这两种方法被某个类实现时，它们可以使用 ObjectOutput 和 ObjectInput 的所有方法读写其本身的状态。
    对象负责处理出现的任何版本控制。


