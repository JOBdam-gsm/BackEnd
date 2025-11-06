package com.example.how_do_crud.domain.user.controller;

import com.example.how_do_crud.domain.user.dto.request.UserIdDTO;
import com.example.how_do_crud.domain.user.service.UserService;
import com.example.how_do_crud.domain.user.dto.request.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserInfoDTO> createUser (@RequestBody UserInfoDTO userInfo){
        userService.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserInfoDTO> readUser (@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public void deleteUser (@RequestBody UserIdDTO dto){
        userService.updateUser(dto.getId(), dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
