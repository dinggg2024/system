package com.example.mapper;

import com.example.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 操作book相关数据接口
*/
public interface BookMapper extends BaseMapper<Book> {

    /**
      * 查询所有
    */
    List<Book> selectAll(Book book);

    /**
      * 根据ID查询
    */
    Book selectById(Integer id);

    /**
      * 删除
    */
    int deleteById(Integer id);

}