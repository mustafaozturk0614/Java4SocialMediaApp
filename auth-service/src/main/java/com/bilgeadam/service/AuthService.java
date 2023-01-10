package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.ActivateResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.Roles;
import com.bilgeadam.repository.enums.Status;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 1- kay?t olduktan sonra gelen aktivasyon koduyla auth umuz statusu nu pending den active cekelim
 */
@Service
public class AuthService  extends ServiceManager<Auth,Long > {


    private  final IAuthRepository authRepository;
    private final IUserManager userManager;

    public AuthService(IAuthRepository authRepository, IUserManager userManager) {
        super(authRepository);
        this.authRepository=authRepository;
        this.userManager = userManager;
    }



    public RegisterResponseDto register(RegisterRequestDto dto){

    /*    if (authRepository.findOptionalByUsername(dto.getUsername()).isPresent()){
            throw  new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        }*/
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        try {
            auth.setActivationCode(CodeGenerator.genarateCode());
            save(auth);
            userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserDto(auth));
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        }catch (Exception e){
            System.out.println(e.toString());
        //  throw  new DataIntegrityViolationException("Kullan?c? ad? vard?r");
            throw  new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        }


    }


    public ActivateResponseDto activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth=findById(dto.getId()) ;
        ActivateResponseDto responseDto=null;
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (auth.get().getActivationCode().equals(dto.getActivationCode())){
            auth.get().setStatus(Status.ACTIVE);

            save(auth.get());
            // userprofile controller a bir id gonderebilirim
            userManager.activateStatus(dto.getId());

            responseDto=IAuthMapper.INSTANCE.toActivateResponseDto(auth.get());
        }
        return responseDto;
    }

    public Boolean login(LoginRequestDto dto) {
        Optional<Auth> auth=authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if (!auth.get().getStatus().equals(Status.ACTIVE)){
            throw new AuthManagerException(ErrorType.LOGIN_STATUS_ERROR);
        }
        return  true;
    }

    public List<ActivateResponseDto> getAllActiveStatus() {

        List<Auth> authList=authRepository.getAllActivateStatus();

//   return authList.stream().map(a-> IAuthMapper.INSTANCE.toActivateResponseDto(a)).collect(Collectors.toList());


        return IAuthMapper.INSTANCE.toActivateResponseDtos(authList);
    }

    public List<ActivateResponseDto> findAllByStatus(Status status) {

        List<Auth> authList=authRepository.findAllByStatus(status);

        return IAuthMapper.INSTANCE.toActivateResponseDtos(authList);

    }

    public List<ActivateResponseDto>  findAllByStatusString(String status) {

        Status status1=null;
              try {
                  status1= Status.valueOf(status.toUpperCase());
                  List<Auth> authList=authRepository.findAllByStatus(status1);
                  return IAuthMapper.INSTANCE.toActivateResponseDtos(authList);
              }catch (Exception e){
                throw  new AuthManagerException(ErrorType.STATUS_NOT_FOUND);
              }

    }

    public Boolean deleteAuthById(Long authId) {

        Optional<Auth> auth=findById(authId);
        if (auth.isEmpty()){

            throw  new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(Status.DELETED);
         save(auth.get());
         userManager.deleteByAuthId(authId);
         return  true;

    }

    public Boolean updateByEmailOrUsername(UpdateByEmailOrUsernameRequestDto dto) {

        Optional<Auth> auth=authRepository.findById(dto.getId());

        if (auth.isEmpty()){
            throw  new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        save(auth.get());
        return  true;

    }

    public List<RoleResponseDto> getByRole(String role) {
        Roles roles1=null;

        try {
            roles1=Roles.valueOf(role);
            return IAuthMapper.INSTANCE.toRoleResponseDtos(authRepository.findAllOptionalByRole(roles1));
        }catch (Exception e){

            throw  new AuthManagerException(ErrorType.ROLE_NOT_FOUND);
        }

    }
}
