package com.pjieyi.yiapi.service.impl;

import com.pjieyi.yiapi.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author pjieyi
 * @description
 */
@SpringBootTest
class UserInterfaceInfoServiceImplTest {



    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Test
    void invokeCount() {
        boolean result = userInterfaceInfoService.invokeCount(1, 1);
        Assertions.assertTrue(result);
    }
}