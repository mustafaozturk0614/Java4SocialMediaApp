package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank
    @Size(min = 3 ,max=20 ,message = "Kullanici adi en az  3 karakter en fazla 20 karakter olabilir")
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8 ,max=32 ,message = "Sifre en az 8 karakter en fazla 32 karakter olabilir")
    private String password;
}
