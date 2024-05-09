package com.example.mapper;

import com.example.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 操作type相关数据接口
*/
public interface TypeMapper extends BaseMapper<Type> {

    /**
      * 查询所有
    */
    List<Type> selectAll(Type type);

    /**
      * 根据ID查询
    */
    Type selectById(Integer id);

    /**
      * 删除
    */
    int deleteById(Integer id);

}