package com.sbnh.healer_head_small_program.constants.enums;

/**
 * @Waring 所有存储数据 基本状态描述
 * @Description
 * @Author hua
 * @Date 2022-3-20
 */
public enum StatusEnum {
    //存储状态 正常
    OK(0),
    //存储状态 删除
    DEL(1),
    //存储状态 禁用； 社区动态如果是该状态则只有自己能查看，拒绝别人查看
    FORBIDDEN(2);


    private final int status;

    StatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static int ok() {
        return StatusEnum.OK.status;
    }

    public static int del() {
        return StatusEnum.DEL.status;
    }

    public static int forbidden() {
        return StatusEnum.FORBIDDEN.status;
    }

    public static StatusEnum getStatus(int statusCode) {

        for (StatusEnum status : StatusEnum.values()) {
            if (status.getStatus() == statusCode) {
                return status;
            }
        }
        return null;
    }

}
