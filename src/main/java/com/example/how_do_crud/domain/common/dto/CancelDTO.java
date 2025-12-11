package com.example.how_do_crud.domain.common.dto;

import com.example.how_do_crud.domain.common.entity.CommonEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CancelDTO {
    private int period;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int studentNumber;

    private String cancellation;

    public CommonEntity toEntity(CancelDTO dto) {
        CommonEntity entity = new CommonEntity();
        entity.setPeriod(period);
        entity.setDate(date);
        entity.setStudentNumber(studentNumber);
        entity.setCancellation(cancellation);
        return entity;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }
}
