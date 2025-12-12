package com.example.how_do_crud.domain.common.dto;

import com.example.how_do_crud.domain.common.entity.StateEnum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AllowDTO {

    private LocalDate date;

    private int period;

    private int studentNumber;

    private StateEnum state;

    private String cancellation;
}
