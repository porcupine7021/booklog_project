package com.porcupine.bookLog.bookRecord.service;

import com.porcupine.bookLog.bookRecord.dto.BookRecordDTO;
import com.porcupine.bookLog.bookRecord.mapper.BookRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRecordServiceImpl implements BookRecordService {

    private final BookRecordMapper bookRecordMapper;

    public BookRecordServiceImpl(BookRecordMapper bookRecordMapper) {
        this.bookRecordMapper = bookRecordMapper;
    }

    @Override
    public List<BookRecordDTO> getRecordsByUserId(String userId) {
        return bookRecordMapper.getRecordsByUserId(userId);
    }

    @Override
    public void updateRecord(BookRecordDTO bookRecordDTO) {
        bookRecordMapper.updateRecord(bookRecordDTO);
    }

    @Override
    public void deleteRecord(int id) {
        bookRecordMapper.deleteRecord(id);
    }

    @Override
    public void saveRecord(BookRecordDTO bookRecordDTO) {
        bookRecordMapper.insertBookRecord(bookRecordDTO);
    }
}
