package org.pp.java8.datastruct.test;

import org.pp.java8.datastruct.nonlinear.BST;
import org.pp.java8.datastruct.nonlinear.BTNode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BSTTest {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        ops(bst);
//        bst.writeTree("");
    }

    public static void ops(BST<Integer> bst) {
        bst.insert(1); // root
        bst.insert(3/* + "3"*/);
        bst.insert(2);
        bst.insert(6);
        bst.insert(5);
        bst.insert(4);
        bst.insert(8);
        bst.insert(7);
        System.out.println(bst.getRoot());
        System.out.println(bst.size());
        BST.print(bst.getRoot(), 0); // 极端不平衡，查找性能低下，左子树为空
        // 打印结点信息
        BTNode<Integer> node = bst.getNode(3);
        System.out.println("node:" + node);
        System.out.println("left:" + node.getLeft().getItem());
        System.out.println("right:" + node.getRight().getItem());
        bst.delete(6); // 删除结点，删除子树顶端元素，取子树最左叶子结点
        System.out.println();
        BST.print(bst.getRoot(), 0);
    }

    // 二叉树的平衡化

}
