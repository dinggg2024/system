package com.example.mapper;

import com.example.entity.Borrow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 操作borrow相关数据接口
*/
public interface BorrowMapper extends BaseMapper<Borrow> {

    /**
      * 查询所有
    */
    List<Borrow> selectAll(Borrow borrow);

    /**
      * 根据ID查询
    */
    Borrow selectById(Integer id);

    /**
      * 删除
    */
    int deleteById(Integer id);

}