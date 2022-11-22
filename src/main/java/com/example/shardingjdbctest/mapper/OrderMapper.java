package com.example.shardingjdbctest.mapper;

import com.example.shardingjdbctest.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into rs_order(member_id,billno,email) values(#{member_id},#{billno},#{email})")
    void insert(@Param("member_id") Long memberId, @Param("billno") String billNo, @Param("email") String email);

    @Select("select * from rs_order where member_id = #{member_id} and billno = #{billno}")
    List<Order> select(@Param("member_id") Long memberId, @Param("billno") String billNo);

    @Insert("insert into rs_order(member_id1,billno,email) values(#{member_id},#{billno},#{email})")
    void insert1(@Param("member_id") Long memberId, @Param("billno") String billNo, @Param("email") String email);

}
