package org.pp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreJavaTest {

    String str = "good";
    char[] ch = {'a', 'b', 'c'};

    public static void main(String args[]) {
        test0();
        test1();
    }

    private void changePersonName(Person p/*拷贝引用到栈*/) {
        p.setName("snow");
        p = new Person();
        p.setName("tomas");
    }

    private void changePersonList(List<Person> pList/*拷贝引用到栈*/) {
        pList.add(new Person());
        pList = new ArrayList<>();
        pList.add(new Person());
        pList.add(new Person());
    }

    public void change(String str, char[] ch) {
        str = "test ok";
        ch[0] = 'g';
        System.out.println(str);
        System.out.println(ch);
    }

    static class Person {
        int age;
        String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void test0() {
        String s = "job";
        String s1 = "j" + "o" + "b";
        String s2 = "n-job".substring(2);
        System.out.println(s == s1);
        System.out.println(s == s2);

//        new ConcurrentHashMap<>(1000000000);
        CoreJavaTest ex = new CoreJavaTest();
//        ex.change(ex.str, ex.ch);
//        System.out.print(ex.str + " and ");
//        System.out.print(ex.ch);

        Person p = new Person();
        p.setName("john");
        ex.changePersonName(p);
        System.out.println(p.getName());

        List<Person> pList = new ArrayList<>();
        ex.changePersonList(pList);
        System.out.println(pList);
    }

    public static void test1() {
        int i = 4;
        double d = 4.0;
        String s = "HackerRank ";

        Scanner scan = new Scanner(System.in);
        System.out.println("输入3行，每行分别为integer, double, and String");
        /* Declare second integer, double, and String variables. */
        String si = scan.nextLine();
        Integer isi = Integer.parseInt(si);
        String sd = scan.nextLine();
        double dsd = Double.parseDouble(sd);
        String ss = scan.nextLine();
        /* Read and save an integer, double, and String to your variables.*/
        // Note: If you have trouble reading the entire String, please go back and review the Tutorial closely.
        int i2 = i + isi;
        double d2 = d + dsd;
        String s2 = s + ss;
        /* Print the sum of both integer variables on a new line. */
        System.out.println("" + i2);
        /* Print the sum of the double variables on a new line. */
        System.out.println("" + d2);
        /* Concatenate and print the String variables on a new line;
        	the 's' variable above should be printed first. */
        System.out.println(s2);
        scan.close();
    }

}
