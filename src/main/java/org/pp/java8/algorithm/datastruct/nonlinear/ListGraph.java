package org.pp.java8.algorithm.datastruct.nonlinear;

import java.util.stream.IntStream;

/**
 * 图-邻接表实现
 * 以链表为元素的数组，数组索引代表起点，链表中每个节点代表一条边
 */
public class ListGraph extends AbstractGraph {

    private Edge[] edges;

    public ListGraph(int numVertex) {
        this.numVertex = numVertex;
        this.numEdge = 0;
        this.edges = new Edge[numVertex];
    }

    @Override
    public int first(int v) {
        Edge edge = edges[v];
        if (edge == null) {
            return numVertex;
        } else {
            return edge.vertex;
        }
    }

    @Override
    public int next(int v1, int v2) {
        Edge edge = edges[v1];
        if (edge == null) {
            return numVertex;
        } else {
            while (edge != null && (edge.vertex < v2)) {
                edge = edge.next;
            }
            if (edge != null && (edge.vertex == v2) && (edge.next != null)) {
                return edge.next.vertex;
            } else {
                return numVertex;
            }
        }
    }

    @Override
    public void setEdge(int v1, int v2, int wgt) {
        rangeCheck(v1, v2, wgt);

        Edge curr = edges[v1];
        if (curr == null || curr.vertex > v2) {
            edges[v1] = new Edge(v2, wgt, curr);
            numEdge++;
        } else if (curr.vertex == v2) {
            curr.weight = wgt;
        } else {
            while ((curr.next != null) && (curr.next.vertex < v2)) {
                curr = curr.next;
            }
            if ((curr.next != null) && (curr.next.vertex == v2)) {
                curr.next.weight = wgt;
            } else {
                curr.next = new Edge(v2, wgt, curr.next);
                numEdge++;
            }
        }
    }

    @Override
    public int weight(int v1, int v2) {
        for (Edge curr = edges[v1]; curr != null; curr = curr.next) {
            if (curr.vertex == v2) { // 找到边
                return curr.weight;
            }
        }
        return 0;
    }

    public void print() {
//        Stream.of(edges).forEach(System.out::println);
        IntStream.range(0, edges.length).map/*mapToObj*/(i -> { // 使用IntStream辅助从流中取得数组下标
            System.out.println(i + "->" + edges[i]);
            return i;
        }).count();
    }

    /**
     * 边
     */
    private class Edge {
        int vertex;
        int weight;
        Edge next;

        public Edge(int vertex, int weight, Edge next) {
            this.vertex = vertex;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public String toString() {
            return vertex + (next == null ? "" : ":" + next) /*+ "(w" + this.weight + ")"*/;
        }
    }
}
