package com.bilgeadam.controller;

import com.bilgeadam.dto.request.ActivateRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.bilgeadam.dto.response.ActivateResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.Status;
import com.bilgeadam.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import  static  com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.GETBYROLE;

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


    @PostMapping(ACTIVATESTATUS)
    public  ResponseEntity<ActivateResponseDto> activateStatus(@RequestBody  ActivateRequestDto dto){

        return  ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody @Valid LoginRequestDto dto){

            return  ResponseEntity.ok(authService.login(dto));

    }

    @GetMapping(GETALLACTIVATESTATUS)
    public ResponseEntity<List<ActivateResponseDto>> getAllActiveStatus(){

        return  ResponseEntity.ok(authService.getAllActiveStatus());

    }
    @GetMapping(GETALLBYSTATUS)
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus(Status status){
        return  ResponseEntity.ok(authService.findAllByStatus(status));
    }
    @GetMapping(GETALLBYSTATUS+"/{status}")
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus2(@PathVariable Status status){
        return  ResponseEntity.ok(authService.findAllByStatus(status));
    }
    @GetMapping(GETALLBYSTRINGSTATUS+"/{status}")
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus3(@PathVariable String status){
        return  ResponseEntity.ok(authService.findAllByStatusString(status));
    }

    @DeleteMapping(DELETEBYID+"/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){

        return  ResponseEntity.ok(authService.deleteAuthById(id));

    }
    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean> updateByEmailOrUsername(@RequestBody UpdateByEmailOrUsernameRequestDto dto){

        return ResponseEntity.ok(authService.updateByEmailOrUsername(dto));
    }

    @GetMapping(GETBYROLE)
    public ResponseEntity<List<RoleResponseDto>> getByRole(@PathVariable String role){

        return  ResponseEntity.ok(authService.getByRole(role));
    }

}
