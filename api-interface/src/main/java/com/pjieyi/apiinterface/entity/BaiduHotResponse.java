package com.pjieyi.apiinterface.entity;

import lombok.Data;

@Data
public class BaiduHotResponse {

    private String code;
    private String msg;
    private BaiduHot[] data;
}
