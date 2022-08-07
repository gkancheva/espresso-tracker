package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.repository.entities.BakeryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BakeryRepository extends JpaRepository<BakeryEntity, Long> {

    Optional<BakeryEntity> findByName(String name);

}
