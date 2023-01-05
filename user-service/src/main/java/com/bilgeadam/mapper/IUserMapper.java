package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.NewCreateUserDto;
import com.bilgeadam.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserMapper {


IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);


UserProfile toUserProfile(final NewCreateUserDto dto);

}
