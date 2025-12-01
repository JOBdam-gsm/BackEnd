package com.example.how_do_crud.domain.auth.controller;

import com.example.how_do_crud.domain.auth.dto.LoginInfoDTO;
import com.example.how_do_crud.domain.auth.dto.RefreshTokenDTO;
import com.example.how_do_crud.domain.auth.dto.SignUpDTO;
import com.example.how_do_crud.domain.auth.JwtProvider;
import com.example.how_do_crud.domain.auth.dto.TokenResponseDTO;
import com.example.how_do_crud.domain.user.dto.request.UserInfoDTO;
import com.example.how_do_crud.domain.user.entity.User;
import com.example.how_do_crud.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthContoller {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<UserInfoDTO> signUp(@RequestBody SignUpDTO signUp){
        User user = new User(signUp); // 역할은 따로 프론트에서 같이 보내는 식으로

        String password = passwordEncoder.encode(signUp.password());

        userService.createUser(
                new UserInfoDTO(user.getEmail(), password, user.getRoles()));
        return new ResponseEntity<>(
                new UserInfoDTO(user.getEmail(), user.getRoles()), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginInfoDTO login){
        UserDetails userDetails = userService.readUser(login.email());

        String accessToken = jwtProvider.createAccessToken(
                userDetails.getUsername(), userDetails.getAuthorities());
        String refreshToken = jwtProvider.createRefreshToken(userDetails.getUsername());

        if(passwordEncoder.matches(login.password(), userDetails.getPassword())){
            return new ResponseEntity<>(
                    TokenResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build()
                    ,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @PostMapping("/logout")
    public void logout(){

    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody RefreshTokenDTO request){
        String refreshToken = request.refreshToken();
        if(refreshToken == null || !jwtProvider.validateToken(refreshToken)){ // accessToken 이 유효하지 않을때
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = jwtProvider.getUsername(refreshToken);
        UserDetails userDetails = userService.readUser(username);

        String accessToken = jwtProvider.createAccessToken(
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
        return new ResponseEntity<>(
                TokenResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
                ,HttpStatus.OK);
    }
}
