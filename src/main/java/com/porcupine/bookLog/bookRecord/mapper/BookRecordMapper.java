package com.porcupine.bookLog.bookRecord.mapper;
import com.porcupine.bookLog.bookRecord.dto.BookRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookRecordMapper {
    void insertBookRecord(BookRecordDTO dto);

    List<BookRecordDTO> getRecordsByUserId(String userId);

    void updateRecord(BookRecordDTO bookRecordDTO);

    void deleteRecord(int id);
}
