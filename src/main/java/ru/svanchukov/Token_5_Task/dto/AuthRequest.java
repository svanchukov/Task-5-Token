package ru.svanchukov.Token_5_Task.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
