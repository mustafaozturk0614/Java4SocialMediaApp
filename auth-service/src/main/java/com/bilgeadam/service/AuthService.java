package com.bilgeadam.service;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService  extends ServiceManager<Auth,Long > {


    private  final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository  ) {
        super(authRepository);
        this.authRepository=authRepository;
    }



    public RegisterResponseDto register(RegisterRequestDto dto){

        if (authRepository.findOptionalByUsername(dto.getUsername()).isPresent()){
            throw  new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.genarateCode());
        save(auth);
        return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }



}
