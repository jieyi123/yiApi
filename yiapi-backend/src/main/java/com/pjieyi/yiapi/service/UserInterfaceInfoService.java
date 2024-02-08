package com.pjieyi.yiapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pjieyi.yiapi.common.BaseResponse;
import com.pjieyi.yiapi.model.vo.InterfaceInfoVO;
import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author pjy17
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-02-03 16:23:36
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

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
     * 统计每个接口总调用次数
     * @return
     */
     List<InterfaceInfoVO> listTopInvokeInterfaceInfo();

}
