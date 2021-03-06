# HashMap的非线程安全性与ConcurrentHashMap的线程安全性
    源码使用的是：JDK1.8.0_221
    什么是线程安全？？
    为什么HashMap非线程安全？？
    为什么ConcurrentHashMap能保证线程安全？？
    下文中有？？的说明不不清楚，需要求证。
## HashMap是非线程安全的映射
    分析：
        HashMap结构，这里只列出共享可变资源
```java
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {

    transient Node<K,V>[] table;
    transient int size;
    // ...
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
        // getter
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // 无符号右移，高位全部补0，只比较高16位
    }
    
    public V put(K key, V value) {
        // final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict)
        // newNode(hash, key, value, null); 
        return putVal(hash(key), key, value, false, true);
    }
    
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        // ...
        // JDK1.8在Node尾结点插入
        if ((e = p.next) == null) {  // 链表尾节点
           p.next = newNode(hash, key, value, null);
           if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
               treeifyBin(tab, hash);
           break;
        }
        // ...
    }
}
```
    
    在放入元素时，put操作会用到hash(key)去构造要放入的Node结点，并且决定要放在table（数组）哪个索引上
    
    HashMap的线程安全问题：并发场景下
    源码HashMap.resize()中newTab[e.hash & (newCap - 1)] = e;
    如果线程A正在put操作，要插入的Node结点构造了，尾节点也找到了。
    此时线程B也在put，但是put时发现需要resize，线程B执行resize()完成。
    此时线程A操作完成，但是有可能resize后这个尾节点已经不是之前A找到的尾节点啦，但是A线程就是把Tail.next指向了newNode。
    这样的（扩容可变化）尾结点一多那么形成环状结构的概率就会加大，就造成了内存泄漏了。
    如果要模拟那么需要严格控制hash值，让尾节点扩容可变化，比如字符串作为key的hash值的高16位。
    一些知识可以参考本项目源码：org.pp.java8.lang.JavaLangTest.testHashCode()
## ConcurrentHashMap的线程安全特性
    ConcurrentHashMap源码
