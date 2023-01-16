package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.dto.request.UpdateRequestDto;
import com.bilgeadam.dto.response.UpdateResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserMapper {


IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);


UserProfile toUserProfile(final NewCreateUserDto dto);


UserProfile toUserProfile(final UpdateRequestDto dto);
UpdateResponseDto toUpdateResponseDto(final UpdateRequestDto dto);
UpdateResponseDto toUpdateResponseDto(final UserProfile userProfile);

}
