package com.pjieyi.yiapi.service;

import com.pjieyi.yiapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author pjy17
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-01-25 19:25:05
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
