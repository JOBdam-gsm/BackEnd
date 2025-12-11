package com.example.how_do_crud.domain.common.service;

import com.example.how_do_crud.domain.common.dto.AllowDTO;
import com.example.how_do_crud.domain.common.dto.CancelDTO;
import com.example.how_do_crud.domain.common.dto.CreateDTO;
import com.example.how_do_crud.domain.common.dto.LockDTO;
import com.example.how_do_crud.domain.common.entity.CommonEntity;
import com.example.how_do_crud.domain.common.entity.StateEnum;
import com.example.how_do_crud.domain.common.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonRepository commonrepository;

    public void commonSave(CommonEntity entity) {
        commonrepository.save(entity);
    }

    public void createReservation(CreateDTO dto) { //신청시 그럼 WAITING

        List<CommonEntity> CreateList = commonrepository.findByDateAndPeriod(dto.getDate(), dto.getPeriod());

        if (CreateList.isEmpty()) {
            dto.setState(StateEnum.WAITING);
            commonSave(dto.toEntity(dto));
        }
        else {
            for (CommonEntity entity : CreateList) {
                if (
                        entity.getName().equals(dto.getName()) &&
                        entity.getStudentNumber() == dto.getStudentNumber() &&
                        entity.getCancellation().equals("NO")) { //StudentName도 비교해야함
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 예약한 시간입니다.");
                }
                else if (entity.getName().equals(dto.getName()) && entity.getCancellation().equals("YES")) {
                    dto.setState(StateEnum.WAITING);
                    commonSave(dto.toEntity(dto));
                }
                else if (entity.getState().equals(StateEnum.RESERVED)) {

                    throw new ResponseStatusException(HttpStatus.CONFLICT, "누군가 예약한 시간입니다.");
                }
                else if(entity.getState().equals(StateEnum.LOCKED)) {
                    throw new ResponseStatusException(HttpStatus.LOCKED, "잠궈진 날짜 입니다.");
                }
                else {
                    //여기에 log가 있으면 좋겠어
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "생성 할 수 없습니다.");
                }
            }
        }
    }

    public void cancelReservation(CancelDTO dto) {
        CommonEntity entity = commonrepository.findByDateAndPeriodAndStudentNumberAndCancellation(
                dto.getDate(),
                dto.getPeriod(),
                dto.getStudentNumber(),
                dto.getCancellation()) .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.CONFLICT, "생성 할 수 없습니다."));
        if(entity != null) {
            entity.setCancellation("YES"); //프론트에서 NO
            commonSave(entity);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "값을 찾지 못했습니다.");
        }
    }

    public void allow(AllowDTO dto) { //예약의 현재상태를 불러옴 만약 YES 라면 YES를 불러옴
        CommonEntity entity = commonrepository.findByDateAndPeriodAndStudentNumberAndCancellation(
                dto.getDate(),
                dto.getPeriod(),
                dto.getStudentNumber(),
                dto.getCancellation()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "값을 찾을 수 없습니다."));
        if(entity.getCancellation().equals("YES")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 취소된 에약입니다.");
        }
        entity.setState(StateEnum.RESERVED);
        commonSave(entity);
    }

    public void teacherRock(LockDTO dto) { //날짜 + ROCKED
        commonSave(dto.toEntity(dto));
        List<CommonEntity> RockList = commonrepository.findByDateAndPeriod(dto.getDate(), dto.getPeriod());
        for(CommonEntity entity : RockList) {
            if(entity.getCancellation().equals("YES")) {
                continue;
            }
            entity.setCancellation("YES");
            commonSave(entity);
        }
        throw new ResponseStatusException(HttpStatus.LOCKED, "해당 시간을 잠궜습니다.");
    }
}