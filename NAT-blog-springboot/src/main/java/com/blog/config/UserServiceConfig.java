package com.blog.config;

import com.blog.entity.RoleEntity;
import com.blog.entity.UserEntity;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserServiceConfig implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user == null || user.is_enabled() == false)
        {
            throw new UsernameNotFoundException("Colud not find username");
        }
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList())
        );

//        UserEntity user = new UserEntity();
//        user.setUsername("demo");
//        user.setPassword(passwordEncoder.encode("demo"));
//        Collection<RoleEntity> userRoles = new ArrayList<>();
//        RoleEntity r = new RoleEntity();
//        r.setName("ADMIN");
//        userRoles.add(r);
//        user.setRoles(userRoles);
//        if(user == null)
//        {
//            throw new UsernameNotFoundException("Colud not find username");
//        }
//        return new User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRoles()
//                        .stream()
//                        .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList())
//        );
    }
}
