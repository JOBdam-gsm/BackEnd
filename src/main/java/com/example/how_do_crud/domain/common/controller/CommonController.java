package com.example.how_do_crud.domain.common.controller;

import com.example.how_do_crud.domain.common.dto.AllowDTO;
import com.example.how_do_crud.domain.common.dto.CancelDTO;
import com.example.how_do_crud.domain.common.dto.CreateDTO;
import com.example.how_do_crud.domain.common.dto.LockDTO;
import com.example.how_do_crud.domain.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/common")
@RestController //언제부터 rest controller 였지?
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @PostMapping("/create/reservation")
    public ResponseEntity<?> createReservation(@RequestBody CreateDTO dto) {
        commonService.createReservation(dto);
        return ResponseEntity.ok().body("선생님께서 요청 검토중 입니다.");
    }

    @DeleteMapping("/cancel/reservation")
    public ResponseEntity<?> cancelReservation(@RequestBody CancelDTO dto) {
        commonService.cancelReservation(dto);
        return ResponseEntity.ok().body("취소되었습니다.");
    }

    @PutMapping("/create/reservation/allow") //이거 선생님 쪽으로 가야함
    public ResponseEntity<?> createReservationAllow(@RequestBody AllowDTO dto) {//
        commonService.allow(dto);
        return ResponseEntity.status(204).body("요청을 수락했습니다.");
    }

    @PostMapping("/teacher/rock")
    public void teacherRock(@RequestBody LockDTO dto) {
        commonService.teacherRock(dto);
    }
}