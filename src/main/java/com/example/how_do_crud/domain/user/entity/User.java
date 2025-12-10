package com.example.how_do_crud.domain.user.entity;


import com.example.how_do_crud.domain.user.dto.request.UserUpdateReq;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) // null 안되고 중복 X
    private String email;

    @Column(nullable = false) // null만 이니면 됨
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<String> roles = new ArrayList<>();

    public void update(String email, String password){
        this.email = email;
        this.password = password;
    }
}
