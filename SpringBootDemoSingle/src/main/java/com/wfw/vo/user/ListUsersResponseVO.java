package com.wfw.vo.user;

import com.wfw.dto.user.UserDTO;
import com.wfw.vo.BasePageResponseVO;
import io.swagger.annotations.ApiModel;

/**
 * Created by killer9527 on 2018/3/20.
 */
@ApiModel(value = "ListUsersResponseVO", description = "获取用户列表响应")
public class ListUsersResponseVO extends BasePageResponseVO<UserDTO> {
}
