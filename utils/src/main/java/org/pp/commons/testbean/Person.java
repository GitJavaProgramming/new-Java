package org.pp.commons.testbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {
    private String name;
    private int age;
    private User user;
}
