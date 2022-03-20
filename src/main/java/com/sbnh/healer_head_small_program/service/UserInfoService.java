package com.sbnh.healer_head_small_program.service;

import com.sbnh.healer_head_small_program.bean.vo.WeiXinLoginVo;

public interface UserInfoService {
    /**
     * @param code code码
     * @return token
     */
    WeiXinLoginVo weiXinLogin(String code);
}
