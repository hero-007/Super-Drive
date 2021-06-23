package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    UserMapper userMapper;
    HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService){
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public User getUser(String username){
        if(username != null && username.length() > 0){
            User user = userMapper.getUser(username);
            return user;
        }
        return null;
    }

    public Integer createNewUser(User user){
        if(user != null){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
            return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
        }
        return -1;
    }
}
