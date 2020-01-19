package org.pp.java8.algorithm.datastruct.nonlinear;

/**
 * 二叉树节点类
 * @param <E> 节点元素类型
 */
public class BTNode<E> {
    E item;
    BTNode<E> left;
    BTNode<E> right;

    public BTNode(E item) {
        this.item = item;
    }

    public BTNode(E item, BTNode<E> left, BTNode<E> right) {
        this.item = item;
        this.left = left;
        this.right = right;
    }

    public BTNode() {

    }

    @Override
    public String toString() {
        return item.toString();
    }

    public E getItem() {
        return item;
    }

    public BTNode<E> getLeft() {
        return left;
    }

    public BTNode<E> getRight() {
        return right;
    }
}
