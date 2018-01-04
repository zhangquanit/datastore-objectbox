package com.objectbox.example.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserInfo {
    @Id
    public long id;

    public String username;
    public String password;
}
