package massimomauro.S7L5FinalProject.controllers;

import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.exceptions.BadRequestException;
import massimomauro.S7L5FinalProject.payloads.users.NewUserDTO;
import massimomauro.S7L5FinalProject.payloads.users.UserLoginDTO;
import massimomauro.S7L5FinalProject.payloads.users.UserLoginSuccessDTO;
import massimomauro.S7L5FinalProject.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body){

        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.registerUser(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
