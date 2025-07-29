package com.porcupine.bookLog.bookRecord.service;

import com.porcupine.bookLog.bookRecord.dto.BookRecordDTO;
import java.util.List;

public interface BookRecordService {

    // 책 기록 추가
    void saveRecord(BookRecordDTO dto);

    // 사용자 ID로 기록 전체 조회
    List<BookRecordDTO> getRecordsByUserId(String userId);

    // 책 기록 수정
    void updateRecord(BookRecordDTO dto);

    // 책 기록 삭제
    void deleteRecord(int id);
}
