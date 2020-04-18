package org.pp.java8.algorithm.datastruct.test;

import org.pp.java8.algorithm.datastruct.nonlinear.Graph;
import org.pp.java8.algorithm.datastruct.nonlinear.MatrixGraph;

public class GraphTest {
    public static void main(String[] args) {
        Graph g = initGraph();
        printGraph(g);
    }

    /**
     * 构造有向图--顶点连接，边初始化
     */
    private static Graph initGraph() {
        Graph graph = new MatrixGraph(5);
        graph.setEdge(0, 1, 1);
        graph.setEdge(0, 3, 2);
        graph.setEdge(0, 4, 1); // [0][1] [0][4] 权重相等
        graph.setEdge(1, 3, 1);
        graph.setEdge(2, 4, 1);
        graph.setEdge(3, 2, 1);
        graph.setEdge(4, 1, 1);
        return graph;
    }

    private static void printGraph(Graph g) {
        System.out.println("*******************************************");
        System.out.println("顶点数:" + g.vertices());
        System.out.println("边数:" + g.edges());
        System.out.println(g.first(0));
        System.out.println(g.next(0, 1));
    }
}