```java
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V>
    implements ConcurrentMap<K,V>, Serializable {

    /* volatile可以保证table引用的可见性和table构造的有序性 */
    transient volatile Node<K,V>[] table;
    private transient volatile Node<K,V>[] nextTable;
    private transient volatile long baseCount;
    private transient volatile int sizeCtl; // map中元素个数控制器  int默认值位0
    
    final V putVal(K key, V value, boolean onlyIfAbsent) {
        int hash = spread(key.hashCode()); // hash 决定了put结点的桶索引
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f/*辅助指针*/; int n, i, fh;
            if (tab == null || (n = tab.length) == 0) // 首次put时初始化table
                tab = initTable();
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { // 空桶直接放 如果不是空桶则找到了新节点要插入的桶
                // 新建结点 cas插入后退出循环
            }
            else if ((fh = f.hash) == MOVED) // 如果在扩容就帮助扩容，下次遍历再判断
                tab = helpTransfer(tab, f);
            else { // 不是首次插入 没有扩容 不是空桶 则意味着在非空桶链表中插入
                // ...
                synchronized (f) { // f指向桶头节点，锁定f也是锁定桶，避免扩容时Node标记 相当于某个线程的快照
                    // 执行到这里，当前线程中f指向的Node是没有被标记的
                    if (tabAt(tab, i) == f) { // 桶f是否已经被其他线程标记  扩容影响：桶索引是否变动（不会）、Node是否标记
                        if (fh >= 0) { // 结点为链表
                            // put
                        }
                        else if (f instanceof TreeBin) { // 红黑树
                            // putTreeVal
                        }
                    }
                }
            }
        }
        // ...
    }
    
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS; // HASH_BITS = 0x7fffffff usable bits of normal node hash
    }
    
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
            if ((sc = sizeCtl) < 0)
                Thread.yield(); // lost initialization race; just spin
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                // table初始化竞争，竞争失败的线程sizeCtl变量副本减去某个数，此时sizeCtl值是小于0的
                break; // 有且只有一个线程获胜 
            }
        }
        return tab;
    }
    
    // tabAt(tab, i = (n - 1) & hash))  其中 n = tab.length
    // 获取table数组中的索引为i的桶
    static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
        return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
    }
    
    /* 如果线程发现map正在扩容就帮助扩容 */
    // ...
    final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
        Node<K,V>[] nextTab; int sc;
        if (tab != null && (f instanceof ForwardingNode) &&
            (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
            // ...
            transfer(tab, nextTab);
            // ...
        }
        return table;
    }

    private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
        // ...
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // 多核处理器时 充分利用cpu 计算每个线程帮助扩容的长度
        // ...  
        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        // ...
    }


}
```
    ConcurrentHashMap就是要对映射中的可变量进行控制做到线程安全
    
    ConcurrentHashMap中hash碰撞时的链表插入
    链表头插法：将新节点插入到头节点之后。插入的元素位置总是链表第一个。
    链表尾插法：将新节点插入到尾节点之后，新节点称为新的尾节点（由于是单链表）。
    
    JDK1.8之前的ConcurrentHashMap，由于链表插入结构是头插法，用CAS实现很复杂（我猜的！！）所以采用的是分段锁的方式控制。
    分段锁是将哈希映射按某种算法进行分段，每个段都是一个锁，并发时，都在每段上等待锁释放（再争夺）。
    可以参考JDK1.7源码段Segment是Lock锁的实例 分段锁可以保证线程安全，但并不能够支持高（数量的线程）并发。
    
    JDK1.8的采用CAS方式（保证原子性）控制并发，能支持更高的并发量，CAS是一种底层乐观锁方式（采用的是机器指令CMPXCHG实现）。
    这个指令可以在Intel官网找到。intel 64与IA32架构软件开发手册 https://software.intel.com/en-us/articles/intel-sdm 在本
    项目中也可以找到 org/pp/java8/concurrent/253666-sdm-vol-2a[291-295]-CMPXCHG指令.pdf
    
    线程安全控制在上文Java代码注释好了
    
    上文比较了HashMap和ConcurrentHashMap的put操作的线程安全特性
    ConcurrentHashMap很好的解决了HashMap的非线程安全问题，在桶上加锁获得更高的并发量（理想情况下每个桶只有一个元素，每个元素
    都可以由一个线程控制）
## ConcurrentHashMap的扩容妙招

    1.如何判断map中某个Node桶正在扩容
    (fh = f.hash) == MOVED  // MOVED = -1; // hash for forwarding nodes
    说明此时Node已经转变成ForwardingNode 由ForwardingNode构造方法可知hash = MOVE = -1
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    Node桶标识为ForwardingNode表示该桶已经处理过是一个待转移桶
    
    2.扩容的情形出现在以下操作:
    transfer -> helpTransfer() ->        // 帮助扩容时
                                putVal(K, V, boolean)
                                replaceNode(Object, V, Object)
                                clear()
                                computeIfAbsent(K, Function<? super K, ? extends V>)
                                computeIfPresent(K, BiFunction<? super K, ? super V, ? extends V>)
                                compute(K, BiFunction<? super K, ? super V, ? extends V>)
                                merge(K, V, BiFunction<? super V, ? super V, ? extends V>)
    transfer -> addCount -> putVal -> put
    transfer -> tryPresize -> putAll
    transfer -> tryPresize -> treeifyBin  // 如果映射大小小于MIN_TREEIFY_CAPACITY = 64 就扩容
    
    ForwardingNode提供find方法  ForwardingNode.find -> ConcurrentHashMap.get 在map.get时如果在扩容就自旋查找了
    ForwardingNode.find在这里使用了loop跳转，当扩容时自旋直到扩容完成Node结点类型不再是ForwardingNode
    
    3. 发挥多核优势，计算每个线程扩容长度
    4.扩容完成：把数组中的节点复制到新的数组的相同位置，或者移动到扩张部分的相同位置
    
    省略关于扩容时数据移动情况

    
    
    
    
    