package com.pjieyi.yiapi.model.dto.userinterfaceinfo;

import lombok.Data;
import java.io.Serializable;

/**
 * 更新请求
 * @TableName user_interface_info
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;


    /**
     * 接口 id
     */
    private Long interfaceInfoId;


    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 状态 0-正常，1-禁用 默认0
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}