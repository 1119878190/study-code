package com.study.seata.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.seata.entity.TabStorageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-08-24 23:05:21
 */
@Mapper
public interface TabStorageDao extends BaseMapper<TabStorageEntity> {

    @Update("update tab_storage set total = total - #{currentUsed} , used = used + #{currentUsed} where product_id = #{productId}")
    int updateUsed(@Param("productId") long productId , @Param("currentUsed") long currentUsed);


}
