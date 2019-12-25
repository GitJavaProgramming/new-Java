Iterable  迭代器接口
实现这个接口可以迭代遍历

    AbstractCollection 四大抽象子类
        AbstractList
        AbstractQueue
        AbstractSet
        AbstractDeque

    List接口在AbstractList抽象类实现Iterator，可用于公共的toString---AbstractCollection
    Set接口 内部使用Map接口实现
        哈希表实现
        平衡二叉树实现
    Map
        HashMap Map.Entry Node数组
        TreeMap 二叉树

序列化接口 包下面所有类基本都实现了Serializable接口，可以序列化
    实现 Serializable 接口允许对象序列化，以保存和恢复对象的全部状态，并且允许类在写入流时的状态和从流读取时的状态之间变化。
    它自动遍历对象之间的引用，保存和恢复全部图形。

    实现 Externalizable 接口允许对象假定可以完全控制对象的序列化形式的内容和格式。
    调用 Externalizable 接口的方法（writeExternal 和 readExternal）来保存和恢复对象状态。
    当这两种方法被某个类实现时，它们可以使用 ObjectOutput 和 ObjectInput 的所有方法读写其本身的状态。
    对象负责处理出现的任何版本控制。


