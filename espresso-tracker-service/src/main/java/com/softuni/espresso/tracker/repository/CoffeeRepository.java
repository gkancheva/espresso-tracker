package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.model.entities.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {

    List<CoffeeEntity> findAllByDateCreated(LocalDate date);

}
