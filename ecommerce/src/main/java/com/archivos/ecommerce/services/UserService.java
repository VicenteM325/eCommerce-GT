package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.admin.AdminUpdateUserDto;
import com.archivos.ecommerce.entities.Role;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.enums.RoleList;
import com.archivos.ecommerce.repositories.RoleRepository;
import com.archivos.ecommerce.repositories.UserEntityRepository;
import com.archivos.ecommerce.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserEntityRepository userEntityRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserEntityRepository userEntityRepository){
        this.userRepository = userRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(()-> new UsernameNotFoundException("Correo no encontrado") );
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());

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

    public boolean existsByDpi(Long dpi){
        return userRepository.existsByDpi(dpi);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserDetails(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return findByEmailAddress(email);
    }



    //Admin
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserByAdmin(UUID userId, AdminUpdateUserDto dto, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        User user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(dto.getName());
        user.setEmailAddress(dto.getEmailAddress());
        if(dto.getPassword() != null && !dto.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setAddress(dto.getAddress());
        user.setDpi(dto.getDpi());
        user.setUserStatus(dto.getUserStatus());

        RoleList roleEnum;
        try {
            roleEnum = RoleList.valueOf(dto.getRole());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol no válido");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRole(role);

        userRepository.save(user);
    }

    //Eliminar Usuario
    public void deleteUser(UUID userId){
        User user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }
}
