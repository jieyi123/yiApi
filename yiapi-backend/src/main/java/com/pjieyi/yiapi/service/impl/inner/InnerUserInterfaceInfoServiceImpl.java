package com.pjieyi.yiapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.mapper.UserInterfaceInfoMapper;
import com.pjieyi.yiapi.service.UserInterfaceInfoService;
import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;
import com.pjieyi.yiapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author pjieyi
 * @description
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    /**
     * 校验
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

    }

    /**
     * 用户调用接口次数统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return 调用结果
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
       return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }

    /**
     * 验证当前用户对当前接口是否还有调用次数
     * @param interfaceInfoId
     * @param userId
     */
    @Override
    public void checkCount(long interfaceInfoId, long userId) {

        if (interfaceInfoId<=0 || userId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //首先查询是否有用户调用接口的记录
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        queryWrapper.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo one = userInterfaceInfoService.getOne(queryWrapper);
        if (one!=null){
            Integer leftNum = one.getLeftNum();
            if (leftNum<=0){
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
    }
}
