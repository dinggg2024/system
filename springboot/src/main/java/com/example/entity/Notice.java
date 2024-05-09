package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 系统公告表
*/
@Data
@TableName("notice")
public class Notice {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 公告标题 */
    private String name;
    /** 公告内容 */
    private String content;
    /** 发布时间 */
    private String time;

}