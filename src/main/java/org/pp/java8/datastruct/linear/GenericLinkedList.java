package org.pp.java8.datastruct.linear;

import java.util.NoSuchElementException;

/**
 * 双向链表
 */
public class GenericLinkedList<E> {

    /**
     * 头结点
     */
    private Node<E> first;
    /**
     * 尾结点
     */
    private Node<E> last;
    /**
     * 链表长度
     */
    private int size = 0;

    public GenericLinkedList() {
    }

    /**
     * 插入元素 当作头节点
     *
     * @param e 插入元素
     * @return 成功返回true
     */
    public boolean addFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        return true;
    }

    /**
     * 链表尾部插入
     */
    public boolean addLast(E e) {
        final Node<E> ln = last;
        final Node<E> newNode = new Node(ln, e, null);
        last = newNode;
        if (ln == null) {
            first = newNode;
        } else {
            ln.next = newNode;
        }
        size++;
        return true;
    }

    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.elem;
    }

    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.elem;
    }

    public E removeFirst() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        unlinkFirst(f);
        return f.elem;
    }

    private E unlink(Node<E> x) {
        final E element = x.elem;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.elem = null;
        size--;
        return element;
    }

    private E unlinkFirst(Node<E> f) {
        final E elem = f.elem;
        final Node<E> next = f.next;
        f.elem = null;
        f.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return elem;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "GenericLinkedList{" +
                "first=" + first +
                ", last=" + last +
                ", size=" + size +
                '}';
    }

    /**
     * 双向链表，维护前后节点引用
     *
     * @param <E>
     */
    private static final class Node<E> {
        Node<E> prev;
        E elem;
        Node<E> next;

        public Node(Node<E> prev, E elem, Node<E> next) {
            this.prev = prev;
            this.elem = elem;
            this.next = next;
        }
    }

}
