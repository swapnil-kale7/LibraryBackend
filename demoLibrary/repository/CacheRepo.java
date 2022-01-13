package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepo {

    private static  String prefix_key="usr::";

    @Autowired
    RedisTemplate<String,Object>redisTemplate;

    public User fetchUserFromCache(String username){

        return (User) redisTemplate.opsForValue().get(prefix_key+username);
    }

    public void SaveUserIncache(User user){
        redisTemplate.opsForValue().set(prefix_key+user.getUsername(),user);
    }


}
