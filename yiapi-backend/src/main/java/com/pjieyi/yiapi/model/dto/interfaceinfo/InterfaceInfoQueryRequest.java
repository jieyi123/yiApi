package com.pjieyi.yiapi.model.dto.interfaceinfo;

import com.pjieyi.yiapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 查询请求
 *
 * @author pjieyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;


    /**
     *查询  开始时间
     */
    private String startTime;

    /**
     *查询  结束时间
     */
    private String endTime;

    private static final long serialVersionUID = 1L;
}