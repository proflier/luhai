package com.cbmie.common.utils;

/**
 * 封装流程作废原因
 */
public enum AbolishReason {
    INITABOLISH("initAbolish"), // 申请人废除
    CALLBACK("callBack"), // 撤回
    GIVEUP("giveUp"),//申请人直接在第一次提交时关闭
    SUPER("super");

    private String reason;

    private AbolishReason(String reason) {
        this.reason = reason;
    }

    public String getValue() {
        return reason;
    }
}