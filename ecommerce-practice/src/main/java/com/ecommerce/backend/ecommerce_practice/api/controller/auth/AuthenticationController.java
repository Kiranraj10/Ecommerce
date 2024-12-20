package com.ecommerce.backend.ecommerce_practice.api.controller.auth;

import com.ecommerce.backend.ecommerce_practice.api.modal.LoginBody;
import com.ecommerce.backend.ecommerce_practice.api.modal.LoginResponse;
import com.ecommerce.backend.ecommerce_practice.api.modal.RegistrationBody;
import com.ecommerce.backend.ecommerce_practice.exception.EmailFailureException;
import com.ecommerce.backend.ecommerce_practice.exception.UserAlreadyExistException;
import com.ecommerce.backend.ecommerce_practice.exception.UserNotVerified;
import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    //this calls the registerUser method in userService
    @PostMapping("/register")
    public ResponseEntity registerUser(
        @Valid @RequestBody RegistrationBody registrationBody
    ) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException | EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }
    }
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token1) {
        if(userService.verifyUser(token1)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginBody loginBody
    ) {
        String jwt= null;
        try {
            jwt = userService.loginUser(loginBody);
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (UserNotVerified e) {
            LoginResponse response=new LoginResponse();
            response.setSuccess(false);
            String reason="User not verified";
            if(e.isEmailSent()){
                reason="Email not sent";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        }
        if(jwt==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
       else{
            LoginResponse response=new LoginResponse();
            response.setJWT(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);

        }


    }
}
