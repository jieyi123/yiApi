package com.pjieyi.yiapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口管理
 * @author pjieyi
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}