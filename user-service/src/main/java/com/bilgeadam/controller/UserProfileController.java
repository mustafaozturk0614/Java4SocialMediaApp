package com.bilgeadam.controller;

import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.UpdateRequestDto;
import com.bilgeadam.dto.response.UpdateResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import  static  com.bilgeadam.constant.ApiUrls.*;
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;


    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @PostMapping(ACTIVATESTATUSBYID)
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return  ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @PutMapping(UPDATE)
    public  ResponseEntity<UpdateResponseDto> update(@RequestBody @Valid UpdateRequestDto dto){
        return  ResponseEntity.ok(userProfileService.updateProfile(dto));
    }
    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public  ResponseEntity<UpdateResponseDto> update2(@RequestBody @Valid UpdateRequestDto dto){
        return  ResponseEntity.ok(userProfileService.updateProfile2(dto));
    }
    @DeleteMapping(DELETEBYAUTHID+"/{authId}")
    public  ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId){
        return  ResponseEntity.ok(userProfileService.deleteByAuthId(authId));
    }
    @GetMapping(GETBYROLE)
    public ResponseEntity<List<UserProfile>> getByRole(@PathVariable String role){

        return  ResponseEntity.ok(userProfileService.getByRole(role));
    }


}
