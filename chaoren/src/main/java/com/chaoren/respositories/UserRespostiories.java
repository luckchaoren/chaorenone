package com.chaoren.respositories;

import com.chaoren.common.utils.DateUtil;
import com.chaoren.common.utils.UUIDUtil;
import com.chaoren.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with Date:2018/10/2 Time:15:59
 * author:LiChao
 */
@Transactional
@Repository
public class UserRespostiories {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 增加用户
     * @param user
     * @return
     */
    public int addUserOne(User user){
        int i = 0;
        String sql = "INSERT INTO `user` (`name`,`password`,`createTime`,`updateTime`,`serial`,`status`) VALUES (?,?,?,?,?,1)";
        i = jdbcTemplate.update(sql,new Object[] {
            user.getName(), user.getPassword(),
                DateUtil.getNow("yyyy-MM-dd"),DateUtil.getNow("yyyy-MM-dd"),
                UUIDUtil.getUUID()

        });
        return i;
    }

}
