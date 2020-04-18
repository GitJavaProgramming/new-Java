package org.pp.java8.algorithm.datastruct.nonlinear;

/**
 * 图：顶点集合和边的集合的二元组，要表示一个图G=(V, E),有两种标准的方法：邻接表和邻接矩阵
 */
public interface Graph {
    /**
     * 图中顶点数
     */
    int vertices();

    /**
     * 图中的边数
     */
    int edges();

    /**
     * 返回与顶点关联的第一条边
     */
    int first(int v);

    /**
     * 返回下一条与此顶点关联的边
     */
    int next(int v1, int v2);

    /**
     * 设置一条边的权，权重>0
     */
    void setEdge(int v1, int v2, int wgt);
//    void setEdge(int v1, int v2);

    /**
     * 获得边的权重,使用起点和终点表示一条边，如果边不存在则权重为0
     */
    int weight(int v1, int v2);

    /**
     * 深度优先遍历,沿着某一分支搜索至末端，然后回溯，沿着另一分支搜索
     */
    void dfs(Graph g);

    /**
     * 广度优先遍历，在进一步深入访问其他顶点前，检查所有的邻接点
     */
    void bfs(Graph g);
}
