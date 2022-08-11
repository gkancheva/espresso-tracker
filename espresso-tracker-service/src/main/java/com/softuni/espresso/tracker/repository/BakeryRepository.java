package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.model.entities.BakeryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BakeryRepository extends JpaRepository<BakeryEntity, Long> {

    Optional<BakeryEntity> findByName(String name);

    @Query("SELECT b FROM BakeryEntity b " +
            "LEFT JOIN CoffeeEntity c ON c.bakery.id = b.id " +
            "WHERE b.id = :id")
    Optional<BakeryEntity> findByIdWithCoffees(long id);

}
