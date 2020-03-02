package org.pp.commons.google.guava;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import org.pp.commons.testbean.Person;
import org.pp.commons.testbean.User;

import java.util.*;

/**
 * 参考
 * https://ifeve.com/google-guava/
 */
public class TestGuava {

    @Test
    public void testBasis() {
//        Preconditions.checkArgument(1>1, "1>1");
        Objects.hashCode(1);
        Objects.toString(1);
        // comparator
        Ordering.natural();
        Ordering.usingToString();
        // optional
        Optional.absent();
    }

    @Test
    public void testCollection() {
        List list = Lists.newArrayList(1, "2", 3.0);
        list = Lists.newArrayListWithCapacity(5);

        Integer[] arr = FluentIterable.of(1, 5, 3).filter(i -> i > 2).toArray(Integer.class/*int.class*/);
        System.out.println(Arrays.toString(arr));

        list = Ints.asList(1, 2, 3, 9, 8, 7);
        list = Lists.partition(list, 5);
        /*list = */
        Lists.reverse(list); // 不改变源list
//        ImmutableList.toImmutableList();
        System.out.println(list);

        Map<String, Integer> map = ImmutableMap.of("1:1:3", 12, "2:1:2", 33, "2:2:1", 33);
        SetMultimap multimap = Multimaps.forMap(map);
        Multimap inverse = Multimaps.invertFrom(multimap, HashMultimap.create());
        System.out.println(inverse);

        User u1 = new User("u1");
        Person p1 = new Person("p1", 1, u1);
        User u2 = new User("u2");
        Person p2 = new Person("p2", 2, u2);
        User u3 = new User("u3");
        Person p3 = new Person("p3", 3, u3);
        User u4 = new User("u4");
        Person p4 = new Person("p4", 4, u4);
        List<Person> personList = Lists.newArrayList(p1, p2, p3, p4);
        ImmutableList<Person> people = ImmutableList.copyOf(personList);
        List<Person> unmodifiableList = Collections.unmodifiableList(personList);
        personList.get(0).getUser().setPassword("new Password");
        System.out.println(personList.get(0).getUser().getPassword());
        System.out.println(people.get(0).getUser().getPassword());
        System.out.println(unmodifiableList.get(0).getUser().getPassword());
    }
}
