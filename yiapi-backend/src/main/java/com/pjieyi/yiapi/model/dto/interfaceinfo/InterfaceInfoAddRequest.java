package com.pjieyi.yiapi.model.dto.interfaceinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 创建接口
 *
 * @TableName interface_info
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {


    /**
     * 名称
     */
    @ApiModelProperty("接口名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("接口描述")
    private String description;

    /**
     * 接口地址
     */
    @ApiModelProperty("接口地址")
    private String url;

    /**
     * 请求头
     */
    @ApiModelProperty("请求头")
    private String requestHeader;

    /**
     * 响应头
     */
    @ApiModelProperty("响应头")
    private String responseHeader;

    /**
     * 请求参数
     */
    private String requestParams;


    /**
     * 请求类型
     */
    @ApiModelProperty("请求类型")
    private String method;

    /**
     * 参数示例
     */
    @ApiModelProperty("参数示例")
    private String parameterExample;

    private static final long serialVersionUID = 1L;
}