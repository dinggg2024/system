package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 图书信息
*/
@Data
@TableName("book")
public class Book {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 图书封面 */
    private String img;
    /** 图书名称 */
    private String name;
    /** 图书价格 */
    private Double price;
    /** ISBN号 */
    private String isbn;
    /** 图书作者 */
    private String author;
    /** 出版社 */
    private String publisher;
    /** 分类ID */
    private Integer typeId;
    /** 剩余数量 */
    private Integer num;

    @TableField(exist = false)
    private String typeName;

}