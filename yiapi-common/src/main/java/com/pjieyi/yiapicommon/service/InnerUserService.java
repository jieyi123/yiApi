package com.pjieyi.yiapicommon.service;


import com.pjieyi.yiapicommon.model.entity.User;


/**
 * 用户服务
 *
 * @author pjieyi
 */
public interface InnerUserService {

    /**
     * 是否分配给用户密钥
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
