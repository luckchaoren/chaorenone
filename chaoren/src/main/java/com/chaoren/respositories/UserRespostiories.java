package com.chaoren.respositories;

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


    public int AddUserOne(){

        return 0;
    }

}
