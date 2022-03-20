package com.sbnh.healer_head_small_program.bean.po;

import com.sbnh.healer_head_small_program.constants.enums.StatusEnum;
import com.sbnh.healer_head_small_program.utils.IdGen;
import lombok.Data;

@Data
public class BasePo {
    /**
     * id
     */
    private String id = IdGen.uuid();
    /**
     *创建时间
     */
    private Long createTime =System.currentTimeMillis();
    /**
     *状态
     */
    private Integer status= StatusEnum.ok();
}
