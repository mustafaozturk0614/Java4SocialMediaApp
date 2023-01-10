package com.bilgeadam.manager;

import com.bilgeadam.dto.request.NewCreateUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.DELETEBYAUTHID;


@FeignClient(url = "${myapplication.feign.user.profile}",name = "user-userprofile",decode404 = true)
public interface IUserManager {

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto);

    @PostMapping(ACTIVATESTATUSBYID)
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);

    @DeleteMapping(DELETEBYAUTHID+"/{authId}")
    public  ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId);

}
