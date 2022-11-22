package com.example.shardingjdbctest.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wangkang
 * @date 2021-09-10
 * @since -
 */
@Data
public class UserVo2 {

    private Integer age;
    private List<Aihao> aihaos;

    @Data
    public static class Aihao {
        private String name;
        private Integer type;
        private List<String> count;
    }
}
