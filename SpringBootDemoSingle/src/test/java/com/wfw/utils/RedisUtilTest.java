package com.wfw.utils;

import com.wfw.dto.user.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by killer9527 on 2018/4/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedisUtil(){
        //设置redis
        String key = UUID.randomUUID().toString();
        redisUtil.set(key, "this is a redis test without timeout");
        //取值
        String value = redisUtil.get(key);
        System.out.println(value);
        //删除key
        redisUtil.delete(key);
        value = redisUtil.get(key);
        System.out.println(value);

        key = UUID.randomUUID().toString();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("wfw");
        userDTO.setDepartment("tech");
        redisUtil.set(key, userDTO, 10000);
        UserDTO userValue = redisUtil.get(key, UserDTO.class);
        System.out.println(userValue.getUserName() + ": " + userValue.getDepartment());
    }
}
