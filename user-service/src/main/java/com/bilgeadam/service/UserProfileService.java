package com.bilgeadam.service;


import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.bilgeadam.dto.request.UpdateRequestDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.dto.response.UpdateResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.Status;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {


    private  final IUserProfileRepository userProfileRepository;
    private  final IAuthManager authManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, IAuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.authManager = authManager;
    }

    public Boolean createUser(NewCreateUserDto dto) {

        try {
            UserProfile userProfile=IUserMapper.INSTANCE.toUserProfile(dto);
            userProfile.setCreatedDate(System.currentTimeMillis());
            save(userProfile);
             return true;
        }catch (Exception e){
            throw  new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw  new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return  true;
    }


    public UpdateResponseDto updateProfile(UpdateRequestDto dto) {

        Optional<UserProfile> userProfile=userProfileRepository.findById(dto.getId());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setName(dto.getName());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setUsername(dto.getUsername());
        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setUpdatedDate(System.currentTimeMillis());
        save(userProfile.get());
         return  IUserMapper.INSTANCE.toUpdateResponseDto(userProfile.get());

    }


    public UpdateResponseDto updateProfile2(UpdateRequestDto dto) {
        Optional<UserProfile> userProfile=userProfileRepository.findById(dto.getId());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!dto.getUsername().equals(userProfile.get().getUsername()) ||!dto.getEmail().equals(userProfile.get().getEmail())  ){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
             authManager.updateByEmailOrUsername(UpdateByEmailOrUsernameRequestDto.builder().id(userProfile.get().getAuthId())
                     .email(dto.getEmail()).username(dto.getUsername()).build());

        }
        userProfile.get().setName(dto.getName());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setUpdatedDate(System.currentTimeMillis());
        save(userProfile.get());
        return  IUserMapper.INSTANCE.toUpdateResponseDto(userProfile.get());

    }


    public Boolean deleteByAuthId(Long authId) {

        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId);

        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.DELETED);
        save(userProfile.get());
        return  true;
    }

    public List<UserProfile> getByRole(String role) {

        List<RoleResponseDto> roleResponseDtoList= authManager.getByRole(role).getBody();


        return  roleResponseDtoList.stream().map(x-> userProfileRepository.findOptionalByAuthId(x.getId()).get()).collect(Collectors.toList())   ;
    }
}
