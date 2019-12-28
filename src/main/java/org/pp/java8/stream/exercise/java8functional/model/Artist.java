package org.pp.java8.stream.exercise.java8functional.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创作音乐的个人或团队
 */
@Data
@NoArgsConstructor
public class Artist {
    /* 艺术家的名字 */
    private String name;
    /* 乐队成员，字段可为空 */
    private String members;
    /* 乐队来自哪里 */
    private String origin;

    public Artist(String name, String members, String origin) {
        this.name = name;
        this.members = members;
        this.origin = origin;
    }

    public boolean isFrom(String origin) {
        return this.origin.equals(origin);
    }
}
