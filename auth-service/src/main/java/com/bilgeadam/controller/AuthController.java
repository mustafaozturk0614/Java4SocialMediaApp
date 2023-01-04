package com.bilgeadam.controller;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import  static  com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {


    private final AuthService authService;


    @PostMapping(REGISTER)
    @Operation(summary = "Kullanici kayit eden metot")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return  ResponseEntity.ok( authService.register(dto));
    }


}
