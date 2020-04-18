package org.pp.java8.algorithm.datastruct.nonlinear;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractGraph implements Graph {
    /**
     * 顶点数
     */
    protected int numVertex;
    /**
     * 边
     */
    protected int numEdge;

    @Override
    public int vertices() {
        return numVertex;
    }

    @Override
    public int edges() {
        return numEdge;
    }

    protected void rangeCheck(int v1, int v2, int wgt) {
        if (v1 > numVertex || v2 > numVertex) {
//            System.out.println("点不在矩阵中.");
            throw new IllegalStateException("点不在矩阵中.");
        }
        if (wgt <= 0) {
//            System.out.println("设置边时权重必须设置为大于0.");
            throw new IllegalStateException("设置边时权重必须设置为大于0.");
        }
    }

    /**************************************************************************************/
    @Override
    public void dfs(Graph g) {
        // 初始化图
        boolean[] visited = initGraph(g);
        // 深度优先遍历图
        int vertices = g.vertices();
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfs(g, i, visited);
            }
        }
    }

    private void dfs(Graph g, int v, boolean[] visited) {
        preVisit(g, v);
        visited[v] = true; // 设置已访问
        for (int w = g.first(v); w < g.vertices(); w = g.next(v, w)) { // 深度遍历
            if (!visited[w]) { // 可达路径上，没访问过的就访问
                dfs(g, w, visited);
            }
        }
        postVisit(g, v);
    }

    /**************************************************************************************/
    @Override
    public void bfs(Graph g) {
        boolean[] visited = initGraph(g);
        // 广度优先遍历图
        int vertices = g.vertices();
        Queue<Integer> queue = new LinkedBlockingQueue(); // 使用队列保存邻接点
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                bfs(g, i, visited, queue);
            }
        }
    }

    private void bfs(Graph g, int start, boolean[] visited, Queue<Integer> queue) {
        queue.offer(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            preVisit(g, v);
            for (int w = g.first(v); w < g.vertices(); w = g.next(v, w)) {
                if (!visited[w]) {
                    visited[w] = true;
                    queue.offer(w);
                }
            }
            postVisit(g, v);
        }
    }

    /*******************************************业务方法*******************************************/
    /**
     * 初始化图，未遍历初始状态
     */
    protected boolean[] initGraph(Graph g) {
        int vertices = g.vertices();
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }
        return visited;
    }

    protected void preVisit(Graph g, int v) {
        System.out.println("preVisit vertex : " + v);
    }

    protected void postVisit(Graph g, int v) {
        System.out.println("postVisit vertex : " + v);
    }
}
