package com.bilgeadam.manager;

import com.bilgeadam.dto.request.NewCreateUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(url = "http://localhost:8091/api/v1/user",name = "user-userprofile",decode404 = true)
public interface IUserManager {


    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto);

}
