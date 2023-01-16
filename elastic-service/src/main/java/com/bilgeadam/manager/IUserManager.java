package com.bilgeadam.manager;

import com.bilgeadam.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.FINDALL;

@FeignClient(url = "${myapplication.feign.user.profile}",name = "user-service-elastic-userprofile",decode404 = true)
public interface IUserManager {


    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findAll();
}
