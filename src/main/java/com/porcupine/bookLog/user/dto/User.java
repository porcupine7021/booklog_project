package com.porcupine.bookLog.user.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private String nickname;
    private LocalDateTime createdAt;
}
