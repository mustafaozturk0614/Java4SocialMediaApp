package com.bilgeadam.service;


import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.bilgeadam.dto.request.UpdateRequestDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.dto.response.UpdateResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.manager.IElasticManager;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.UpdateUserProfileModel;
import com.bilgeadam.rabbitmq.producer.UpdateUserProducer;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.Status;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private  final IUserProfileRepository userProfileRepository;
    private  final IAuthManager authManager;

    private final IElasticManager elasticManager;
    private final  CacheManager cacheManager;
    private  final UpdateUserProducer updateUserProducer;
    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository userProfileRepository, IAuthManager authManager, IElasticManager elasticManager, CacheManager cacheManager, UpdateUserProducer updateUserProducer, JwtTokenManager jwtTokenManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.authManager = authManager;
        this.elasticManager = elasticManager;
        this.cacheManager = cacheManager;
        this.updateUserProducer = updateUserProducer;
        this.jwtTokenManager = jwtTokenManager;
    }
    @Transactional
    public Boolean createUser(NewCreateUserDto dto) {

        try {
            UserProfile userProfile=IUserMapper.INSTANCE.toUserProfile(dto);
            userProfile.setCreatedDate(System.currentTimeMillis());
            save(userProfile);
            elasticManager.create(userProfile);
             return true;
        }catch (Exception e){
            e.printStackTrace();
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
        Optional<Long> id=verifyToken(dto.getToken());
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(id.get());
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

    public Optional<Long> verifyToken(String token){
        Optional<Long> id=jwtTokenManager.getUserId(token);
        if (id.isEmpty()){
            throw  new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        return id;
    }

    @Transactional
    public UpdateResponseDto updateProfile2(UpdateRequestDto dto) {
        Optional<Long> id=verifyToken(dto.getToken());
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(id.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername());
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

    public UpdateResponseDto updateProfileWithRabbitMQ(UpdateRequestDto dto) {
        Optional<Long> id=verifyToken(dto.getToken());
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(id.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername());
        if (!dto.getUsername().equals(userProfile.get().getUsername()) ||!dto.getEmail().equals(userProfile.get().getEmail())  ){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());

            updateUserProducer.sendUpdateUser(UpdateUserProfileModel.builder()
                    .email(userProfile.get().getEmail())
                    .username(userProfile.get().getUsername())
                    .id(userProfile.get().getAuthId())
                    .build());

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
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername());
        return  true;
    }
    @Cacheable(value = "getbyrole" ,key = "#role.toUpperCase()")
    public List<UserProfile> getByRole(String role) {
        List<RoleResponseDto> roleResponseDtoList= authManager.getByRole(role.toUpperCase()).getBody();
        return  roleResponseDtoList.stream().map(x-> userProfileRepository.findOptionalByAuthId(x.getId()).get()).collect(Collectors.toList())   ;
    }


    @Cacheable(value = "findallactiveprofile")
    public List<UserProfile> findAllActiveProfile() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userProfileRepository.findAllActiveProfile();
    }

    @Cacheable(value = "findbyusername",key="#username.toLowerCase()")
    public UserProfile findbyUsername(String username){
        try {
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
       Optional<UserProfile> userProfile=  userProfileRepository.findOptionalByUsernameEqualsIgnoreCase(username);
        if (userProfile.isPresent()){
            return  userProfile.get();
        }else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Kullanýcý Adý Bulunamadi");
        }
    }

}
