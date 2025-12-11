package com.example.how_do_crud.domain.common.dto;

import com.example.how_do_crud.domain.common.entity.CommonEntity;
import com.example.how_do_crud.domain.common.entity.StateEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LockDTO {
    private int period;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private StateEnum state;

    public CommonEntity toEntity(LockDTO dto) {
        CommonEntity entity = new CommonEntity();
        entity.setPeriod(period);
        entity.setDate(date);
        entity.setState(state);
        return entity;
    }

    public void setCondition(StateEnum state) {
        this.state = state;
    }
}