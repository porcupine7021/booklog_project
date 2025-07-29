package com.porcupine.bookLog.bookRecord.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookRecordDTO {
       private String bookRecodeId;
       private String userId;
       private String bookId;
       private String rating;
       private LocalDate readDate;
       private LocalDateTime bookLogCreateAt;

}
