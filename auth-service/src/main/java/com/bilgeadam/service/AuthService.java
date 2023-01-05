package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.ActivateResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.Status;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 1- kayýt olduktan sonra gelen aktivasyon koduyla auth umuz statusu nu pending den active cekelim
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
        //  throw  new DataIntegrityViolationException("Kullanýcý adý vardýr");
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
            responseDto=IAuthMapper.INSTANCE.toActivateResponseDto(auth.get());
        }
        return responseDto;
    }

    public Boolean login(LoginRequestDto dto) {
        Optional<Auth> auth=authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        return  true;
    }
}
