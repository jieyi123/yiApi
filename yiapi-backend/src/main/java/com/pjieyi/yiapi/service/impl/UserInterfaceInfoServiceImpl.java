package com.pjieyi.yiapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.model.entity.InterfaceInfo;
import com.pjieyi.yiapi.model.entity.UserInterfaceInfo;
import com.pjieyi.yiapi.service.UserInterfaceInfoService;
import com.pjieyi.yiapi.mapper.UserInterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author pjy17
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-02-03 16:23:36
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    /**
     * 校验
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //参数校验
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getUserId()<=0 || userInterfaceInfo.getInterfaceInfoId()<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }
        //剩余调用次数
        if (userInterfaceInfo.getLeftNum()<=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口调用次数已用完");
        }
    }

    /**
     * 用户调用接口次数统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return 调用结果
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //参数校验
        if (interfaceInfoId<=0 || userId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //用户可能会瞬间调用大量接口次数，为了避免统计出错，需要涉及到事务和锁的知识
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        updateWrapper.eq(UserInterfaceInfo::getUserId,userId);
        //setSql 方法用于设置要更新的 SQL 语句。这里通过 SQL 表达式实现了两个字段的更新操作：
        //leftNum=leftNum-1和totalNum=totalNum+1。意思是将leftNum字段减一，totalNum字段加一。
        updateWrapper.setSql("leftNum =leftNum-1,totalNum=totalNum+1");
        boolean result=this.update(updateWrapper);
        return result;
    }

}




