package com.pjieyi.yiapi.model.vo;

import com.pjieyi.yiapi.model.entity.Post;
import com.pjieyi.yiapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子视图
 *
 * @author pjieyi
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 接口调用总数
     */
    private Integer totalNum;

    /**
     * 剩余次数
     */
    private Integer leftNum;

    private static final long serialVersionUID = 1L;
}