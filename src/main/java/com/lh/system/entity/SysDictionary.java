package com.lh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 功能描述：
 *  <p>版权所有：</p>
 *  未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: LionHerding
 * @Author xieyc
 * @Date 2019-09-20
 * @Version: 1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDictionary implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "sys_dictionary_id", type = IdType.UUID)
    private String sysDictionaryId;
    private String parentId;
    private String key;
    private String value;
    private Integer sort;
    /**
     * 创建人
     */
    private String createId;
    private LocalDateTime createDate;
    /**
     * 修改人
     */
    private String updateId;
    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


    /////////////////////////////// 非表字段 ///////////////////////////////

}