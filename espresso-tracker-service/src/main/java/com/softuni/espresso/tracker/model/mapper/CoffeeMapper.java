package com.softuni.espresso.tracker.model.mapper;

import com.softuni.espresso.tracker.model.Coffee;
import com.softuni.espresso.tracker.repository.entities.CoffeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    @Mapping(source = "bakery.name", target = "bakery")
    Coffee mapToModel(CoffeeEntity entity);
}
