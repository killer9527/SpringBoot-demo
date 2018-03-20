package com.wfw.service;


import com.wfw.dto.user.UserDTO;

import java.util.List;

/**
 * Created by killer9527 on 2018/3/20.
 */
public interface UserService {
    List<UserDTO> listUsers(int pageIndex, int pageSize);
    int countUsers();
}
