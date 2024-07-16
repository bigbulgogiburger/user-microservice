package com.example.usermicroservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
    @NotNull(message = "이메일에다가 널 넣지 마세요")
    @Size(min=2, message = "이메일이 두글자인건 말이 안됩니다.")
    @Email
    private String email;
    @NotNull(message = "이름에다 널 넣지 마세요")
    @Size(min=2, message = "이름이 두글자인건 말이 안됩니다.")
    private String name;
    @NotNull(message = "can't be null")
    @Size(min=8,message = "input more than 8 characters")
    private String pwd;
}
