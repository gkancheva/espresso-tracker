package com.softuni.espresso.tracker.model.mapper;

import com.softuni.espresso.tracker.model.EspressoSetting;
import com.softuni.espresso.tracker.model.entities.EspressoSettingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CoffeeMapper.class)
public interface EspressoSettingMapper {

    EspressoSetting mapToModel (EspressoSettingEntity entity);

}
