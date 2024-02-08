package com.pjieyi.yiapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author pjy17
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-02-03 16:23:36
* @Entity com.pjieyi.yiapi.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 统计接口调用次数
     * @param limit 前几名
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




