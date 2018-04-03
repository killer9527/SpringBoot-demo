package com.wfw.service;

import com.wfw.dto.user.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by killer9527 on 2018/4/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void listUsers() throws Exception {
        List<UserDTO> users = this.userService.listUsers(1, 10);
        System.out.println(users.size());
    }

    @Test
    public void countUsers() throws Exception {
        int userCount = this.userService.countUsers();
        System.out.println(userCount);
    }

}