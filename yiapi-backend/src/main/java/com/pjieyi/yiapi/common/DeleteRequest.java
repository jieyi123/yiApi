package com.pjieyi.yiapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author pjieyi
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}