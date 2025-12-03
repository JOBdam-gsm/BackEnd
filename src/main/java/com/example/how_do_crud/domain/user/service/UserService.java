package com.example.how_do_crud.domain.user.service;

import com.example.how_do_crud.domain.user.dto.request.UserCreateReq;
import com.example.how_do_crud.domain.user.dto.request.UserInfoDTO;
import com.example.how_do_crud.domain.user.dto.request.UserIdDTO;
import com.example.how_do_crud.domain.user.dto.request.UserUpdateReq;
import com.example.how_do_crud.domain.user.entity.CustomUserDetails;
import com.example.how_do_crud.domain.user.entity.User;
import com.example.how_do_crud.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }

    public UserDetails readUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
        return new CustomUserDetails(user);
    }
    public UserDetails readUser(String name){
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new IllegalArgumentException(""));
        return new CustomUserDetails(user);
    }

    public void updateUser(UserUpdateReq request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException(""));
        user.update(request);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
        userRepository.delete(user);
    }
}
