# 排序算法--伪代码描述
#### 选择排序    
    描述：扫描整个列表，找到它的最小元素，然后和第一个元素交换，将最小元素放在它的最终位置。
    算法：SelectionSort(A[0...n-1])
        // 选择排序，对给定数组排序
        for i<-0 to n-2 do
          min<-i
          for j<-i+1 to n-1 do
            if A[j] < A[min] min<-j
          swap A[i] and A[min] 

#### 希尔排序
    描述：

#### 堆排序
    堆：堆序性质，二叉堆数据结构是一种数组对象，它可以被视为一颗完全二叉树。
    完全k叉树是所有的叶结点都有相同的深度，并且所有的内部结点度都为k。见《算法导论》附录B.5.3 P673
    完全二叉树具有严格的形状要求：对于高度为h的二叉树，除了最下面一层第h层以外其他各层都是满的，并且第h层的结点都集中在该层最左边
    完全二叉树是最优平衡二叉树，插入删除查找渐进效率要看具体实现堆的数据结构
    
    堆排序：
    1 将输入数组A[0...n-1]构建堆--构建完全二叉树性质
    2 取堆顶元素并删除，重新堆化--保持完全二叉树性质
    3 重复过程2，直到堆中只剩堆顶和它的左子结点，这时不需要比较，堆确保它们的大小顺序
    
    这里给出堆排序伪代码：
    MAX-HEAPIFY(A, i) 数组堆化 使i为根的子树成为最大堆
    l <- LEFT(i)
    r <- RIGHT(i)
    if l <= heap-size[A] and A[l] > A[i]
    then largest <=- l
    else largest <- i
    if r <= heap-size[A] and A[r] > A[largest]
    then largest <- r
    if largest != i
    then swap(A[i], A[largest])
         MAX-HEAPIFY(A, largest)
    算法说明：
    给定某个结点的下标i，其父节点下标PARENT(i) = floor(i/2) 左子结点下标LEFT(i) = 2i 右子结点下标RIGHT(i) = 2i+1
    这里注意：i在序列中A[1...n]，对于数组A[0...n-1] i = i-1 规则如下：
    父子结点 PARENT(i) = floor((i-1)/2) LEFT(i) = 2i+1 RIGHT(i) = 2i+2
    兄弟节点 LEFT-SIBLING(i) = i - 1  RIGHT-SIBLING(i) = i + 1
    
    BUILD-MAX-HEAP(A) 建堆
    heap-size[A] <- length[A]
    for i <- floor(length[A]/2) downto 1
    do MAX-HEAPIFY(A, i)
    
    堆排序
    HEAP-SORT(A)  A[0...n-1]
    BUILD-MAX-HEAP(A) // O(n)=nlogn
    for i <- length[A] downto 2
    do swap(A[0], A[i])
      heap-size[A]--
      MAX-HEAPIFY(A, 0)

    优先队列：参考Java中 java.util.PriorityQueue 一个最简单的模型如下：
    addElement(T) removeElement() pollTop()
    