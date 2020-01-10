# 排序算法--伪代码描述
#### 选择排序    
    语言描述：扫描整个列表，找到它的最小元素，然后和第一个元素交换，将最小元素放在它的最终位置。
       算法：SelectionSort(A[0...n-1])
            // 选择排序，对给定数组排序
            for i<-0 to n-2 do
              min<-i
              for j<-i+1 to n-1 do
                if A[j] < A[min] min<-j
              swap A[i] and A[min] 
