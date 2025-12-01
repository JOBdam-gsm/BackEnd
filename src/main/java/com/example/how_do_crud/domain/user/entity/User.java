package com.example.how_do_crud.domain.user.entity;


import com.example.how_do_crud.domain.auth.dto.SignUpDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
//@RequiredArgsConstructor
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
    private List<String> roles = new ArrayList<>();



    public User(String email, String password, List<String> roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public User(SignUpDTO dto){
        this.name = dto.name();
        this.email = dto.email();
        this.password = dto.password();
        setRoles(dto.role());
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public List<String> getRoles(){
        return roles;
    }
    public String getName() {return name;}

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRoles(String roles){
        this.roles.add(roles);
    }
}
