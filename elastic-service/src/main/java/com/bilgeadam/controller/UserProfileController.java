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

import static com.bilgeadam.constant.ApiUrls.*;
@RestController
@RequestMapping(ELASTIC)
@RequiredArgsConstructor
public class UserProfileController {


private  final UserProfileService userProfileService;


    @GetMapping(FINDALL)
    public ResponseEntity<Iterable<UserProfile>> findAll(){

        return ResponseEntity.ok(userProfileService.findAll());
    }
    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile userProfile){
        return ResponseEntity.ok(userProfileService.save(userProfile));
    }
}
