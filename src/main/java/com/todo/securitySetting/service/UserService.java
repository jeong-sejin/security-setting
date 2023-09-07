package com.todo.securitySetting.service;

import com.todo.securitySetting.entity.User;
import com.todo.securitySetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    public User getUserBySeq(Long seq) throws Exception {
        return userRepository.findBySeq(seq);
    }

    public User getUserByUserid(String userid) throws Exception {
        return userRepository.findByUserid(userid);
    }

    public User createUser(User user) throws Exception {
        User setUser = User.builder()
                        .userid(user.getUserid())
                        .password(bcryptPasswordEncoder.encode(user.getPassword()))
                        .useYn(true)
                        .roles("ROLE_USER")
                        .build();

        return userRepository.save(setUser);
    }
}