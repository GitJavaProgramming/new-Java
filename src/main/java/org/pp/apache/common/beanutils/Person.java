package org.pp.apache.common.beanutils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Person {
    private String name;
    private int age;
    private User user;
}
