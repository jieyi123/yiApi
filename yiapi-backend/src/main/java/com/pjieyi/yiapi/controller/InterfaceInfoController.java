package com.pjieyi.yiapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.pjieyi.yiapi.annotation.AuthCheck;
import com.pjieyi.yiapi.common.*;
import com.pjieyi.yiapi.constant.CommonConstant;
import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.pjieyi.yiapi.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.pjieyi.yiapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.pjieyi.yiapi.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.pjieyi.yiapi.model.entity.InterfaceInfo;
import com.pjieyi.yiapi.model.entity.User;
import com.pjieyi.yiapi.model.enums.InterfaceInfoStatusEnum;
import com.pjieyi.yiapi.service.InterfaceInfoService;
import com.pjieyi.yiapi.service.UserService;
import com.pjieyi.yiapiclientsdk.client.YiApiClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口管理
 * @author pjieyi
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private YiApiClient yiApiClient;

    // region 增删改查

    /**
     * 创建
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增接口")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        //String content = interfaceInfoQuery.getContent();
        //// content 需支持模糊搜索
        //interfaceInfoQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        String name = interfaceInfoQuery.getName();
        String description = interfaceInfoQuery.getDescription();
        String url = interfaceInfoQuery.getUrl();
        String requestHeader = interfaceInfoQuery.getRequestHeader();
        String responseHeader = interfaceInfoQuery.getResponseHeader();
        Integer status = interfaceInfoQuery.getStatus();
        String method = interfaceInfoQuery.getMethod();
        String startTime = interfaceInfoQueryRequest.getStartTime();
        String endTime = interfaceInfoQueryRequest.getEndTime();

        if (StringUtils.isNotEmpty(name)){
            queryWrapper.like(InterfaceInfo::getName,name);
        }
        if (StringUtils.isNotEmpty(description)){
            queryWrapper.like(InterfaceInfo::getDescription,description);
        }
        if (StringUtils.isNotEmpty(requestHeader)){
            queryWrapper.like(InterfaceInfo::getRequestHeader,requestHeader);
        }
        if (StringUtils.isNotEmpty(url)){
            queryWrapper.like(InterfaceInfo::getUrl,url);
        }
        if (StringUtils.isNotEmpty(responseHeader)){
            queryWrapper.like(InterfaceInfo::getResponseHeader,responseHeader);
        }
        if (status!=null){
            queryWrapper.like(InterfaceInfo::getStatus,status);
        }
        if (StringUtils.isNotEmpty(method)){
            queryWrapper.like(InterfaceInfo::getMethod,method);
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNoneEmpty(endTime)){
            //大于等于
            queryWrapper.ge(InterfaceInfo::getCreateTime,startTime);
            //小于等于
            queryWrapper.le(InterfaceInfo::getCreateTime,endTime);
        }
        queryWrapper.orderByDesc(InterfaceInfo::getUpdateTime);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    // endregion

    /**
     * 上线接口
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin") //管理员才能调用
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        //校验参数
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.判断接口是否存在
        InterfaceInfo interfaceId = interfaceInfoService.getById(idRequest.getId());
        if (interfaceId==null){
            //接口不存在
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        //2.判断接口是否可以调用
        //todo 动态的修改 接口上线只能管理员能操作，是否不需要验证接口
        //先模拟调用接口
        com.pjieyi.yiapiclientsdk.model.User user=new com.pjieyi.yiapiclientsdk.model.User();
        user.setName("pjieyi");
        String nameByPostRestful = yiApiClient.getNameByPostRestful(user);
        if (StringUtils.isAnyBlank(nameByPostRestful)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证失败");
        }
        InterfaceInfo info=new InterfaceInfo();
        info.setId(idRequest.getId());
        //3.修改接口数据库中的状态字段为上线
        info.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(info);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        //校验参数
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.判断接口是否存在
        InterfaceInfo interfaceId = interfaceInfoService.getById(idRequest.getId());
        if (interfaceId==null){
            //接口不存在
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        InterfaceInfo info=new InterfaceInfo();
        info.setId(idRequest.getId());
        //3.修改接口数据库中的状态字段为下线
        info.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(info);
        return ResultUtils.success(result);

    }

    /**
     * 测试调用
     * @param interfaceInfoInvoke
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<String> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvoke,
                                                      HttpServletRequest request) {
        //校验参数
        if (interfaceInfoInvoke == null || interfaceInfoInvoke.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.判断接口是否存在
        InterfaceInfo interfaceId = interfaceInfoService.getById(interfaceInfoInvoke.getId());
        if (interfaceId==null){
            //接口不存在
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        String userRequestParams = interfaceInfoInvoke.getUserRequestParams();
        //获取当前用户
        User loginUser = userService.getLoginUser(request);
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        //创建客户端
        YiApiClient apiClient=new YiApiClient(accessKey,secretKey);
        // 我们只需要进行测试调用，所以我们需要解析传递过来的参数。
        Gson gson=new Gson();
        // 将用户请求参数转换为com.pjieyi.yiapiclientsdk.model.User对象
        com.pjieyi.yiapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.pjieyi.yiapiclientsdk.model.User.class);
        String result = yiApiClient.getNameByPostRestful(user);
        return ResultUtils.success(result);

    }

}
