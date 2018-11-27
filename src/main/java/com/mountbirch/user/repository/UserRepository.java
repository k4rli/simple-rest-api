package com.mountbirch.user.repository;

import com.mountbirch.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kaur Laanemäe on 19/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByPrimaryEmailIgnoreCase(String primaryEmail);
}
