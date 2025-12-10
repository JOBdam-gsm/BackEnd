package com.example.how_do_crud.domain.auth.controller;

import com.example.how_do_crud.domain.auth.dto.request.LoginReq;
import com.example.how_do_crud.domain.auth.dto.RefreshTokenDTO;
import com.example.how_do_crud.domain.auth.dto.request.SignUpReq;
import com.example.how_do_crud.domain.auth.JwtProvider;
import com.example.how_do_crud.domain.auth.dto.TokenResponseDTO;
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
    public ResponseEntity<HttpStatus > signUp(@RequestBody SignUpReq request){
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(request.roles())
                .build();
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginReq login){
        UserDetails userDetails = userService.readUser(login.email());

        if(passwordEncoder.matches(login.password(), userDetails.getPassword())){
            String accessToken = jwtProvider.createAccessToken(
                    userDetails.getUsername(), userDetails.getAuthorities());
            String refreshToken = jwtProvider.createRefreshToken(userDetails.getUsername());

            return new ResponseEntity<>(
                    TokenResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build()
                    ,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody RefreshTokenDTO request){
        String refreshToken = request.refreshToken();
        if(refreshToken == null || !jwtProvider.validateToken(refreshToken)){ // refreshToken 이 유효하지 않을때
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
