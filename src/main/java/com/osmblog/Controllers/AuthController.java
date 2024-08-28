package com.osmblog.Controllers;

import com.osmblog.Payload.UserDto;
import com.osmblog.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> creteUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        UserDto user = userService.signUp(userDto);
        return new ResponseEntity<>("User Created Successfully!!!", HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> verifyLogin(@RequestBody UserDto userDto) {
        String status = userService.signIn(userDto);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
