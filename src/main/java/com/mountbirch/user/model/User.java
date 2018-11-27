package com.mountbirch.user.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by Kaur Laanemäe on 19/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Data
@Entity( name = "users" )
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String password;
    private String primaryEmail;
    private Boolean validated;
    private Boolean enabled;
    private LocalDateTime createdOn;
    private LocalDateTime lastLogin;
}
