package com.pjieyi.yiapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.mapper.UserMapper;
import com.pjieyi.yiapicommon.model.entity.User;
import com.pjieyi.yiapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author pjieyi
 * @description
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 是否分配给用户密钥
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isEmpty(accessKey)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey,accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
