package com.pjieyi.yiapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pjieyi.yiapi.annotation.AuthCheck;
import com.pjieyi.yiapi.common.BaseResponse;
import com.pjieyi.yiapi.common.ResultUtils;
import com.pjieyi.yiapi.mapper.UserInterfaceInfoMapper;
import com.pjieyi.yiapi.model.vo.InterfaceInfoVO;
import com.pjieyi.yiapi.service.InterfaceInfoService;
import com.pjieyi.yiapi.service.UserInterfaceInfoService;
import com.pjieyi.yiapicommon.model.entity.InterfaceInfo;
import com.pjieyi.yiapicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pjieyi
 * @description 统计控制类
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    /**
     * 获取调用次数最多的接口信息列表
     * @return
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){

        return ResultUtils.success(userInterfaceInfoService.listTopInvokeInterfaceInfo());
    }

}
