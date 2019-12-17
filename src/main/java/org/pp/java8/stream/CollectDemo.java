package org.pp.java8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectDemo {

    public static void main(String[] args) {
        // 初始化list
        List<NamePhoneEmail> namePhoneEmailList = new ArrayList<>();
        namePhoneEmailList.add(new NamePhoneEmail("aa1", "ab1", "ac1"));
        namePhoneEmailList.add(new NamePhoneEmail("aa2", "ab2", "ac2"));
        namePhoneEmailList.add(new NamePhoneEmail("aa3", "ab3", "ac3"));
        namePhoneEmailList.add(new NamePhoneEmail("aa4", "ab4", "ac4"));
        namePhoneEmailList.add(new NamePhoneEmail("aa4", "ab4", "ac4"));
        String b = "";
        String strName = new String("aa4" + b);
        namePhoneEmailList.add(new NamePhoneEmail(strName, "ab4", "ac4"));

        /**
         * 流收集操作 toList toSet等
         */
        // 用map将namePhoneEmailList.stream()产生的流映射到另一个流
        Stream<NamePhone> namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        Collection<NamePhone> namePhoneCollection = namePhoneStream.collect(Collectors.toList()); // 流收集操作 终端流，会关闭流
        System.out.println("toList=" + namePhoneCollection);

        namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        namePhoneCollection = namePhoneStream.collect(Collectors.toSet()); // 比较equals和hashCode
        System.out.println("toSet=" + namePhoneCollection);

        namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        namePhoneCollection = namePhoneStream.collect(
                LinkedList::new,
                (list, element) -> {
//                    if (!list.contains(element)) {
//                        list.add(element);
//                    }
                    list.add(element);
                }, // 操作并将一个部分结果添加到结果中
                (listA, listB) -> {
                    listA.addAll(listB); // 合并两个部分结果
//                    listA.retainAll(listB);  // 前后两个结果 没有交集
                }
        );
        System.out.println("自定义收集：" + namePhoneCollection);

        // 流迭代操作  Iterator
        namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        Iterator<NamePhone> iter = namePhoneStream.iterator();
        while (iter.hasNext()) {
            NamePhone namePhone = iter.next();
            if ("aa4".equals(namePhone.name)) {
//                iter.remove();// 集合不可变 unmodifiable，Exception in thread "main" java.lang.UnsupportedOperationException: remove
            }
            System.out.println("namePhone=" + namePhone);
        }

        System.out.println("=============================spliterator.tryAdvance=============================");
        // 流迭代操作  Spliterator
        namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        Spliterator<NamePhone> spliterator = namePhoneStream.spliterator();
        while (spliterator.tryAdvance((n) -> {
            System.out.println(n)/*在迭代器中下一个元素执行的操作*/;
            spliterator.forEachRemaining((x) -> System.out.println(x + "1234")); // 流中剩余元素执行操作
        })) ;
        spliterator.forEachRemaining((x) -> System.out.println(x + "4321"));

        // 拆分迭代器 将被迭代的元素划分成两部分 trySplit
        namePhoneStream = namePhoneEmailList.stream().map((a) -> new NamePhone(a.name, a.phone));
        Spliterator<NamePhone> spliterator1 = namePhoneStream.spliterator();
//        System.out.println(spliterator == spliterator1); // Spliterator<NamePhone> spliterator 是单例吗？？？
//        spliterator1.forEachRemaining((x) -> System.out.println(x + "abc"));
        Spliterator<NamePhone> spliterator2 = spliterator1.trySplit(); // 分割迭代器
        if(spliterator2 != null) { // breakpoint 不能拆分？？ 怎么判断可以拆分 参考javadoc？？？
            spliterator2.forEachRemaining((x) -> System.out.println(x + "kfc"));
        }

    }
}
