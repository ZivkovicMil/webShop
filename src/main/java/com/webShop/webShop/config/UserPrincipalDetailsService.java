package com.webShop.webShop.config;

import com.webShop.webShop.entities.User;
import com.webShop.webShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
        throw new UsernameNotFoundException("User with that username doesn't exist");
    }
}
