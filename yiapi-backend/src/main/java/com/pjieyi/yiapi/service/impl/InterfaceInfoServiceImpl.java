package com.pjieyi.yiapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.model.entity.InterfaceInfo;
import com.pjieyi.yiapi.model.enums.PostGenderEnum;
import com.pjieyi.yiapi.model.enums.PostReviewStatusEnum;
import com.pjieyi.yiapi.service.InterfaceInfoService;
import com.pjieyi.yiapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author pjy17
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-01-25 19:25:05
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    /**
     * 校验
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String method = interfaceInfo.getMethod();

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, description, url, requestHeader, responseHeader,method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(description) && description.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }
}




