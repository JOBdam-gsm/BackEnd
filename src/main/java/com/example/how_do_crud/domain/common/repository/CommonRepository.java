package com.example.how_do_crud.domain.common.repository;

import com.example.how_do_crud.domain.common.entity.CommonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommonRepository extends JpaRepository<CommonEntity, Long> {
    Optional<CommonEntity> findByDateAndPeriodAndStudentNumberAndCancellation(
            LocalDate date,
            int period,
            int studentNumber,
            String cancellation);
    List<CommonEntity> findByDateAndPeriod(LocalDate date, int period);
}