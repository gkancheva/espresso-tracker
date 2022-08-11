package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.model.entities.CoffeeToolEntity;
import com.softuni.espresso.tracker.model.CoffeeToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeToolRepository extends JpaRepository<CoffeeToolEntity, Long> {

    Optional<CoffeeToolEntity> findByCoffeeToolTypeAndAndName(CoffeeToolType type, String name);

}
