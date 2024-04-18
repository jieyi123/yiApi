package com.pjieyi.yiapicommon.service;


import com.pjieyi.yiapicommon.model.entity.InterfaceInfo;

/**
* @author pjy17
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-01-25 19:25:05
*/
public interface InnerInterfaceInfoService {


    /**
     * 模拟接口是否存在
     * @param path 路径
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path);
}
