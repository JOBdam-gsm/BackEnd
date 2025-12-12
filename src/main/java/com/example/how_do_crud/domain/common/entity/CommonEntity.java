package com.example.how_do_crud.domain.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name="common_table")
public class CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private int studentNumber;

    private String name;

    private String title;

    private int period;

    @Enumerated(EnumType.STRING) //DB에 이 ENUM을 문자열로 저장해줘~
    private StateEnum state = StateEnum.BLANK;

    private String cancellation = "NO";

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
    public void setName(String username) {
        this.name = username;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public void setState(StateEnum state) {
        this.state = state;
    }
    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }
}

