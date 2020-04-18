package org.pp.java8.algorithm.datastruct.nonlinear;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图--邻接矩阵，行列分别代表边的起点和终点
 */
public class MatrixGraph extends AbstractGraph {

    /**
     * 抽象矩阵--图在其中
     */
    private int[][] matrix;
    /**
     * 顶点数，也是初始矩阵维度
     */
    private int numVertex;
    /**
     * 边
     */
    private int numEdge;

    /**
     * 是否有向，默认有向
     */
    private boolean hasDirection = true;

//    /**
//     * 权重列表，某个位置的权重
//     */
//    private final List<Map<Integer, Integer>> weightList;

    public MatrixGraph(int numVertex/*矩阵维度*/) {
        this.numVertex = numVertex;
        this.numEdge = 0;
        this.matrix = new int[numVertex][numVertex]; // 初始矩阵中各点为0
//        this.weightList = new ArrayList<>();
    }

    public boolean isHasDirection() {
        return hasDirection;
    }

    @Override
    public int vertices() {
        return numVertex;
    }

    @Override
    public int edges() {
        return numEdge;
    }

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

    @Override
    public void setEdge(int v1, int v2, int wgt) {
        if (v1 > numVertex || v2 > numVertex) {
            System.out.println("点不在矩阵中.");
            return;
        }
        if (wgt <= 0) {
//            throw new IllegalStateException("权重必须大于0.");
            System.out.println("设置边时权重必须设置为大于0.");
            return;
        }
        if (matrix[v1][v2] == 0) {
            numEdge++;
        }
        matrix[v1][v2] = wgt; // (v1,v2)边的权重，可用于确定优先级
        if (!hasDirection) { // 无向图
            matrix[v2][v1] = wgt;
        }
    }

    @Override
    public int weight(int v1, int v2) {
        return matrix[v1][v2]; // (v1, v2)不存在边时 返回0
    }
}
