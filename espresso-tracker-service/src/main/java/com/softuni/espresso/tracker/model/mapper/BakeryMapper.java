package com.softuni.espresso.tracker.model.mapper;

import com.softuni.espresso.tracker.model.Bakery;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.repository.entities.BakeryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BakeryMapper {

    Bakery map (BakeryEntity entity);

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "imgSource", target = "imgSrc")
    BakeryEntity mapToEntity(BakeryRequest request);

}
