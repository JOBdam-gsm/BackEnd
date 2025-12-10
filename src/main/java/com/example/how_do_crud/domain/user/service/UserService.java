package com.example.how_do_crud.domain.user.service;

import com.example.how_do_crud.domain.user.dto.request.UserUpdateReq;
import com.example.how_do_crud.domain.user.entity.CustomUserDetails;
import com.example.how_do_crud.domain.user.entity.User;
import com.example.how_do_crud.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        return new CustomUserDetails(user);
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public UserDetails readUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return new CustomUserDetails(user);
    }
    public UserDetails readUser(String name){
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return new CustomUserDetails(user);
    }

    public void updateUser(UserUpdateReq request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        user.update(request.email(), passwordEncoder.encode(request.password()));
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
        userRepository.delete(user);
    }
}
