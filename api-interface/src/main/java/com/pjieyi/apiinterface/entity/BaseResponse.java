package com.pjieyi.apiinterface.entity;

import lombok.Data;

@Data
public class BaseResponse {

    private String code;
    private String msg;
    private Image data;
}
