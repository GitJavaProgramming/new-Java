package org.pp.java8.stream.exercise.java8functional.model;

import lombok.Data;

import java.util.List;

/**
 * 专辑，由若干曲目组成
 */
@Data
public class Album {

    /* 专辑名 */
    private String name;
    /* 专辑中的曲目列表 */
    private List<Track> tracks;
    /* 参与创作本专辑的艺术家列表 */
    private List<Artist> musicians;
}
