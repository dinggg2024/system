package com.example.mapper;

import com.example.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 操作notice相关数据接口
*/
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
      * 查询所有
    */
    List<Notice> selectAll(Notice notice);

    /**
      * 根据ID查询
    */
    Notice selectById(Integer id);

    /**
      * 删除
    */
    int deleteById(Integer id);

}