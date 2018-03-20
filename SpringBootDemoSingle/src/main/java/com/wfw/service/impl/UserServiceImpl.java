package com.wfw.service.impl;

import com.wfw.dao.UsersMapper;
import com.wfw.dto.user.UserDTO;
import com.wfw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by killer9527 on 2018/3/20.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<UserDTO> listUsers(int pageIndex, int pageSize) {
        int from = (pageIndex - 1) * pageSize;
        return this.usersMapper.listUsers(from, pageSize);
    }

    @Override
    public int countUsers() {
        return this.usersMapper.countUsers();
    }
}
