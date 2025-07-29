package com.porcupine.bookLog.user.service;

import com.porcupine.bookLog.user.dto.User;
import com.porcupine.bookLog.user.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //모든 사용자 검색
    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // 로그인
    @Override
    public User login(String email, String password) {
        User user = userMapper.findByEmail(email);
        if (user != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        return null;
    }

    //회원가입
    @Override
    public void register(User user) {
        // 비밀번호 암호화 처리
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        userMapper.insert(user);
    }

    //세션 로그인 이메일 체크
    public User findByEmail(String email) {
        System.out.println("요청 이메일: " + email);
        User user = userMapper.findByEmail(email);
        System.out.println("조회된 유저: " + user);
        return user;
    }

    //사용자 정보 수정
    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    //사용자 이메일 중복 체크
    @Override
    public boolean isEmailDuplicate(String email) {
        return userMapper.findByEmail(email) != null;
    }
}
