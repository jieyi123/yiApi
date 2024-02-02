package com.pjieyi.yiapi.service;

import com.pjieyi.yiapi.exception.BusinessException;
import com.pjieyi.yiapi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;


/**
 * 用户服务测试
 *
 * @author pjieyi
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }


    @Test
    void retrievePassword() {
        String userPassword="123456789";
        String checkPassword="123456789";
        String phone="13232323232";
        String verifyCode="1231";
        try {
            long id = userService.retrievePassword(userPassword, checkPassword, phone, verifyCode);
            Assertions.assertTrue(id>0);
        }catch (BusinessException e){
            e.printStackTrace();
        }
    }

    @Test
    void testAliyunSMS(){

    }


}