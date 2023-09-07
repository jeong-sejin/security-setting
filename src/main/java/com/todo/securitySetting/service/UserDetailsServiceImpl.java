package com.todo.securitySetting.service;

import com.todo.securitySetting.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    ////////////////////////////////////////////////////////////////////////////////
    //< public functions (override)

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
      User user = null;
      try {
          user = userService.getUserByUserid(userid);
      } catch (Exception e) {
          throw new UsernameNotFoundException(e.getMessage());
      }

      return user;
    }

}