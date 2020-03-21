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
                    if (tabAt(tab, i) == f) { // 由于存在扩容影响需要再次判断f所在的桶是否变动，保证map数据一致
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
            int rs = resizeStamp(tab.length);
            while (nextTab == nextTable && table == tab &&
                   (sc = sizeCtl) < 0) {
                if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                    sc == rs + MAX_RESIZERS || transferIndex <= 0)
                    break;
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                    transfer(tab, nextTab);
                    break;
                }
            }
            return nextTab;
        }
        return table;
    }

    private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
        int n = tab.length, stride;
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // subdivide range
        if (nextTab == null) {            // initiating
            try {
                @SuppressWarnings("unchecked")
                Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
                nextTab = nt;
            } catch (Throwable ex) {      // try to cope with OOME
                sizeCtl = Integer.MAX_VALUE;
                return;
            }
            nextTable = nextTab;
            transferIndex = n;
        }
        int nextn = nextTab.length;
        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        for (int i = 0, bound = 0;;) {
            Node<K,V> f; int fh;
            while (advance) {
                int nextIndex, nextBound;
                if (--i >= bound || finishing)
                    advance = false;
                else if ((nextIndex = transferIndex) <= 0) {
                    i = -1;
                    advance = false;
                }
                else if (U.compareAndSwapInt
                         (this, TRANSFERINDEX, nextIndex,
                          nextBound = (nextIndex > stride ?
                                       nextIndex - stride : 0))) {
                    bound = nextBound;
                    i = nextIndex - 1;
                    advance = false;
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
                int sc;
                if (finishing) {
                    nextTable = null;
                    table = nextTab;
                    sizeCtl = (n << 1) - (n >>> 1);
                    return;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                        return;
                    finishing = advance = true;
                    i = n; // recheck before commit
                }
            }
            else if ((f = tabAt(tab, i)) == null)
                advance = casTabAt(tab, i, null, fwd);
            else if ((fh = f.hash) == MOVED)
                advance = true; // already processed
            else {
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        Node<K,V> ln, hn;
                        if (fh >= 0) {
                            int runBit = fh & n;
                            Node<K,V> lastRun = f;
                            for (Node<K,V> p = f.next; p != null; p = p.next) {
                                int b = p.hash & n;
                                if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            if (runBit == 0) {
                                ln = lastRun;
                                hn = null;
                            }
                            else {
                                hn = lastRun;
                                ln = null;
                            }
                            for (Node<K,V> p = f; p != lastRun; p = p.next) {
                                int ph = p.hash; K pk = p.key; V pv = p.val;
                                if ((ph & n) == 0)
                                    ln = new Node<K,V>(ph, pk, pv, ln);
                                else
                                    hn = new Node<K,V>(ph, pk, pv, hn);
                            }
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                        else if (f instanceof TreeBin) {
                            TreeBin<K,V> t = (TreeBin<K,V>)f;
                            TreeNode<K,V> lo = null, loTail = null;
                            TreeNode<K,V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            for (Node<K,V> e = t.first; e != null; e = e.next) {
                                int h = e.hash;
                                TreeNode<K,V> p = new TreeNode<K,V>
                                    (h, e.key, e.val, null, null);
                                if ((h & n) == 0) {
                                    if ((p.prev = loTail) == null)
                                        lo = p;
                                    else
                                        loTail.next = p;
                                    loTail = p;
                                    ++lc;
                                }
                                else {
                                    if ((p.prev = hiTail) == null)
                                        hi = p;
                                    else
                                        hiTail.next = p;
                                    hiTail = p;
                                    ++hc;
                                }
                            }
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                                (hc != 0) ? new TreeBin<K,V>(lo) : t;
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                                (lc != 0) ? new TreeBin<K,V>(hi) : t;
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                    }
                }
            }
        }
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

    扩容终极一问：如何扩容（扩容算法）？？
    
    如何判断map正在扩容？？
    (fh = f.hash) == MOVED  // MOVED = -1; // hash for forwarding nodes
    
    当Node桶标识为ForwardingNode表示该桶已经处理过是一个待转移桶
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    
    transfer迁移桶出现在以下几处:
    transfer -> helpTransfer() // 帮助扩容时
    transfer -> addCount -> putVal -> put
    transfer -> tryPresize -> putAll
    transfer -> tryPresize -> treeifyBin  // 如果映射大小小于MIN_TREEIFY_CAPACITY = 64 就扩容
    
    ForwardingNode提供find方法  ForwardingNode.find -> ConcurrentHashMap.get 在map.get时如果在扩容也可以安全的取到值
    
    线程帮助扩容时如果发现帮助的是已处理过的待转移桶 如何寻找下一个？？
    
    

    
    
    
    
    
    
    
    
    