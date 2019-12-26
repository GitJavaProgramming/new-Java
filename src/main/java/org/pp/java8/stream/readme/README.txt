Stream: 使用函数式编程方式在集合类上进行复杂操作的工具api
public interface Stream<T> extends BaseStream<T, Stream<T>>
这个形式是不是感觉似曾相识：
public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable

参考UML图，哪些方法是非终态方法一目了然

惰性求值  返回Stream对象
及早求值  返回一个值或者NULL  终态--流已经被关闭

高阶函数：参数列表中含有函数式接口或返回值是函数接口的函数




