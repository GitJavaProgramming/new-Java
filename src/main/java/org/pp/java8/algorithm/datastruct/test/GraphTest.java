package org.pp.java8.algorithm.datastruct.test;

import org.pp.java8.algorithm.datastruct.nonlinear.Graph;
import org.pp.java8.algorithm.datastruct.nonlinear.ListGraph;
import org.pp.java8.algorithm.datastruct.nonlinear.MatrixGraph;

import java.util.List;

public class GraphTest {
    public static void main(String[] args) {
        Graph g = paint(new MatrixGraph(5));
        g = paint(new ListGraph(5)); // breakpoint

        graphInfo(g);
    }

    private static Graph paint(Graph graph) {
        graph.setEdge(0, 1, 1);
        graph.setEdge(0, 3, 2);
        graph.setEdge(0, 4, 1); // [0][1] [0][4] 权重相等
        graph.setEdge(1, 3, 1);
        graph.setEdge(2, 4, 1);
        graph.setEdge(3, 2, 1);
        graph.setEdge(4, 1, 1);
        return graph;
    }

//    /**
//     * 构造有向图--顶点连接，边初始化
//     */
//    private static Graph initGraph() {
//        Graph graph = new MatrixGraph(5);
//        graph.setEdge(0, 1, 1);
//        graph.setEdge(0, 3, 2);
//        graph.setEdge(0, 4, 1); // [0][1] [0][4] 权重相等
//        graph.setEdge(1, 3, 1);
//        graph.setEdge(2, 4, 1);
//        graph.setEdge(3, 2, 1);
//        graph.setEdge(4, 1, 1);
//        return graph;
//    }

    private static void graphInfo(Graph g) {
        System.out.println("*******************************************");
        System.out.println("顶点数:" + g.vertices());
        System.out.println("边数:" + g.edges());
        System.out.println("以0为起点的第一条边的终点:" + g.first(0));
        System.out.println("边(0,1)的下一条边的终点:" + g.next(0, 1));
        System.out.println("*********************dfs**********************");
        g.dfs(g);
        System.out.println("*********************bfs**********************");
        g.bfs(g);
        System.out.println("*******************************************");
        // 注意first next不能和firstWithWeight nextWithWeight混用，只能选其一
        if(g instanceof MatrixGraph) {
            ((MatrixGraph) g).print(); // 打印矩阵
            System.out.println("以0为起点的最大权重边的终点:" + ((MatrixGraph) g).firstWithWeight(0));
            System.out.println("(0,x)下一条边:" + ((MatrixGraph) g).nextWithWeight(0, ((MatrixGraph) g).firstWithWeight(0)));
        }
        if(g instanceof ListGraph) {
            ((ListGraph) g).print(); // 打印邻接表
        }
    }
}
