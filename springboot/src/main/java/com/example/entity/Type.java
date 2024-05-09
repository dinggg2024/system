package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 图书分类
*/
@Data
@TableName("type")
public class Type {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 类型名称 */
    private String name;

}