package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 借阅管理
*/
@Data
@TableName("borrow")
public class Borrow {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID */
    private Integer userId;
    /** 借阅时间 */
    private String startTime;
    /** 归还时间 */
    private String endTime;
    /** 借阅状态 */
    private String status;
    /** 书籍ID */
    private Integer bookId;
    private String name;

    @TableField(exist = false)
    private String bookName;
    @TableField(exist = false)
    private String userName;

}