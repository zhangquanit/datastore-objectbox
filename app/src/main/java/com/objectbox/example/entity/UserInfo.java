package com.objectbox.example.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;

@Entity
public class UserInfo {

    public long uid;
    @Id
    public long id;

    @NameInDb("name") //数据库中保存的名字
    public String username;
    @Index         //如果某个字段需要频繁查询，使用Index可以提高查询效率
    public String password;


    @Transient     //标识该字段不能被持久化
    public int tempColumn;

    /**
     * 如果字段不是public的，需要提供get方法
     */
}
