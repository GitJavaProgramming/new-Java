package org.pp.java8.functional.stream.exercise.java8functional.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专辑中的一首曲目
 */
@Data
@NoArgsConstructor
public class Track {
    /* 曲目名 */
    private String name;
    /* 曲目长度 */
    private int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }
}
