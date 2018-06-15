package com.wfw.dao;

import com.wfw.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by killer9527 on 2018/6/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional
    //单元测试中事务是默认回滚的，因此设置为false，可以实现对数据库的修改
    @Rollback(false)
    @Test
    public void testRepeatableRead(){
        /**
         * mysql事务的隔离级别默认是“Repeatable read”，就是在开始读取数据（事务开启）后，同一事务中读取的数据都是相同的
         */
        Users userEntity = this.usersMapper.selectByPrimaryKey(1);
        //在两次操作之中在数据库中修改该行的值（模拟其他session的update操作），再次在该事务中读该行数据，发现改行数据并没有改变
        userEntity = this.usersMapper.selectByPrimaryKey(1);
        System.out.println();
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testSelectForUpdate(){
        //使用select ... for update后对所有select出的行加锁
        Users userEntity = this.usersMapper.selectByPrimaryKeyForUpdate(1);
        //在数据库中修改该行的值（模拟其他session的update操作），此时该事务还未提交，未释放行所，其他update操作会阻塞
        userEntity.setDepartmentId(10);
        int result = this.usersMapper.updateByPrimaryKey(userEntity);
        System.out.println(result);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testSelectForUpdateAfterUpdate(){
        //使用select ... for update后对所有select出的行加锁
        Users userEntity = this.usersMapper.selectByPrimaryKeyForUpdate(1);
        //在数据库中修改该行的值（模拟其他session的update操作），此时该事务还未提交，未释放行所，其他update操作会阻塞
        userEntity.setDepartmentId(userEntity.getDepartmentId() + 1);
        int result = this.usersMapper.updateByPrimaryKey(userEntity);
        System.out.println(result);
    }
}
