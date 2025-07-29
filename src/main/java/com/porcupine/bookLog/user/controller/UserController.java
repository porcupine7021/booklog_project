package com.porcupine.bookLog.user.controller;
import com.porcupine.bookLog.user.dto.LoginRequest;
import com.porcupine.bookLog.user.dto.User;
import com.porcupine.bookLog.user.mapper.UserMapper;
import com.porcupine.bookLog.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserMapper userMapper, UserService userService, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    /*
    // 사용자 검색 테스트
    @GetMapping
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }*/

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("회원가입 성공");
    }

    /*
    //로그인 테스트
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User found = userService.login(user.getEmail(), user.getPasswordHash());
        if (found != null) {

            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }*/

    //모든 사용자 조회
    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }
    
    //세션 로그인
    @PostMapping("/sessionLogin")
    public ResponseEntity<String> sessionLogin(@RequestBody LoginRequest request, HttpSession session){
        User user = userService.findByEmail(request.getEmail());
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일이 존재하지 않습니다");
        }
        //비밀번호 일치 확인
        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }
        //세션에 사용자 정보 저장
        session.setAttribute("user",user);
        
        return ResponseEntity.ok("로그인 성공");
    }

    
    //로그인 사용자만 접근 가능한 페이지 테스트
    @GetMapping("/mypage")
    public ResponseEntity<String> mypage(HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }
        return ResponseEntity.ok(loggedInUser +"님의 마이페이지 입니다.");
    }


    //세션 기반 인증 확인 API
    @GetMapping("/auth/check")
    public ResponseEntity<?> authCheck(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.invalidate();//세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }

    //사용자 정보 수정
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody User updateUser, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        updateUser.setId(loggedUser.getId()); // 현재 로그인된 사용자 기준으로 수정
        userService.updateUser(updateUser); // controller는 service 호출만 하면 됨

        // 세션 정보 갱신
        User refreshedUser = userService.findByEmail(updateUser.getEmail());
        session.setAttribute("user", refreshedUser);

        return ResponseEntity.ok("사용자 정보가 수정되었습니다.");
    }

    //사용자 이메일 중복 체크
    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email){
        boolean idDuplicate = userService.isEmailDuplicate(email);
        return ResponseEntity.ok(idDuplicate);
        //true면 중복 false 면 사용 가능
    }


}