package com.softuni.espresso.tracker.model.mapper;

import com.softuni.espresso.tracker.model.CoffeeTool;
import com.softuni.espresso.tracker.model.entities.CoffeeToolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeToolMapper {
    
    CoffeeTool map(CoffeeToolEntity entity);

}