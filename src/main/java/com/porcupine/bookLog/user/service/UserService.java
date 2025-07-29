package com.porcupine.bookLog.user.service;

import com.porcupine.bookLog.user.dto.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(); //모든 사용자 검색
    User login(String email, String password); // 로그인
    void register(User user); // 회원가입

    User findByEmail(String email);

    void updateUser(User user);//사용자 정보 수정

    boolean isEmailDuplicate(String email);//사용자 이메일 중복 체크

}
