Throwable类用来表示任何可以作为异常被抛出的类
    Error：表示编译时和系统错误
    Exception：Java类库、用户方法以及运行时故障都可能抛出Exception异常
异常情况是指阻止当前方法或作用域继续执行的问题，异常作为程序出错的标志，决不应该被忽略。（除非你故意为之！！）
抛出异常时，异常处理系统将会按照代码的书写顺序找出最近的处理程序，找到匹配的处理程序之后，它就认为异常将得到处理，然后就不再继续查找。
    public class RuntimeException extends Exception
    RuntimeException是在JVM运行期间能被抛出的异常超类，在方法或构造器中抛出unchecked exceptions不需要throws子句声明

Java语言规范（ch11-异常） 指出非受检查的异常包括RuntimeException、Error
为了利用编译时对异常处理器的检查，自定义异常类不应该定义为非受检查的异常，即不是RuntimeException、Error子类。



