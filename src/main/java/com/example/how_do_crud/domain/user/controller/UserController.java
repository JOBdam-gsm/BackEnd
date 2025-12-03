package com.example.how_do_crud.domain.user.controller;

import com.example.how_do_crud.domain.user.dto.request.UserUpdateReq;
import com.example.how_do_crud.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/read/{id}")
    public ResponseEntity<UserDetails> readUser (@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UserUpdateReq request){
        userService.updateUser(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
