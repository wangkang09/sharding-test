package com.example.shardingjdbctest.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * @author wangkang
 * @date 2021-09-10
 * @since -
 */
@Data
public class UserVo1 {

    private Integer age;
    private List<Aihao> aihaos;

    @Data
    public static class Aihao {
        private String name;
        private Integer type;
        private List<Aihao1> aihaos;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Aihao1 {
        private String name;
        private Integer type;
    }
}
