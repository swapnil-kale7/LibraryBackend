package com.example.libraryManagement.demoLibrary.service;

import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.repository.CacheRepo;
import com.example.libraryManagement.demoLibrary.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userServise implements UserDetailsService {

    @Autowired
    userRepo userRepo;

    @Autowired
    CacheRepo cacheRepo;
    //this is a functionality spring securoty is giving so that u can decide how u can fetch data  and match u just write logic to detch
    //data;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user=cacheRepo.fetchUserFromCache(s);

        if (user!=null){//if we find user in redis then return it or go to database fetch user and add to redis
            return  user;
        }
        user= userRepo.findByUsername(s);
        cacheRepo.SaveUserIncache(user);
        return user;
    }
}
