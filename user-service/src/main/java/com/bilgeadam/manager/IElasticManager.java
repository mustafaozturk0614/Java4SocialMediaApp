package com.bilgeadam.manager;

import com.bilgeadam.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@FeignClient(url = "${myapplication.feign.elastic}",name = "user-elastic",decode404 = true)
public interface IElasticManager {


    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile userProfile);

}
