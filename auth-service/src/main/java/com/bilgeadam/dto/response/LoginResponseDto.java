package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private Long id;
    private String username;
    private String email;
    private Roles role;
    private String token;
}
