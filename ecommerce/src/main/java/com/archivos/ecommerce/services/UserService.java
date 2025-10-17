package com.archivos.ecommerce.services;

import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(()-> new UsernameNotFoundException("Correo no encontrado") );
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toString());

        return new org.springframework.security.core.userdetails.User(
                user.getEmailAddress(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }

    public User findByEmailAddress(String email){
        return userRepository.findByEmailAddress(email)
                .orElseThrow(()->new UsernameNotFoundException("Correo no encontrado"));
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmailAddress(email);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserDetails(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return findByEmailAddress(email);
    }
}
