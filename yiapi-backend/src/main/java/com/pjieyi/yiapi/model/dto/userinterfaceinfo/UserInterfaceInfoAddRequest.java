package com.pjieyi.yiapi.model.dto.userinterfaceinfo;

import lombok.Data;
import java.io.Serializable;

/**
 * 创建用户接口调用
 * @TableName user_interface_info
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {



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

    private static final long serialVersionUID = 1L;
}