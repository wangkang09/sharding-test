package com.example.shardingjdbctest.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    @Insert("insert into rs_order(member_id,billno,email) values(#{member_id},#{billno},#{email})")
    void insert(@Param("member_id") Long memberId, @Param("billno") String billNo, @Param("email") String email);
}
