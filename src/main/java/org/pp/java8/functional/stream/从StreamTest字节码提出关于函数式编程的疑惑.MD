# javap查看字节码指令
    一般常用的是-v -l -c三个选项。
    javap -v 不仅会输出行号、本地变量表信息、反编译汇编代码，还会输出当前类用到的常量池等信息。
    javap -l 会输出行号和本地变量表信息。
    javap -c 会对当前class字节码进行反编译生成汇编代码。
PS D:\IdeaProjects\corejava\target\classes\org\pp\java8\functional\stream> javap -c .\StreamTest.class
```
Compiled from "StreamTest.java"
public class org.pp.java8.functional.stream.StreamTest {
  public org.pp.java8.functional.stream.StreamTest();
    Code:
       0: aload_0                           // 0x2a 将第1个引用类型本地变量推送至栈顶  哪个引用？
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V  父类初始化 谁的父类？会造成Object实例化？
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_3  // 0x06 将int类型3推送至栈顶
       1: anewarray     #2                  // class java/lang/Integer  0xbd 创建Integer数组并将引用值压入栈顶
       4: dup  // 0x59 复制栈顶数值并将复制值压入栈顶
       5: iconst_0
       6: iconst_1
       7: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      10: aastore  // 将栈顶引用类型数值存入指定数组的指定索引位置？？
      11: dup
      12: iconst_1
      13: iconst_2
      14: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      17: aastore
      18: dup
      19: iconst_2
      20: iconst_3
      21: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      24: aastore
      25: invokestatic  #4                  // InterfaceMethod java/util/stream/Stream.of:([Ljava/lang/Object;)Ljava/util/stream/Stream;
      28: invokedynamic #5,  0              // InvokeDynamic #0:test:()Ljava/util/function/Predicate;
      33: invokeinterface #6,  2            // InterfaceMethod java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
      38: invokestatic  #7                  // Method java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
      41: invokeinterface #8,  2            // InterfaceMethod java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
      46: pop
      47: return
}
```
    main
    0-24行  newarray   #号是什么意思？好像有序的增加？
    28 invokedynamic 谓词判断  0xba 调用动态链接方法  #5,  0是什么意思？

    修改StreamTest Stream.of(1, 2, 3)  ==>  Stream.of(1, 2, 3, 4, 5)  进行两次谓词判断
```
Compiled from "StreamTest.java"
    public class org.pp.java8.functional.stream.StreamTest {
      public org.pp.java8.functional.stream.StreamTest();
        Code:
           0: aload_0
           1: invokespecial #1                  // Method java/lang/Object."<init>":()V
           4: return
    
      public static void main(java.lang.String[]);
        Code:
           0: iconst_5
           1: anewarray     #2                  // class java/lang/Integer
           4: dup
           5: iconst_0
           6: iconst_1
           7: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
          10: aastore
          11: dup
          12: iconst_1
          13: iconst_2
          14: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
          17: aastore
          18: dup
          19: iconst_2
          20: iconst_3
          21: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
          24: aastore
          25: dup
          26: iconst_3
          27: iconst_4
          28: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
          31: aastore
          32: dup
          33: iconst_4
          34: iconst_5
          35: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
          38: aastore
          39: invokestatic  #4                  // InterfaceMethod java/util/stream/Stream.of:([Ljava/lang/Object;)Ljava/util/stream/Stream;
          42: invokedynamic #5,  0              // InvokeDynamic #0:test:()Ljava/util/function/Predicate;
          47: invokeinterface #6,  2            // InterfaceMethod java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
          52: invokestatic  #7                  // Method java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
          55: invokeinterface #8,  2            // InterfaceMethod java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
          60: pop
          61: return
    }
```
    从字节码看到没有执行两次谓词判断 没有生成更多的字节码？？
    代码前面的行号是啥 怎么生成的？？
    
    解答：字节码阅读的问题没有回答
    函数式问题解答：
        最牛解答（其中讨论区的链接读起来更易理解）：https://www.zhihu.com/question/36782552
                                                https://blog.csdn.net/pongba/article/details/1336028
        阅读理解；与图灵机抽象的倾向于硬件性不同，丘齐的lambda算子理论是从数学的角度进行抽象，不关心运算的机械过程而只关心运算
        的抽象性质，只用最简洁的几条公理便建立起了与图灵机完全等价的计算模型，其体现出来的数学抽象美开出了函数式编程语言这朵奇葩
        
        疑问：如何在图灵机上实现函数式，lambda演算的解释器？？

   
javap: jdk源码目录\langtools\src\share\classes\com\sun\tools\javap
```
JavapTask.java
private void handleOption(String name, Iterator<String> rest) throws BadArgs {
        for (Option o: recognizedOptions) {
            if (o.matches(name)) {
                if (o.hasArg) {
                    if (rest.hasNext())
                        o.process(this, name, rest.next());
                    else
                        throw new BadArgs("err.missing.arg", name).showUsage(true);
                } else
                    o.process(this, name, null);

                if (o.ignoreRest()) {
                    while (rest.hasNext())
                        rest.next();
                }
                return;
            }
        }

        if (fileManager.handleOption(name, rest))
            return;

        throw new BadArgs("err.unknown.option", name).showUsage(true);
    }

    javap -c命令
    new Option(false, "-c") {
        void process(JavapTask task, String opt, String arg) {
            task.options.showDisassembled = true;
        }
    }

    if (fileManager == null)
                fileManager = getDefaultFileManager(diagnosticListener, log);
``` 