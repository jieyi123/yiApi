package com.pjieyi.yiapicommon.service;

import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;

/**
* @author pjy17
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 校验
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /**
     * 用户调用接口次数统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return 调用结果
     */
    boolean invokeCount(long interfaceInfoId,long userId);

    /**
     * 验证是否还有剩余调用接口次数
     * @param interfaceInfoId
     * @param userId
     */
    void checkCount(long interfaceInfoId,long userId);


}
