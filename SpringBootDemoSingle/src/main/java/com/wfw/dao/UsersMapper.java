package com.wfw.dao;

import com.wfw.dto.user.UserDTO;
import com.wfw.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<UserDTO> listUsers(@Param(value = "from") int from,
                            @Param(value = "size") int size);

    int countUsers();

    Users selectByPrimaryKeyForUpdate(Integer id);
}