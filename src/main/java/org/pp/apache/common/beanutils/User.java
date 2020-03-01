package org.pp.apache.common.beanutils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class User extends Person {
    private String password;

    public User(String password) {
        this.password = password;
    }
}
