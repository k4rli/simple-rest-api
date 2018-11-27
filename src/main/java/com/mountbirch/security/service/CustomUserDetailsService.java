package com.mountbirch.security.service;

import com.mountbirch.security.model.CustomUserPrincipal;
import com.mountbirch.user.model.User;
import com.mountbirch.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Kaur Laanemäe on 19/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPrimaryEmailIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserPrincipal(user);
    }

}
