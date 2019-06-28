package com.lh.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：Model - 字典管理    SYS_Dictionary
 *
 * <p>
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: LionHerding
 * @Author: 牧狮&&紫色年华
 * @Datetime: 2019-05-10 16:40
 */
@Data
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;			// UUID

    private String parentId;	// 父亲节点的标识	(UUID)

    private String key;			// 关键字

    private String value;		// 属性值

    private int sort;			// 排序

    private String createId;	// 创建人标识

    private String createName;	// 创建人姓名

    private Date createDate;	// 创建时间
}