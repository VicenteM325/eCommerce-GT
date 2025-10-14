package com.archivos.ecommerce.controllers;


import com.archivos.ecommerce.dtos.LoginUserDto;
import com.archivos.ecommerce.dtos.NewUserDto;
import com.archivos.ecommerce.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
        try{
            String jwt = authService.authenticate(loginUserDto.getEmailAddress(), loginUserDto.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.ok("Registrado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok("Autenticado correctamente");
    }
}
