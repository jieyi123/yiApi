package com.pjieyi.yiapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.mapper.InterfaceInfoMapper;
import com.pjieyi.yiapicommon.model.entity.InterfaceInfo;
import com.pjieyi.yiapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author pjieyi
 * @description
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 模拟接口是否存在
     * @param path 路径
     * @return
     */

    @Override
    public InterfaceInfo getInterfaceInfo(String path) {
        if (StringUtils.isAnyBlank(path)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceInfo::getUrl,path);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
