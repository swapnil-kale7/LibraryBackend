package com.example.libraryManagement.demoLibrary;

import com.example.libraryManagement.demoLibrary.service.userServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LibraryConfig extends WebSecurityConfigurerAdapter {

    //User and ADMIN ROles

    @Autowired
    userServise userServise;

    @Value("${app.security.user_role}")
    private String Student_role;

    @Value("${app.security.admin_role}")
    private String ADMIN_ROLE;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServise);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//csrf is req to disable to make post api which has permitall autho accesible
        //spring need csrf token for unsafe method if autho is permit all so disable it

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/book/**").hasAnyAuthority(ADMIN_ROLE, Student_role)
                .antMatchers("/book/**").hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST,"/student/**").permitAll()
                .antMatchers(HttpMethod.GET,"/student/Id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET,"/student/**").hasAuthority(Student_role)
                .antMatchers(HttpMethod.GET,"/transaction/fine/student_id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers("/transaction/**").hasAuthority(Student_role)
                .antMatchers("/admin/**").hasAuthority(ADMIN_ROLE)
                .and().formLogin();

    }
    @Bean
    public PasswordEncoder getpe(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LettuceConnectionFactory getredisFactory(){

        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(
                "localhost",6379
        );
        //now connecting with cloud redis mention cloud url without port no in hostname and put port no in port
        //now for password
//        redisStandaloneConfiguration.setPassword("your password");
        LettuceConnectionFactory lettuceConnectionFactory=new LettuceConnectionFactory(redisStandaloneConfiguration);

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate getTemplate(){
        //use object below to make redistemp generic

        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());//new StringRedisSerializer())this covert string to byte and jdkSerial coverts java obj to byte;
        //similar to value serializable we can set Hash value serializable
        //for given field we can store obj in hash in rdis
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        //incase we are passing something else as key we can use key serializable
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //another thing we need is this template should run on which connection factory
        redisTemplate.setConnectionFactory(getredisFactory());
        return redisTemplate;
    }
}
