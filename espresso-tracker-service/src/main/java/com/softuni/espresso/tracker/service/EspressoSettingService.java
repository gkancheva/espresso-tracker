package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.EspressoSetting;
import com.softuni.espresso.tracker.model.mapper.EspressoSettingMapper;
import com.softuni.espresso.tracker.repository.EspressoSettingsRepository;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.repository.entities.EspressoSettingEntity;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspressoSettingService {

    private final UserRepository userRepository;
    private final EspressoSettingsRepository espressoSettingsRepository;
    private final EspressoSettingMapper mapper;

    public List<EspressoSetting> getCoffees(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        List<EspressoSettingEntity> espressoSettingsEntities = espressoSettingsRepository.findAllByUserId(user.get().getId());

        return espressoSettingsEntities.stream().map(mapper::mapToModel)
                .collect(Collectors.toList());
    }

}
