package com.softuni.espresso.tracker.repository.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bakeries")
@Getter
@Setter
public class BakeryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phoneNumber;

    @ManyToMany
    @JoinTable(
        inverseJoinColumns = @JoinColumn(name = "coffee_id"),
        joinColumns = @JoinColumn(name = "bakery_id"))
    private List<CoffeeEntity> coffees = new ArrayList<>();
}
