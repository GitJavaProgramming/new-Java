Iterable  迭代器接口
实现这个接口可以迭代遍历

    AbstractCollection 四大抽象子类
        AbstractList
        AbstractQueue
        AbstractSet
        AbstractDeque

    List接口在AbstractList抽象类实现Iterator，可用于公共的toString---AbstractCollection
    Set接口 内部使用Map接口实现

    Map
        HashMap Map.Entry Node数组
        TreeMap 二叉树
