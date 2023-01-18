package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.ActivateResponseDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.dto.response.RoleResponseDto;
import com.bilgeadam.rabbitmq.model.NewCreateUserModel;
import com.bilgeadam.rabbitmq.model.UpdateUserProfileModel;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);


    Auth  toAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);


    ActivateResponseDto toActivateResponseDto(final  Auth auth);

    @Mapping(source ="id" ,target ="authId")
    NewCreateUserDto toNewCreateUserDto(final Auth auth);

    List<ActivateResponseDto>  toActivateResponseDtos(final List<Auth> authlist);

    List<RoleResponseDto> toRoleResponseDtos(final List<Auth> authList);

    LoginResponseDto toLoginResponseDto(final  Auth auth);

    @Mapping(source = "id",target = "authId")
    NewCreateUserModel toNewCreateUserModel(final Auth auth);

    Auth toUserAuth(final UpdateUserProfileModel model);
}
