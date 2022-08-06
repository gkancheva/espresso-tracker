package com.softuni.espresso.tracker.model.mapper;

import com.softuni.espresso.tracker.model.CustomUser;
import com.softuni.espresso.tracker.model.UserRequest;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CustomUser mapToUser (UserEntity entity);

    @Mapping(target = "password", ignore = true)
    UserEntity mapToEntity (UserRequest userRequest);
}
