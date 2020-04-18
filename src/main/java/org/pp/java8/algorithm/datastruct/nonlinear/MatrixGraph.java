package org.pp.java8.algorithm.datastruct.nonlinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 图--邻接矩阵，行列分别代表边的起点和终点
 */
public class MatrixGraph extends AbstractGraph {

    /**
     * 抽象矩阵--图在其中
     */
    private int[][] matrix;

    /**
     * 是否有向，默认有向
     */
//    private boolean hasDirection = true;
    public MatrixGraph(int numVertex/*矩阵维度*/) {
        this.numVertex = numVertex;
        this.numEdge = 0;
        this.matrix = new int[numVertex][numVertex]; // 初始矩阵中各点为0
    }

//    public boolean isHasDirection() {
//        return hasDirection;
//    }

    /**
     * 起点v的第一条边
     */
    @Override
    public int first(int v) {
        int i;
        for (i = 0; i < numVertex; i++) {
            if (matrix[v][i] != 0) {
                break;
            }
        }
        return i;
    }

    public int firstWithWeight(int v) {
        final List<Pair<Pair<Integer, Integer>/*边*/, Integer/*权重*/>> weightList = new ArrayList<>();
        for (int i = 0; i < numVertex; i++) {
            int weight = matrix[v][i];
            if (weight != 0) {
                weightList.add(new Pair(new Pair<>(v, i), weight)/*某个边的权重*/);
            }
        }
        weightList.sort(Pair::compareTo);
        return weightList.get(0).k.v; // 返回权重最大的边的终点
    }

    /**
     * 起点v1，边(v1,v2)的下一条边，返回终点
     */
    @Override
    public int next(int v1, int v2) {
        int i;
        for (i = v2 + 1; i < numVertex; i++) {
            if (matrix[v1][i] != 0) {
                break;
            }
        }
        return i;
    }

    public int nextWithWeight(int v1, int v2) {
        final List<Pair<Pair<Integer, Integer>/*边*/, Integer/*权重*/>> weightList = new ArrayList<>();
        for (int i = v2 + 1; i < numVertex; i++) {
            int weight = matrix[v1][i];
            if (weight != 0) {
                weightList.add(new Pair(new Pair<>(v1, i), weight)/*某个边的权重*/);
            }
        }
        weightList.sort(Pair::compareTo);
        return weightList.get(0).k.v;
    }

    @Override
    public void setEdge(int v1, int v2, int wgt) {
        rangeCheck(v1, v2, wgt);

        if (matrix[v1][v2] == 0) {
            numEdge++;
        }
        matrix[v1][v2] = wgt; // (v1,v2)边的权重，可用于确定优先级
//        if (!hasDirection) { // 无向图
//            matrix[v2][v1] = wgt;
//        }
    }

    @Override
    public int weight(int v1, int v2) {
        return matrix[v1][v2]; // (v1, v2)不存在边时 返回0
    }

    public void print() {
        Stream.of(matrix).forEach(m -> System.out.println(Arrays.toString(m)));
    }

    protected class Pair<K extends Comparable<K>, V extends Comparable<V>/*比较V, K*/> implements Comparable<Pair<K, V>> {
        K k;
        V v;

        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }

        @Override
        public int compareTo(Pair<K, V> o) {
            if (o.v.compareTo(this.v) == 0) { // 权重相等的边,按终点值正常顺序比较
                return this.k.compareTo(o.k);
            }
            return o.v.compareTo(this.v); // 反向排序
//            return this.v.compareTo(o.v);
        }
    }
}
