package com.mountbirch.user.service;

import com.mountbirch.user.model.User;
import com.mountbirch.user.model.UserRegistrationFormDto;
import com.mountbirch.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Kaur Laanemäe on 21/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String email){
        User user = userRepository.findByPrimaryEmailIgnoreCase(email);
        return user!=null;
    }

    public User createUserFromRegistrationForm(UserRegistrationFormDto userRegistrationFormDto){

        User userToRegister = new User();
        userToRegister.setCreatedOn(LocalDateTime.now());
        userToRegister.setPrimaryEmail(userRegistrationFormDto.getEmail());
        userToRegister.setPassword(passwordEncoder.encode(userRegistrationFormDto.getPassword()));
        userToRegister.setEnabled(true);
        userToRegister.setValidated(false);

        userRepository.save(userToRegister);

        return userToRegister;
    }
}
