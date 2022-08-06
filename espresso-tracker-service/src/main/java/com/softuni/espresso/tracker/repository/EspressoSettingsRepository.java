package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.repository.entities.EspressoSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspressoSettingsRepository extends JpaRepository<EspressoSettingEntity, Long> {

    List<EspressoSettingEntity> findAllByUserId(long userId);

}
