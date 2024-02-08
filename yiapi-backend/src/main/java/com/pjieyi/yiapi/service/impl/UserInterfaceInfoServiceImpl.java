package com.pjieyi.yiapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjieyi.yiapi.common.ErrorCode;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.model.vo.InterfaceInfoVO;
import com.pjieyi.yiapi.service.InterfaceInfoService;
import com.pjieyi.yiapi.service.UserInterfaceInfoService;
import com.pjieyi.yiapi.mapper.UserInterfaceInfoMapper;
import com.pjieyi.yiapicommon.model.entity.InterfaceInfo;
import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author pjy17
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-02-03 16:23:36
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

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
        //首先查询是否有用户调用接口的记录
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        queryWrapper.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo one = this.getOne(queryWrapper);
        if (one==null){
            //说明当前用户没有对应接口的调用记录
            //为调用这个接口的用户配100次免费调用
            UserInterfaceInfo userInterface=new UserInterfaceInfo();
            userInterface.setUserId(userId);
            userInterface.setInterfaceInfoId(interfaceInfoId);
            userInterface.setTotalNum(0);
            userInterface.setLeftNum(100);
            //新增数据
            this.save(userInterface);
        }
        //当前用户有接口调用信息 直接计算
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

    /**
     * 统计每个接口总调用次数
     * @return
     */
    @Override
    public List<InterfaceInfoVO> listTopInvokeInterfaceInfo() {
        //统计接口调用次数前5
        List<UserInterfaceInfo> infos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(5);
        //将infos转为map
        //key为interfaceInfoId value 为当前接口总调用次数
        Map<Long, Integer> interfaceInfoMap = infos.stream()
                .collect(Collectors.toMap(UserInterfaceInfo::getInterfaceInfoId, UserInterfaceInfo::getTotalNum));
        LambdaQueryWrapper<InterfaceInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId,interfaceInfoMap.keySet());
        //接口信息详情
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(queryWrapper);
        List<InterfaceInfoVO> interfaceInfoVOS = interfaceInfos.stream().map(interfaceInfo -> {
            InterfaceInfoVO vo = new InterfaceInfoVO();
            // 将接口信息复制到接口信息VO对象中
            BeanUtils.copyProperties(interfaceInfo, vo);
            //填充totalNum
            vo.setTotalNum(interfaceInfoMap.get(interfaceInfo.getId()));
            return vo;
        }).collect(Collectors.toList());
        return interfaceInfoVOS;
    }


}




