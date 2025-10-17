package com.archivos.ecommerce.controllers;


import com.archivos.ecommerce.dtos.ApiMessage;
import com.archivos.ecommerce.dtos.LoginUserDto;
import com.archivos.ecommerce.dtos.NewUserDto;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.services.AuthService;
import com.archivos.ecommerce.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiMessage> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiMessage("Credenciales Incorrectas"));
        }
        try{
            String roleName = authService.authenticate(loginUserDto.getEmailAddress(), loginUserDto.getPassword(), response);
            return ResponseEntity.ok(new ApiMessage(roleName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ApiMessage> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiMessage("Revise los campos"));
        }
        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.ok(new ApiMessage("Registrado Correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok("Autenticado correctamente");
    }

    @GetMapping("/user/details")
    public ResponseEntity<User> getAuthenticateUser(){
        User user = userService.getUserDetails();
        return ResponseEntity.ok(user);
    }
}
