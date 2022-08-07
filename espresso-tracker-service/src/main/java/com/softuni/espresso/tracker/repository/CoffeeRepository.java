package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.repository.entities.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {

}
