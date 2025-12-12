package com.example.how_do_crud.domain.common.dto;

import com.example.how_do_crud.domain.common.entity.CommonEntity;
import com.example.how_do_crud.domain.common.entity.StateEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateDTO {
    private String name;

    private int studentNumber;

    private String title;

    private int period;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private StateEnum state;

    public CommonEntity toEntity(CreateDTO dto) {
        CommonEntity entity = new CommonEntity();
        entity.setName(name);
        entity.setStudentNumber(studentNumber);
        entity.setTitle(title);
        entity.setPeriod(period);
        entity.setDate(date);
        entity.setState(state);
        return entity;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }
}