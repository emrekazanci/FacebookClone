package com.emre.mapper;

import com.emre.dto.request.SaveUserProfileRequestDto;
import com.emre.dto.request.UserProfileUpdateRequestDto;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);
    UserProfile toUserProfile(final SaveUserProfileRequestDto dto);
    UserProfile toUserProfile(final UserProfileUpdateRequestDto dto);
    UserProfile toUserProfile(final CreateUserModel model);

}
