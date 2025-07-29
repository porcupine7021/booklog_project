package com.porcupine.bookLog.user.mapper;
import com.porcupine.bookLog.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    void insert(User user);
    User findByEmail(String email);
    List<User> findAll();
    void update(User user);

}