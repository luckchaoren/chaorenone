package com.chaoren.model;

import lombok.Data;

/**
 * Created with Date:2018/10/2 Time:15:53
 * author:LiChao
 */
@Data
public class User {

    private int id;
    private String name;
    private String password;
    private String createTime;
    private String updateTime;
    private String serial;
    private String status;

}
