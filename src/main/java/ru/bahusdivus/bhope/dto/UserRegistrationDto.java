package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationDto {
    private String login;
    private String password;
    private String confirmPassword;
    private String name;
    private String email;
}
