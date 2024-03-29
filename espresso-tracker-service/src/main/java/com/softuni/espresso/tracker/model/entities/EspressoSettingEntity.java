package com.softuni.espresso.tracker.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "espresso_settings")
@Getter
@Setter
public class EspressoSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CoffeeEntity coffee;

    @ManyToOne
    private CoffeeToolEntity coffeeMachine;

    @ManyToOne
    private CoffeeToolEntity grinder;

    private float dose;

    @Column(nullable = false)
    private String grindingFineness;

    private float brewingTemperature;

    private float brewingPressure;

    private float volume;

    private int extractDurationSec;
}
