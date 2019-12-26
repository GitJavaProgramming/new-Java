package org.pp.java8.datastruct.nonlinear;

import java.io.*;

/**
 * 二叉查找树实现
 * 设二叉查找树的大小为n，如果二叉查找树的高度为d，则比较次数最多为d。如果二叉查找树始终是平衡的，它的高度为floor(log\u2082n)+1
 * 因此最大比较次数为floor(log2n)+1,故最佳情况下时间复杂度为O(log2n)。如果二叉查找树不平衡，它的高度最多为n（退化为链表），
 * 因此最大比较次数为n，故最差情况下时间代价为O(n)
 * <p>
 * 使二叉树保持平衡，将会获得比较号的平均时间复杂度，最常见的如AVL数、红黑树等，通常在进行一次插入或删除后立即调整树结构以保持平衡
 * 另外一种方法是，在需要的时候进行一次平衡化重构：
 * 1、二叉树转化为主链，同时保持中序遍历顺序不变
 * 2、将主链转化为平衡二叉树，同时保持中序遍历顺序不变
 *
 * @param <E> 元素类型，这里限定其是Comparable子类，或者使用Comparator<T>函数式接口由外部比较器比较
 */
public class BST<E extends Comparable<E>> {
    /**
     * 树的根结点
     */
    private BTNode<E> root;
    private int count;

    public BST() {
        clear();
    }

    public void clear() {
        root = null;
        count = 0;
    }

    public void insert(E e) {
        root = insert(root, e);
//        print
    }

    private BTNode<E> insert(BTNode<E> ref, E e) {
        if (ref == null) {
            count++;
            return new BTNode(e);
        } else {
            int comp = e.compareTo(ref.item);
            if (comp < 0) {
                ref.left = insert(ref.left, e);
            } else { // >= 0
                ref.right = insert(ref.right, e);
            }
        }
        return ref;
    }

    public void delete(E e) {
        root = delete(root, e);
    }

    private BTNode<E> delete(BTNode<E> ref, E e) {
        if (ref != null) {
            int comp = e.compareTo(ref.item);
            if (comp == 0) {
                count--;
                return deleteTopmost(ref); // 删除找到元素的父节点元素
            } else if (comp < 0) {
                ref.left = delete(ref.left, e);
            } else {
                ref.right = delete(ref.right, e);
            }
        }
        return ref;
    }

    private BTNode<E> deleteTopmost(BTNode<E> ref) {
        if (ref.left == null) {
            return ref.right;
        } else if (ref.right == null) {
            return ref.left;
        } else {
            ref.item = getLeftmost(ref.right);
            ref.right = deleteLeftmost(ref.right);
            return ref;
        }
    }

    private BTNode<E> deleteLeftmost(BTNode<E> ref) {
        if (ref.left == null) {
            return ref.right;
        } else {
            ref.left = deleteLeftmost(ref.left);
            return ref;
        }
    }

    private E getLeftmost(BTNode<E> ref) {
        if (ref.left == null) {
            return ref.item;
        } else {
            return getLeftmost(ref.left);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public boolean contains(E e) {
        return search(root, e) != null;
    }

    public BTNode<E> getNode(E e) {
        return search(root, e);
    }

    /**
     * 查找元素是否在树中
     *
     * @param ref
     * @param e
     * @return
     */
    private BTNode<E> search(BTNode<E> ref, E e) {
        if (ref == null) {
            return null;
        } else {
            int comp = e.compareTo(ref.item);
            if (comp == 0) {
                return ref;
            } else if (comp < 0) {
                return search(ref.left, e);
            } else {
                return search(ref.right, e);
            }
        }
    }

    /**
     * 前序遍历，先访问根结点，再依次访问左右子树
     *
     * @param ref
     */
    public static void preOrder(BTNode/*<E>*/ ref) {
        if (ref == null) {
            return;
        }
        System.out.println(ref.item + " ");
        preOrder(ref.left);
        preOrder(ref.right);
    }

    public static void inOrder(BTNode/*<E>*/ ref) {
        if (ref == null) {
            return;
        }
        preOrder(ref.left);
        System.out.println(ref.item + " ");
        preOrder(ref.right);
    }

    public static void postOrder(BTNode/*<E>*/ ref) {
        if (ref == null) {
            return;
        }
        preOrder(ref.right);
        preOrder(ref.left);
        System.out.println(ref.item + " ");
    }

    public BTNode<E> getRoot() {
        return root;
    }

    public static void print(BTNode ref, int level) {
        if (ref == null) {
            return;
        } else {
            print(ref.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("____");
            }
            System.out.println(ref.item);
            print(ref.left, level + 1);
        }
    }

    /**
     * 将二叉树写入文件，
     * 第一种方法：前序遍历按顺序读取文件（字节）数据，调用插入算法，将数据插入空的二叉树
     * 第二种方法：将二叉树恢复为平衡二叉树（这也是二叉树平衡化的一种方法！！）
     * 将二叉树中的结点树n写入文件，中序遍历写入文件
     * 顺序读取数据类构建平衡二叉树
     *
     * @param fileName 要写入的文件名
     */
    public void writeTree(String fileName) throws IOException {
        FileOutputStream outFile = new FileOutputStream(fileName);
        ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
        writeTreePreOrder(root, outputStream);
        outputStream.close();
    }

    /**
     * 中序遍历将根结点为ref的子树写入文件
     *
     * @param ref
     * @param outputStream
     * @throws IOException
     */
    public void writeTreePreOrder(BTNode ref, ObjectOutputStream outputStream) throws IOException {
        if (ref == null) {
            return;
        }
        outputStream.writeObject(ref.getItem());
        writeTreePreOrder(ref.getLeft(), outputStream);
        writeTreePreOrder(ref.getRight(), outputStream);
    }

    /**
     * 从文件中还原二叉树
     *
     * @param fileName
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readTree(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        clear(); // 清空树
        readTreePreOrder(objectInputStream);
        objectInputStream.close();
    }

    public void readTreePreOrder(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        while (inputStream.available() <= 0) {
            E val = (E) inputStream.readObject();
            insert(val);
        }
    }

    public void writeTreeInOrder(String fileName) throws IOException {
        FileOutputStream outFile = new FileOutputStream(fileName);
        ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
        outputStream.writeInt(count); // 写入树的所有结点数量
        writeTreeInOrder(root, outputStream);
        outputStream.close();
    }

    /**
     * 中序遍历写入文件
     *
     * @param ref
     * @param outputStream
     * @throws IOException
     */
    public void writeTreeInOrder(BTNode ref, ObjectOutputStream outputStream) throws IOException {
        if (ref == null) {
            return;
        }
        writeTreeInOrder(ref.getLeft(), outputStream);
        outputStream.writeObject(ref.getItem());
        writeTreeInOrder(ref.getRight(), outputStream);
    }

    public void readAndBalance(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        clear(); // 清空树
        int n = objectInputStream.readInt();
        root = readTreeInOrder(objectInputStream, n);
        objectInputStream.close();
    }

    private BTNode<E> readTreeInOrder(ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        if (n == 0) {
            return null;
        }
        BTNode<E> ref = new BTNode();
        ref.left = readTreeInOrder(objectInputStream, n / 2);
        ref.item = (E) objectInputStream.readObject();
        ref.right = readTreeInOrder(objectInputStream, (n - 1) / 2);
        return ref;
    }
}
